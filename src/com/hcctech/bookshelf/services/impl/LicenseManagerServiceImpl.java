package com.hcctech.bookshelf.services.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.dao.BsAreaDao;
import com.hcctech.bookshelf.dao.BsDictionaryDao;
import com.hcctech.bookshelf.dao.BsEbookDao;
import com.hcctech.bookshelf.dao.BsLicenseBatchDao;
import com.hcctech.bookshelf.dao.BsLicenseKeyDao;
import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.dao.BsSchoolDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsDictionary;
import com.hcctech.bookshelf.pojo.BsEbook;
import com.hcctech.bookshelf.pojo.BsLicenseBatch;
import com.hcctech.bookshelf.pojo.BsLicenseKey;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.LicenseManagerService;
import com.hcctech.bookshelf.util.LicenseKeyUtil;
import com.hcctech.bookshelf.util.OperatorExcel;
import com.opensymphony.xwork2.ActionContext;

/**
 * 授权码管理
 * @author apple
 *
 */
public class LicenseManagerServiceImpl implements LicenseManagerService {
	private BsLicenseBatchDao bsLicenseBatchDao;
	private BsLicenseKeyDao bsLicenseKeyDao;
	private BsProductsDao bsProductsDao;
	private BsAreaDao bsAreaDao;
	private BsSchoolDao bsSchoolDao;
	private BsDictionaryDao bsDictionaryDao;
	private BsEbookDao bsEbookDao;
	private static final Log logger = LogFactory.getLog(LicenseManagerServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LicenseManagerService#loadSchool(java.lang.String)
	 */
	public List<BsSchool> loadSchool(String shengId){
		final String hql="from BsSchool b where b.bsArea.areaId=?";
		List<BsSchool> list=bsSchoolDao.findByHql(hql, Integer.valueOf(shengId));
		for(BsSchool bsSchool:list){
			bsSchool.setBsArea(null);
			bsSchool.setBsLicenseKeies(null);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LicenseManagerService#exportExcel(java.lang.String)
	 */
	public void exportExcel(String batch) throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String,Object> map =loadKeyByBatch(batch);
			OperatorExcel.exportExcel(batch,map, response);
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LicenseManagerService#addLicenseKeyBatch(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String)
	 */
	public void addLicenseKeyBatch(String idStr, String quId, Integer schoolId,String schoolStage,
			Integer keySize, String lifeTime,String pattern ,String serVersion,String pattern_target){
		if(serVersion == null){
			return ;
		}
		BsAdminUser user= (BsAdminUser) ActionContext.getContext().getSession().get("adminuser");
		if(user==null||"".equals(user.getUserName())){
			return;
		}
		if(keySize<=0){
			return;
		}
		if(lifeTime==null||"".equals(lifeTime)){
			return;
		}
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = dateformat.parse(lifeTime);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		try {
    		final String schoolHql="from BsSchool b where b.schoolId = ?";
    		BsSchool bsSchool = bsSchoolDao.findUniqueByHql(schoolHql,schoolId);
    		if(bsSchool==null){
    			return;
    		}
    		//批次
    		BsLicenseBatch bsLicenseBatch=new BsLicenseBatch();
    		if (idStr == null ||"".equals(idStr) ||idStr.trim().equals("")) {
    			return;
    		}
    		bsLicenseBatch.setCreateTime( new Timestamp(System.currentTimeMillis()));
    		bsLicenseBatch.setOperator(user.getUserName());
    		bsLicenseBatch.setSchoolStage(Integer.valueOf(schoolStage));
    		bsLicenseBatchDao.save(bsLicenseBatch);
    		//授权码
    	//	List<String> list=LicenseKeyUtil.getLicenKey(keySize);
    		String[] str=idStr.split(",");
    		Set<BsProducts> bsProductses = new HashSet<BsProducts>();
    		for (String id : str) {
    			BsProducts products = bsProductsDao.get(Integer.valueOf(id));
    			bsProductses.add(products);
    		}
    		//final String productSql="insert into bs_license_ebook (product_id,key_id) values(?,?)";
    		if(bsProductses==null || bsProductses.size()<=0) {
    		    return ;
    		}
    		List<BsDictionary> dictionaries = bsDictionaryDao.findByHql("from BsDictionary bd where bd.name = ?", bsProductses.iterator().next().getSubject());
    		String subject = "Z";
    		if(dictionaries!=null&&!dictionaries.isEmpty()){
    			subject = dictionaries.get(0).getDicCode();
    		}
    		
    		List<BsEbook> ebook = bsEbookDao.findByHql("from BsEbook e where e.bookCode = ?", bsProductses.iterator().next().getBookCode());
    		String eduVersion = "0";
    		if(ebook!=null&&!ebook.isEmpty()){
    			eduVersion = ebook.get(0).getEduVersion();
    		}//获取电子书版本
    		for(int i=0;i<keySize;i++){
    			BsLicenseKey bsLicenseKey=new BsLicenseKey();
    			bsLicenseKey.setBsLicenseBatch(bsLicenseBatch);
    			bsLicenseKey.setBsSchool(bsSchool);
    			bsLicenseKey.setBsWebUser(null);
    			bsLicenseKey.setKeyId("");
    			bsLicenseKey.setUseStatus(0);
    			bsLicenseKey.setLifeTime(date);
    			//products.getBookCode()
    			String sn = LicenseKeyUtil.generateSN(bsProductses, bsSchool.getBsArea().getAreaCode(), pattern,pattern_target, serVersion ,subject, eduVersion, i);
    			bsLicenseKey.setSerialNum(sn);
    			bsLicenseKey.setBsProductses(bsProductses);
    			bsLicenseKeyDao.save(bsLicenseKey);
    			bsLicenseKey.setKeyId(LicenseKeyUtil.generateLicenKey(bsLicenseKey.getLkid()));
    			try {
    				Thread.sleep(10);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			/*for(int j=0;j<str.length;j++){
    				bsLicenseKeyDao.executeSQL(productSql,Integer.valueOf(idStr),list.get(i));
    			}*/
    			//bsLicenseKeyDao.executeSQL(productSql,Integer.valueOf(idStr),list.get(i));
    		}
    		logger.info("添加授权码批次"+bsLicenseBatch.getBatchId());
		}catch(Exception e) {
		    e.printStackTrace();
		    return;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LicenseManagerService#loadSheng(java.lang.String)
	 */
	public List<BsArea> loadSheng(String shengId){
		String hql="from BsArea a where a.bsArea.areaId ="+shengId;
		List<BsArea> list =bsAreaDao.findByHql(hql);
		for (BsArea bsArea : list) {
			bsArea.setBsSchools(null);
			bsArea.setBsAreas(null);
			bsArea.setBsArea(null);
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LicenseManagerService#loadProductAll(java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Page<BsProducts> loadProductAll(String productName, int pageNo, int pageSize, String sort, String order,String idStr) {
		final StringBuilder hql = new StringBuilder("from BsProducts b where b.status = 1 ");
		appendHqlProduct(productName, hql, sort, order,idStr);
		Page<BsProducts> page =  bsProductsDao.pagedQuery(hql.toString(), pageNo, pageSize);
		return page;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LicenseManagerService#loadKeyByBatch(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> loadKeyByBatch(String batch) {
		List<BsLicenseKey> list = null;
		List<BsLicenseKey> list1 = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if (batch != null && !("".equals(batch)) && !(batch.trim().equals(""))) {
			// 授权码列表
			final String hql = "from BsLicenseKey b inner join fetch b.bsLicenseBatch where b.bsLicenseBatch.batchId=?  order by b.useStatus asc";
			list = bsLicenseKeyDao.findByHql(hql, Integer.valueOf(batch));
			// 已用授权码数量
			final String hql1 = "from BsLicenseKey b inner join fetch b.bsLicenseBatch where b.bsLicenseBatch.batchId=? and b.useStatus=1";
			list1 = bsLicenseKeyDao.findByHql(hql1, Integer.valueOf(batch));
			int usedSize = 0;
			if (list1 != null && list1.size() > 0) {
				usedSize = list1.size();
			}
			// 电子书列表
			if (list != null && list.size() > 0) {
				Set<BsProducts> set = list.get(0).getBsProductses();
				for (BsProducts bsProducts : set) {
					bsProducts.getBookCode();
					bsProducts.getProductName();
				}
				map.put("set", set);
				map.put("usedSize", usedSize);
				map.put("name", list.get(0).getBsSchool().getSchoolName());
				map.put("list", list);
			}
		}
		return map;
	}

	/**
	 * @param batch
	 * @param list
	 * @return
	 */
	public String loadSchoolName(String batch, List<BsLicenseKey> list) {
		if (batch != null && !("".equals(batch)) && !(batch.trim().equals(""))) {
			final String hql = "from BsLicenseKey b inner join fetch b.bsLicenseBatch where b.bsLicenseBatch.batchId=?";
			list = bsLicenseKeyDao.findByHql(hql, Integer.valueOf(batch));
		}
		String schoolName = list.get(0).getBsSchool().getSchoolName();
		return schoolName;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LicenseManagerService#loadBatchList(java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
    public Page<BsLicenseBatch> loadBatchList(String batch, String schoolName, int pageNo, int pageSize, String sort, String order)
    {
        String temp = "from BsLicenseKey b inner join fetch b.bsLicenseBatch where b.bsLicenseBatch.batchId=?";
        final StringBuilder hql = new StringBuilder("select b from BsLicenseBatch b join b.bsLicenseKeies as c ");
        appendHqlBatch(batch, schoolName, hql, sort, order);
        Page<BsLicenseBatch> page = bsLicenseBatchDao.queryPage_(hql.toString(), pageNo, pageSize);
        if(page != null && page.getList() != null && page.getList().size() > 0)
        {
            for(BsLicenseBatch bsLicenseBatch : page.getList())
            {
                if(bsLicenseBatch != null)
                {
                    Set<BsLicenseKey> s = bsLicenseBatch.getBsLicenseKeies();
                    if(s != null)
                    {
                        int zongshu = s.size();
                        int i = 0;
                        for(BsLicenseKey bsLicenseKey : s)
                        {
                            if(bsLicenseKey != null)
                            {
                                BsSchool bsSchool = bsLicenseKey.getBsSchool();
                                try
                                {
                                    if(bsLicenseKey.getLifeTime().before(new Date()))
                                    {
                                        bsLicenseBatch.setIsLift("已过期");
                                    }
                                    else
                                    {
                                        bsLicenseBatch.setIsLift("未过期");
                                    }
                                    if(bsSchool != null && bsSchool.getSchoolName() != null && !"".equals(bsSchool.getSchoolName()))
                                    {
                                        bsLicenseBatch.setSchoolName(bsSchool.getSchoolName());
                                    }
                                    else
                                    {
                                        bsLicenseBatch.setSchoolName("");
                                    }
                                }
                                catch(Exception e)
                                {
                                    bsLicenseBatch.setSchoolName("");
                                }
                                if(bsLicenseKey.getUseStatus() == 1)
                                {
                                    i = i + 1;
                                }
                                break;
                            }
                        }
                    }
                    final String hql0 = "from BsLicenseKey b  where b.bsLicenseBatch.batchId=? ";
                    List list = bsLicenseKeyDao.findByHql(hql0, bsLicenseBatch.getBatchId());
                    // 已用授权码数量
                    final String hql1 = "from BsLicenseKey b where b.bsLicenseBatch.batchId=? and b.useStatus=1";
                    List list1 = bsLicenseKeyDao.findByHql(hql1, bsLicenseBatch.getBatchId());
                    int usedSize = 0;
                    if(list1 != null && list1.size() > 0)
                    {
                        usedSize = list1.size();
                    }
                    bsLicenseBatch.setShuliang(usedSize + "/" + list.size());
                }
            }
        }
        return page;
    }

	/**
	 * 拼hql
	 * @param batch
	 * @param schoolName
	 * @param hql
	 * @param sort
	 * @param order
	 */
	public void appendHqlBatch(String batch, String schoolName,
			final StringBuilder hql, String sort, String order) {
		boolean flag = false;
		if (batch != null && !batch.trim().equals("")) {
			hql.append(" where b.batchId like '%").append(batch).append("%' ");
			flag = true;
		}
		if (schoolName != null && !schoolName.trim().equals("")) {
			if (flag) {
				hql.append(" and c.bsSchool.schoolName like '%")
						.append(schoolName).append("%' ");
			} else {
				String t = " where c.bsSchool.schoolName like '%"+schoolName+"%'";
				hql.append(t);
				
			}
			flag = true;
		}
		hql.append(" GROUP BY b.batchId ");
		if (sort != null && !"".equals(sort.trim()) && order != null
				&& !"".equals(order.trim())) {
			hql.append(" order by b.").append(sort).append(" ").append(order);
		} else {
			hql.append(" order by b.createTime desc");
		}
	}
	
	/**
	 * 拼hql
	 * @param productName
	 * @param hql
	 * @param sort
	 * @param order
	 * @param idStr
	 */
	public void appendHqlProduct(String productName,
			final StringBuilder hql, String sort, String order,String idStr) {
		boolean flag=false;
		if (productName != null &&!"".equals(productName) && !productName.trim().equals("")) {
				hql.append(" and b.productName like '%")
						.append(productName).append("%' ");
				flag=true;
		}
		if (idStr != null &&!"".equals(idStr) &&!idStr.trim().equals("")) {
			if(flag){
				hql.append(" and b.productId not in (")
				.append(idStr).append(") ");
			}else{
				hql.append(" and b.productId not in (")
				.append(idStr).append(") ");
			}
		}
		if (sort != null && !"".equals(sort.trim()) && order != null
				&& !"".equals(order.trim())) {
			hql.append(" order by b.").append(sort).append(" ").append(order);
		} else {
			hql.append(" order by b.productId asc");
		}
	}
	
	public BsLicenseKey loadByAuthCode(String authCode){
		Integer lkid = null ;
		if(authCode.length() == 16){
			lkid = keyId(authCode);
			String s="-";
			authCode = authCode.substring(0,4)+s+authCode.substring(4,8)+s+authCode.substring(8,12)+s+authCode.substring(12,16);
		}else if(authCode.length() == 19){
			String[] keyArr=authCode.split("-");
			StringBuffer sb = new StringBuffer();
			for(String str : keyArr){
				sb.append(str);
			}
			lkid = keyId(sb.toString());
		}else {
			return null;
		}
		String hql = "SELECT key from BsLicenseKey key join key.bsProductses p where key.lkid = ?";
		List<BsLicenseKey> list = bsLicenseKeyDao.findByHql(hql, lkid) ;
		if(list == null || list.size() == 0){
			return null;
		}
		BsLicenseKey bs = list.get(0);
		if(!bs.getKeyId().equals(authCode)){
			return null;
		}
		if(bs.getBsWebUser() != null){
			BsWebUser user = bs.getBsWebUser();
			user.setBsLicenseKeies(null);
			user.setBsMybooks(null);
			user.setBsOrders(null);
			user.setBsUserDrms(null);
			user.setBsUserInfo(null);
		}
		BsSchool school = bs.getBsSchool();
		school.setBsLicenseKeies(null);
		school.setBsArea(null);
		BsLicenseBatch batch = bs.getBsLicenseBatch() ;
		if(batch != null) {
			batch.setBsLicenseKeies(null);
		}
		Iterator<BsProducts> iterator = bs.getBsProductses().iterator() ;
		while(iterator.hasNext()){
			BsProducts product = iterator.next();
			product.setBsLicenseKeies(null);
			product.setBsMybooks(null);
			product.setBsOrderProducts(null);
		}
		return bs;
	}
	
	private Integer keyId(String authCode){
		Integer lkid = null;
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < authCode.length() ; i++){
			if(i%2==0){
				char c = authCode.charAt(i);
				if(c >= 48 && c<= 57){
					sb.append(c);
				}
			}
		}
		lkid = Integer.parseInt(sb.toString());
		return lkid ;
	}

	public void setBsLicenseBatchDao(BsLicenseBatchDao bsLicenseBatchDao) {
		this.bsLicenseBatchDao = bsLicenseBatchDao;
	}

	public void setBsLicenseKeyDao(BsLicenseKeyDao bsLicenseKeyDao) {
		this.bsLicenseKeyDao = bsLicenseKeyDao;
	}

	public void setBsProductsDao(BsProductsDao bsProductsDao) {
		this.bsProductsDao = bsProductsDao;
	}
	public void setBsAreaDao(BsAreaDao bsAreaDao) {
		this.bsAreaDao = bsAreaDao;
	}

	public void setBsSchoolDao(BsSchoolDao bsSchoolDao) {
		this.bsSchoolDao = bsSchoolDao;
	}

	public void setBsDictionaryDao(BsDictionaryDao bsDictionaryDao) {
		this.bsDictionaryDao = bsDictionaryDao;
	}

	public void setBsEbookDao(BsEbookDao bsEbookDao) {
		this.bsEbookDao = bsEbookDao;
	}
	
}
