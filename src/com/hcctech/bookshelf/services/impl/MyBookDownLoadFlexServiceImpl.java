package com.hcctech.bookshelf.services.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.commons.lang3.StringUtils;

import com.hcctech.bookshelf.dao.BsEbookDao;
import com.hcctech.bookshelf.dao.BsMyBookDao;
import com.hcctech.bookshelf.dao.BsUserDrmDao;
import com.hcctech.bookshelf.pojo.BsEbook;
import com.hcctech.bookshelf.pojo.BsMybook;
import com.hcctech.bookshelf.pojo.BsUserDrm;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.MyBookDownLoadFlexService;
import com.hcctech.bookshelf.util.DomainUtil;
import com.hcctech.bookshelf.util.Md5;

/**
 * 我的电子书下载
 * @author apple
 *
 */
public class MyBookDownLoadFlexServiceImpl implements MyBookDownLoadFlexService{
	private BsUserDrmDao bsUserDrmDao;
	private BsMyBookDao bsMyBookDao;
	private BsEbookDao bsEbookDao;
	
	/**
	 * 下载我的电子书
	 * @param cpuIdStr 加密后的cpuId
	 * @param user  用户
	 * @param myBookId  要下载的我的电子书Id
	 * @return
	 */
	public boolean myBookDownLoadValidate(String cpuIdStr,BsWebUser user,int myBookId){
		boolean flag=false;
		if(myBookId<=0){
			return flag;
		}
		if(cpuIdStr==null||"".equals(cpuIdStr.trim())){
			return flag;
		}
		byte[] bytearr= Base64Utils.decode(cpuIdStr);
		String cpuId=new String(bytearr);
		//判断用户是否为第一次下载
		String hql1="from BsUserDrm b where b.bsMybook.mybookId=? and b.bsWebUser.wuId=? and b.cpuId=?";
		BsUserDrm bsUserDrm=bsUserDrmDao.findUniqueByHql(hql1, myBookId,user.getWuId(),cpuId);
		BsMybook bsMybook=null;
		if(bsUserDrm!=null){
			//不是第一次下载    判断下载次数
			bsMybook=bsUserDrm.getBsMybook();
			if(bsMybook!=null&&bsMybook.getDownNumbers()>0){
				//System.out.println("可以下载");
				//下载完成
				//bsUserDrm.getBsMybook().setDownNumbers(bsMybook.getDownNumbers()-1);
			}else{
				return flag;
			}
		}else{
			//第一次下载   
			String hql2="from BsMybook b where b.mybookId=? ";
			bsMybook=bsMyBookDao.findUniqueByHql(hql2, myBookId);
			if(bsMybook!=null){
				System.out.println("可以下载了");
				//下载完成
				bsMybook.setDownNumbers(bsMybook.getDownNumbers()-1);
				BsUserDrm bsUserDrm1=new BsUserDrm();
				bsUserDrm1.setBsMybook(bsMybook);
				bsUserDrm1.setBsWebUser(user);
				bsUserDrm1.setCpuId(cpuId);
				bsUserDrm1.setOperateTime(new Timestamp(System.currentTimeMillis()));
				bsMyBookDao.save(bsUserDrm1);
			}else{
				return flag;
			}
		}
		flag=true;
		return flag;
	}
	
	/**
	 * 下载电子书之前的验证
	 * flag 0:没有权限下载
	 * flag 1：可以下载
	 * flag 2：以前下载过，下载不再消耗次数
	 */
	public int downLoadValidate(String cpuIdStr, BsWebUser user,
			int myBookId) {
		int flag = 0;
		if(myBookId<=0||cpuIdStr==null||"".equals(StringUtils.trim(cpuIdStr))||user==null)
			return 0;
		final String hql1 = "from BsUserDrm b where b.bsMybook.mybookId=? and b.bsWebUser.wuId=? and b.cpuId=?";
		BsUserDrm bsUserDrm=bsUserDrmDao.findUniqueByHql(hql1, myBookId,user.getWuId(),cpuIdStr);
		if(bsUserDrm!=null){
			//该PC&pad 已经授权过，可以再次下载
			flag = 2;
		}else{
			BsMybook bsMybook = bsMyBookDao.get(myBookId);
			boolean d = false;
			if(bsMybook.getDeadline()==null)
				d = true;
			else if(bsMybook.getDeadline().getTime() > new Date().getTime())
				d = true;
			if(bsMybook.getDownNumbers()<=0 )
			{
				//表示数量用完
				return 3;
			}
			if(!d) {
				//表示过期
				return 4;
			}
			if(bsMybook.getDownNumbers()>0 && d){
				//表示正常
				flag = 1;
			}
		}
		return flag;
	}
	
	
	public Map<String,String> getDownloadEbookInfo(int bookId,String deviceName){
		String bookCode = null;
		
		BsEbook ebook = bsEbookDao.findUniqueByHql("from BsEbook b where b.id=?", bookId);//.get(myBookId);
		Map<String, String> flag = null;
		if(ebook!=null){
			String bookkey =ebook.getBookKey();
			String sign = Md5.getMD5Str(bookkey);
			//下载地址
			flag = new HashMap<String, String>();
			String bookPath = ebook.getBookPath();
			//如果是文科，并且是zip文件就返回解压后的根目录，非zip的文科还是返回原来的地址
			if("01".equals(ebook.getBookType()) && ebook.getBookPath().endsWith("zip")) {//文科
				bookPath = ebook.getBookUrl();
				//根据设备返回对应文件
				if(StringUtils.isNotBlank(deviceName)) {
		            if(deviceName.equals("pc")) {
		                bookPath +="/wenke_book.zpk";
		            }else {
		                bookPath +="/wenke_book.fcb";
		            }
		        }
			}
			flag.put("path", DomainUtil.getFileDomain()+bookPath+"?sign="+sign);
			flag.put("key", bookkey);
			flag.put("sign", sign);
			flag.put("bookcode", ebook.getBookCode());
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//			if(bsMybook.getDeadline()!=null)
//				flag.put("deadline", dateFormat.format(bsMybook.getDeadline()));
//			else
				flag.put("deadline", "2100-01-01");
		
		}
		return flag;
	}
	/**
	 * 下载电子书
	 * 返回下载地址
	 * @return
	 */
	public Map<String, String> downloadEbook(String cpuIdStr, BsWebUser user,int myBookId,String deviceName ){
		String bookCode = null;
		if(myBookId<=0||cpuIdStr==null||"".equals(StringUtils.trim(cpuIdStr))||user==null)
			return null;
		BsMybook bsMybook = bsMyBookDao.get(myBookId);
		boolean d = false;
		if(bsMybook!=null){//验证是否过期
			if(bsMybook.getDeadline()==null)
				d = true;
			else if(bsMybook.getDeadline().getTime() > new Date().getTime())
				d = true;
		}
		
		final String hql1 = "from BsUserDrm b where b.bsMybook.mybookId=? and b.bsWebUser.wuId=? and b.cpuId=?";
		BsUserDrm bsUserDrm=bsUserDrmDao.findUniqueByHql(hql1, myBookId,user.getWuId(),cpuIdStr);
		if(bsUserDrm!=null){
			//该PC&pad 已经授权过，可以再次下载
			if(d)
				bookCode=bsUserDrm.getBsMybook().getBsProducts().getBookCode();
			//flag = DomainUtil.getFileDomain() ;//;
			//Signature
		}else{
			if(bsMybook.getDownNumbers()>0 && d){
				bookCode = bsMybook.getBsProducts().getBookCode();
				
				bsMybook.setDownNumbers(bsMybook.getDownNumbers()-1);//减少下载次数
				//记录drm
				BsUserDrm bsUserDrm1=new BsUserDrm();
				bsUserDrm1.setBsMybook(bsMybook);
				bsUserDrm1.setBsWebUser(user);
				bsUserDrm1.setCpuId(cpuIdStr);
				bsUserDrm1.setOperateTime(new Timestamp(System.currentTimeMillis()));
				bsMyBookDao.save(bsMybook);
				bsMyBookDao.save(bsUserDrm1);
			}
		}
		Map<String, String> flag = null;
		if(bookCode!=null){
			final String ghql = "SELECT be from BsEbook be,BsFlow flow where be.bookCode = ? and be.id=flow.objectId and flow.type in (1,2) and flow.isPass = 1 order by be.version desc";
			List<BsEbook> ebooks = bsEbookDao.findByHql(ghql, bookCode);
			if(ebooks!=null&&!ebooks.isEmpty()){
				BsEbook ebook = ebooks.get(0);
				String bookkey =ebook.getBookKey();
				String sign = Md5.getMD5Str(bookkey);
				//下载地址
				flag = new HashMap<String, String>();
				String bookPath = ebook.getBookPath();
				//如果是文科，并且是zip文件就返回解压后的根目录，非zip的文科还是返回原来的地址
				if("01".equals(ebook.getBookType()) && ebook.getBookPath().endsWith("zip")) {//文科
					bookPath = ebook.getBookUrl();
					//根据设备返回对应文件
					if(StringUtils.isNotBlank(deviceName)) {
			            if(deviceName.equals("pc")) {
			                bookPath +="/wenke_book.zpk";
			            }else {
			                bookPath +="/wenke_book.fcb";
			            }
			        }
				}
				flag.put("path", DomainUtil.getFileDomain()+bookPath+"?sign="+sign);
				flag.put("key", bookkey);
				flag.put("sign", sign);
				flag.put("bookcode", bookCode);
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				if(bsMybook.getDeadline()!=null)
					flag.put("deadline", dateFormat.format(bsMybook.getDeadline()));
				else
					flag.put("deadline", "2100-01-01");
			}
		}
		return flag;
	}
	
	public List<BsEbook> checkUpdate(Map<String, Double> map) {
		
		StringBuffer sql = new StringBuffer("select * from bs_ebook where book_code in (");
		for (String code : map.keySet()) {
			sql.append("'"+code+"'");
			sql.append(",");
		}
		sql.append("'')  order by book_code");
		List<BsEbook> myBooks = bsEbookDao.findBySql(sql.toString());
		Map<String, BsEbook> bookmap = new HashMap<String, BsEbook>();
		for (BsEbook bsEbook : myBooks) {
			if(bsEbook.getVersion()>map.get(bsEbook.getBookCode())){
				bookmap.put(bsEbook.getBookCode(), bsEbook);
			}
		}
		
		return new ArrayList<BsEbook>(bookmap.values());
	}

	
	
	public void setBsUserDrmDao(BsUserDrmDao bsUserDrmDao) {
		this.bsUserDrmDao = bsUserDrmDao;
	}
	public void setBsMyBookDao(BsMyBookDao bsMyBookDao) {
		this.bsMyBookDao = bsMyBookDao;
	}

	public void setBsEbookDao(BsEbookDao bsEbookDao) {
		this.bsEbookDao = bsEbookDao;
	}

	
	
}
