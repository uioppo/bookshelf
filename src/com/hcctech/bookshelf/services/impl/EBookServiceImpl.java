package com.hcctech.bookshelf.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hcctech.bookshelf.dao.BsAdminUserDao;
import com.hcctech.bookshelf.dao.BsEbookDao;
import com.hcctech.bookshelf.dao.BsFlowDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsEbook;
import com.hcctech.bookshelf.pojo.BsFlow;
import com.hcctech.bookshelf.services.EBookService;
import com.hcctech.bookshelf.util.AESUtils;
import com.hcctech.bookshelf.util.FtpBeanUtil;
import com.hcctech.bookshelf.util.Md5;
import com.hcctech.bookshelf.util.ThirdVelocityEmailUtil;
import com.hcctech.webservice.util.UNZipServiceImplServiceCallbackHandler;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnzipAndEncrypt;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnzipAndEncryptE;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnzipAndEncryptResponseE;

public class EBookServiceImpl implements EBookService{

	private BsEbookDao bsEbookDao;
	
	private BsFlowDao bsFlowDao;
	
	private BsAdminUserDao bsAdminUserDao;
	
	private ThirdVelocityEmailUtil thirdVelocityEmailUtil;
		
	private static final Log logger = LogFactory.getLog(EBookServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#checkName(java.lang.String)
	 */
	public boolean checkName(String bookName){
		boolean flag=false;
		final String hql ="from BsEbook b where b.bookName =?";
		List<BsEbook> list = bsEbookDao.findByHql(hql, bookName);
		if(list!=null&&list.size()>0){
			flag=true;
		}
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#findEBookList(com.hcctech.bookshelf.pojo.BsEbook)
	 */
	//待审核列表（status=0），电子书列表（status=1）
	public Page<BsEbook> findEBookList(String subject ,Integer status,int pageNo,int pageSize,String name,String value){
		String hql = "select new BsEbook(be,bf.time)"+
						" from BsEbook as be,BsFlow as bf " +
						" where (bf.type = ? or bf.type = ?) and bf.isPass = ? and bf.objectId=be.id ";
		if(null==status){status = 0;}
		subject = StringUtils.trimToNull(subject);
		if(subject != null && !"null".equals(subject)){
			try {
				subject= URLDecoder.decode(subject, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			hql += " and be.subject = '" + subject + "' ";
		}
		if(name!=null) {
			if("bookName_".equals(name)){
				hql += " and be.bookName like '%"+value +"%'";
			}else if("teachVersion_".equals(name)) {
				hql += " and be.teachVersion like '%"+value +"%'";
			}else if("schoolStage_".equals(name)) {
				if("小学".equals(value)){
					value="1";
				} else if("初中".equals(value)){
					value="2";
				} else if ("高中".equals(value)){
					value="3";
				}
				hql += " and be.schoolStage = "+value;
			}else if("grade_".equals(name)) {
				hql += " and be.grade like '%"+value +"%'";
			}else if("subject_".equals(name)) {
				hql += " and be.subject like '%"+value +"%'";
			}else if("applyObject_".equals(name)) {
				hql += " and be.applyObject like '%"+value +"%'";
			}else if("publishing_".equals(name)) {
				hql += " and be.publishing like '%"+value +"%'";
			}
		}
		hql+=" order by bf.time desc";
		Page<BsEbook> page = bsEbookDao.pagedQuery(hql, pageNo, pageSize, "1","2",status);
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#findUpdateEBookList(java.lang.Integer, int, int, java.lang.String, java.lang.String)
	 */
	//更新电子书版本
	public Page<BsEbook> findUpdateEBookList(int pageNo,int pageSize,String name,String value){
		/*更新电子书列表
		 * 已经审核通过的电子书，然后选择最新的version 进行更新
		 * */
//		String sql = "select a.* from bs_ebook a ,bs_flow b " +
//				" where a.id=b.object_id and b.type in (1,2) and b.is_pass=1 ";
//		if(name!=null && "bookName_".equals(name)){
//			sql += " and a.book_name like '%"+value +"%'";
//		}
//		sql += "order by a.book_code,a.version DESC";
//		Page<BsEbook> page = bsEbookDao.pagedSqlQuery(sql, pageNo, pageSize);
// 		= bsEbookDao.findListBySql_(sql, pageNo, pageSize);
		Page<BsEbook> page = null;
		String hql = "select a from BsEbook a ,BsFlow b where a.id = b.objectId and b.type in (1,2) and b.isPass = 1";
		if(name!=null && "bookName_".equals(name)){
			hql += " and a.bookName like '%"+value +"%'";
		}
//		hql += " order by a.bookCode,a.version";
		hql+=" order by b.time desc";
		page = bsEbookDao.pagedQuery(hql, pageNo, pageSize);
		List<BsEbook> ebooks = page.getList();
		Map<String, BsEbook> map = new HashMap<String, BsEbook>();
		for (BsEbook book : ebooks) {
			map.put(book.getBookCode(), book);
		}
		page.setList(new ArrayList<BsEbook>(map.values()));
		return page;
	}
	

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#updateEbookStatus(java.lang.Integer, java.lang.Integer)
	 */
	//待审核列表
	//审核通过（status =1），审核未通过（status=2）
	public void updateEbookStatus(Integer bookId,Integer status,String advice,BsAdminUser adminUser){
		/* status 说明     
		 * 			  3:系统上传流程中的初始状态  
		 * 			  0:待审核（编辑属性提交后）
		 * 			  1：审核通过  
		 *            2：审核不通过
		 *            4：属性不符合，退回
		 */
		if(status!=null){
			final String hql = "from BsEbook where id = ?";
			BsEbook be = bsEbookDao.findUniqueByHql(hql, bookId);
			if(status==2){
				if(null!=be){
					final String uName = adminUser.getRealName();
					final String filePath = be.getBookPath();
					final String fileUrl = be.getBookUrl();
					final String bookname = be.getBookName();
					//删除电子书以及源文件
					bsEbookDao.remove(be.getId());
					Thread thread = new Thread(
					new Runnable() {
						public void run() {
							try {
								FtpBeanUtil f = new FtpBeanUtil();
								f.createFTPClient();
								f.deleteFile(filePath);
								f.deleteDirectoryAndFile(fileUrl);
								f.shutdown();
								logger.info(uName+"审核电子书【"+bookname+"】不通过,删除源文件:"+filePath+",以及目录："+fileUrl);
							} catch (SocketException e) {
							} catch (IOException e) {
							}
						}
					});
					
					thread.start();
					String sql = "update bs_flow set time=?,to_name=?,to_role_name=?,is_pass = ?,idea = ?" +
					" where object_id = ? and (type = '1' or type = '2')";
					Timestamp t = new Timestamp(new Date().getTime());
					bsFlowDao.executeSQL(sql,t,adminUser.getRealName(),
							adminUser.getBsRole().getRoleName(),
							status,advice,bookId+"");
				}
				
			}
			if(status==1){
				logger.info("审核电子书【"+be.getBookName()+"】通过");
				//更新流程
				String sql = "update bs_flow set time=?,to_name=?,to_role_name=?,is_pass = ?,idea = ?" +
						" where object_id = ? and (type = '1' or type = '2')";
				Timestamp t = new Timestamp(new Date().getTime());
				bsFlowDao.executeSQL(sql,t,adminUser.getRealName(),
						adminUser.getBsRole().getRoleName(),
						status,advice,bookId+"");
			}else if(status==3){
				logger.info("审核电子书【"+be.getBookName()+"】,属性不通过，退回");
				//属性不通过，退回
				String sql = "update bs_flow set is_pass = ?,idea = ?,time=? where object_id = ? and (type = '1' or type = '2')";
				Timestamp t1 = new Timestamp(new Date().getTime());
				bsFlowDao.executeSQL(sql,status,advice,t1,bookId+"");
				//发邮件
				final String operator = be.getOperator();
				final Map<String, Object> model = new HashMap<String, Object>();
				String hql_user="from BsAdminUser where auId=?";
				BsAdminUser bsAdminUser1 = bsAdminUserDao.findUniqueByHql(hql_user,be.getAuId());
				if(bsAdminUser1!=null){
					model.put("userName",operator);
					model.put("ebookName", be.getBookName());
					model.put("advice",advice);
					model.put("thisUser",adminUser.getRealName());
					final String userEmail = bsAdminUser1.getEmail();
					Thread thread = new Thread(
					new Runnable() {
						public void run() {
								thirdVelocityEmailUtil.sendEmail(model, "电子书审核回退", "ebookEmailVM.vm",new String[] {userEmail}, null);
						}
					});
					thread.start();
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#deleteEbookById(java.lang.Integer)
	 */
	public void deleteEbookById(Integer bookId){
		final String hql = "from BsEbook where id = ?";
		BsEbook be = bsEbookDao.findUniqueByHql(hql, bookId);
		if(null!=be){
			bsEbookDao.remove(be.getId());
			String sql = "delete from bs_flow where object_id = ? and (type= ? or type=?) and is_pass = ?";
			bsFlowDao.executeSQL(sql, bookId+"","1","2",0);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#addEbook(com.hcctech.bookshelf.pojo.BsEbook, com.hcctech.bookshelf.pojo.BsAdminUser)
	 */
	//添加电子书，更新电子书
	public void addEbook(BsEbook ebook, BsAdminUser adminUser,boolean isSecret) throws Exception {
		//ebook.setId(null);
		String ebookcode =null;
		String key =null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String date = format.format(new Date());
		boolean isadd = true;
		if(ebook.getBookCode()==null||ebook.getBookCode().trim().equals("")){
			//添加电子书
			ebook.setId(null);
			ebookcode = Md5.getMD5Str(String.valueOf(getUUID())).substring(8,24);
			ebook.setBookCode(ebookcode);
			//ebook.setVersion(1.0d);
			try {
				key = AESUtils.getSecretKey(Md5.getMD5Str(String.valueOf(getUUID())).substring(8,24));
			} catch (Exception e1) {
				//
			}
			ebook.setBookKey(key);
			saveOrUpdateEbook(ebook, adminUser, ebookcode, key, date, isadd,isSecret);
		}else{
			//更新电子书
			isadd = false;
			ebookcode = ebook.getBookCode();
			key = ebook.getBookKey();
			Double d = ebook.getVersion();
			int bookSize = ebook.getBookSize();
			ebook = bsEbookDao.get(ebook.getId());
			BsEbook ebook2 = new BsEbook(ebook);
			ebook2.setVersion(d);
			ebook2.setBookSize(bookSize);
			saveOrUpdateEbook(ebook2, adminUser, ebookcode, key, date, isadd,isSecret);
		}
		
	}

	private void saveOrUpdateEbook(BsEbook ebook, BsAdminUser adminUser,
			String ebookcode, String key, String date, boolean isadd,boolean isSecret) {
		if(!isSecret) {//如果不加密把key标记成NOENCRYPT以便加密程序判断
			key="NOENCRYPT";
		}
		String s=getRandom();
		String bookurl = ebook.getBookPath();
		String bookpath = ebook.getBookPath();
		if("01".equals(ebook.getBookType())) {//文科加上标记，解压程序判断
			s += "WENKE";
		}
		if(bookpath.endsWith("zip")) {
			bookurl = "/res/ebook/"+date+"/"+ebookcode+s;
			bookpath = "/res/ebook_zip/"+date+"/"+ebookcode+s+".zip";
			sendEncryptCommand(ebook.getBookPath(), bookurl, bookpath, key);
		}
		//设置实际名称
		String realFileName = ebook.getBookPath();
		//如果是文科并且zip上传，realname就固定为wenke_book.fcb 否则就是上传的名称
		if("01".equals(ebook.getBookType()) && bookpath.endsWith("zip")) {
		    realFileName = "wenke_book.fcb";
		}else {
    		realFileName = realFileName.replace("\\", "/");
    		if(realFileName.indexOf("/")!=-1 && !realFileName.endsWith("/")) {
    			realFileName = realFileName.substring((realFileName.lastIndexOf("/")+1));
    		}
		}
		ebook.setRealFileName(realFileName);
		ebook.setBookPath(bookpath);
		ebook.setBookUrl(bookurl);
		Timestamp timestamp = new Timestamp(new Date().getTime());
		ebook.setCreateTime(timestamp);
		ebook.setUploadTime(timestamp);
		ebook.setOperator(adminUser.getRealName());
		///=============//
		bsEbookDao.save(ebook);
		//==================//
		BsFlow flow = new BsFlow(timestamp, String.valueOf(ebook.getId()), ebook.getBookName(), isadd ?"1":"2", null, null, adminUser.getRealName(), adminUser.getBsRole().getRoleName(), isadd ?3:0, null);
		bsFlowDao.save(flow);
	}


	/**
	 * 发送解压->加密->打包 命令
	 * @param ebook
	 * @param bookurl
	 * @param bookpath
	 * @param key
	 */
	private void sendEncryptCommand(String path, String bookurl,
			String bookpath, String key) {
		try {
			UNZipServiceImplServiceStub implServiceStub = new UNZipServiceImplServiceStub();
			UnzipAndEncrypt unzipAndEncrypt2 = new UnzipAndEncrypt();
			unzipAndEncrypt2.setArg0(path);
			unzipAndEncrypt2.setArg1(bookurl);
			unzipAndEncrypt2.setArg2(bookpath);
			unzipAndEncrypt2.setArg3(key);
			UnzipAndEncryptE andEncryptE = new UnzipAndEncryptE();
			andEncryptE.setUnzipAndEncrypt(unzipAndEncrypt2);
			implServiceStub.startunzipAndEncrypt(andEncryptE, new UNZipServiceImplServiceCallbackHandler() {
			});
			UnzipAndEncryptResponseE responseE = implServiceStub.unzipAndEncrypt(andEncryptE);
			responseE.getUnzipAndEncryptResponse().get_return();
			//System.out.println(return_str);
		} catch (AxisFault e) {
		} catch (RemoteException e) {
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#loadEbook(java.lang.Integer)
	 */
	public BsEbook loadEbook(Integer bookId) {
			//--
		return bsEbookDao.get(bookId);
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.EBookService#loadEbookByUpdate(java.lang.Integer)
	 */
	//加载更新电子书
	public BsEbook loadEbookByUpdate(Integer bookId){
		BsEbook ebook = bsEbookDao.get(bookId);
		
		ebook.getBookCode();
		final String sql ="select count(flow_id) from bs_flow where is_pass='0' and object_id in (select bs.id from bs_ebook as bs where bs.book_code=? )";
		Object object = bsEbookDao.executeSQLbyRe(sql, ebook.getBookCode());
		if((Integer.valueOf(String.valueOf(object) ) )>0){
			return null;
		}
		return ebook;
	}
	//编辑电子书属性
	public boolean editEbookAttr(BsAdminUser adminUser , BsEbook bsEbook){
		boolean flag = true ;
		try {
			final String sql = "update bs_ebook set school_stage = ? ,grade_code = ? ,grade = ? ,subject = ? ,volume = ? ,apply_object = ?,edition = ? ,product_type = ? ,language = ?,publishing = ?,teach_version = ?,author = ?,page_number = ?,operator = ?,edu_version = ?,au_id = ? where id = ?" ;
			bsEbookDao.executeSQL(sql, bsEbook.getSchoolStage(),bsEbook.getGradeCode() ,bsEbook.getGrade(),
							bsEbook.getSubject(),bsEbook.getVolume(),bsEbook.getApplyObject(),
							bsEbook.getEdition(),bsEbook.getProductType(),bsEbook.getLanguage(),
							bsEbook.getPublishing(),bsEbook.getTeachVersion(),bsEbook.getAuthor(),
							bsEbook.getPageNumber(),adminUser.getRealName() ,bsEbook.getEduVersion(),
							adminUser.getAuId(),bsEbook.getId());
			final String flowSql = "update bs_flow set is_pass = 0,time = ? where object_id = ?" ;
			Timestamp timestamp = new Timestamp(new Date().getTime());
			bsFlowDao.executeSQL(flowSql, timestamp, bsEbook.getId());
		}catch(Exception e){
			flag = false ;
			e.printStackTrace();
		}
		return flag ;
	}
	public String getEbookVersion(String bookCode){
		String str="";
		if(bookCode!=null&&!"".equals(bookCode)){
			String sql="select max(version) from bs_ebook a ,bs_flow b where a.id = b.object_id and a.book_code=? and b.type in (1,2) and b.is_pass = 1 ";
			Double d=bsEbookDao.getEbookVersion(sql, bookCode);
			if(d!=null){
				DecimalFormat df = new DecimalFormat("#.00");
				str= df.format(d);
			}
		}
		return str;
	}
	/**
	 * 获得时间戳
	 * @return
	 */
	public static String getRandom(){
		SimpleDateFormat sdf = new SimpleDateFormat("ddhhmmss");
	    String nowdate = sdf.format(new Date());
	    nowdate=nowdate+Math.round(Math.random()*1000);
	    return nowdate;
	}
	public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    }
	
	public void setBsEbookDao(BsEbookDao bsEbookDao) {
		this.bsEbookDao = bsEbookDao;
	}
	
	public void setBsFlowDao(BsFlowDao bsFlowDao) {
		this.bsFlowDao = bsFlowDao;
	}

	public void setBsAdminUserDao(BsAdminUserDao bsAdminUserDao) {
		this.bsAdminUserDao = bsAdminUserDao;
	}

	public void setThirdVelocityEmailUtil(
			ThirdVelocityEmailUtil thirdVelocityEmailUtil) {
		this.thirdVelocityEmailUtil = thirdVelocityEmailUtil;
	}
	
	public static void main(String[] args) {
		System.out.println(String.valueOf(false));
	}
	

}
