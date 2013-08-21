package com.hcctech.bookshelf.flex;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hcctech.bookshelf.flex.vo.FMyBook;
import com.hcctech.bookshelf.pojo.BsEbook;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.MyBookDownLoadFlexService;
import com.hcctech.bookshelf.util.AESUtils;
import com.hcctech.bookshelf.util.Base64Utils;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

public class MyBookDownLoadFlex{
	/**
	 * 我的电子书下载
	 * @return
	 */
	private MyBookDownLoadFlexService myBookDownLoadFlexService;
	public int downLoadValidate(int myBookId,String cpuIdStr){
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser user=(BsWebUser)session.getAttribute("user");
		if(user==null||user.getWuId()==null){
			return 0;
		}
		int flag=myBookDownLoadFlexService.downLoadValidate(cpuIdStr, user, myBookId);
		return flag;
	}
	
	/**
	 * @param myBookId
	 * @param cpuIdStr
	 * @return
	 */
	public List<String> download(int myBookId,String cpuIdStr){
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser user=(BsWebUser)session.getAttribute("user");
		if(user==null||user.getWuId()==null){
			return null;
		}
		Map<String, String> map = myBookDownLoadFlexService.downloadEbook(cpuIdStr, user, myBookId);
		//
		if(map==null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append( map.get("path"));
		String str = cpuIdStr+"<,>"+user.getWuId()+"<,>"+myBookId;
		byte[] byt = null;
		String testxx = null;
		String secretStr =null;
		try {
			String key =  "JQy4loN+iEaybInzfBFGTw==";
			byt = AESUtils.encrypt(str.getBytes(), key);
			buffer.append("&kongfu=");
			String str11 = Base64Utils.encode(byt);
			testxx = str11;
			str11 = URLEncoder.encode(str11, "UTF-8");
			buffer.append(str11);
			
			
			//String secret = map.get("bookcode")+"-++-++-"+map.get("key")+"-++-++-"+map.get("deadline")+"-++-++-"+user.getWuEmail();
			StringBuffer secret = new StringBuffer();
			secret.append(map.get("bookcode"));
			secret.append("-++-++-");
			secret.append(map.get("key"));
			secret.append("-++-++-");
			secret.append(map.get("deadline"));
			secret.append("-++-++-");
			secret.append(user.getWuEmail());
			secret.append("-++-++-");
			secret.append(myBookId);
			secret.append("-++-++-");
			secret.append(cpuIdStr);
			
			String key1 = generateKey(myBookId, cpuIdStr, user);
			byte[] bs = AESUtils.encrypt(secret.toString().getBytes(),key1 );
			secretStr = Base64Utils.encode(bs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		List<String> arr = new ArrayList<String>();
		arr.add(buffer.toString());
		arr.add(secretStr);
		arr.add(testxx);
		return arr;
	}
	
	/**
	 * @param myBookId
	 * @param cpuIdStr
	 * @return
	 */
	public String download2(int myBookId,String cpuIdStr){
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser user=(BsWebUser)session.getAttribute("user");
		if(user==null||user.getWuId()==null){
			return null;
		}
		Map<String, String> map = myBookDownLoadFlexService.downloadEbook(cpuIdStr, user, myBookId);
		//
		StringBuffer buffer = new StringBuffer();
		buffer.append( map.get("path"));
		String str = cpuIdStr+"<,>"+user.getWuId()+"<,>"+myBookId;
		byte[] byt = null;
		String testxx = null;
		String secretStr =null;
		try {
			String key =  "JQy4loN+iEaybInzfBFGTw==";
			byt = AESUtils.encrypt(str.getBytes(), key);
			buffer.append("&kongfu=");
			String str11 = Base64Utils.encode(byt);
			testxx = str11;
			str11 = URLEncoder.encode(str11, "UTF-8");
			buffer.append(str11);
			
			
			//String secret = map.get("bookcode")+"-++-++-"+map.get("key")+"-++-++-"+map.get("deadline")+"-++-++-"+user.getWuEmail();
			StringBuffer secret = new StringBuffer();
			secret.append(map.get("bookcode"));
			secret.append("-++-++-");
			secret.append(map.get("key"));
			secret.append("-++-++-");
			secret.append(map.get("deadline"));
			secret.append("-++-++-");
			secret.append(user.getWuEmail());
			secret.append("-++-++-");
			secret.append(myBookId);
			secret.append("-++-++-");
			secret.append(cpuIdStr);
			
			String key1 = generateKey(myBookId, cpuIdStr, user);
			byte[] bs = AESUtils.encrypt(secret.toString().getBytes(),key1 );
			secretStr = Base64Utils.encode(bs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
//		List<String> arr = new ArrayList<String>();
//		arr.add(buffer.toString());
//		arr.add(secretStr);
//		arr.add(testxx);
		return secretStr;
	}

	
	/**
	 * 检查更新
	 * @param codes
	 * @return
	 */
	public List<BsEbook> checkUpdate(String codes){
		Map<String, Double> map = new HashMap<String, Double>();
		String[] bookstr = codes.split(";");
		for (int i = 0; i < bookstr.length; i++) {
			String[] temp = bookstr[i].split(",");
			map.put(temp[0], Double.valueOf(temp[1]));
		}
		List<BsEbook> updates = myBookDownLoadFlexService.checkUpdate(map);
		return updates;
	}
	
	private String generateKey(int myBookId, String cpuIdStr, BsWebUser user)
			throws UnsupportedEncodingException, Exception {
		byte[] keyb = (cpuIdStr+myBookId+user.getWuEmail()).getBytes("UTF-8");
		byte[] kb = new byte[16];
		if(keyb.length>16){
			for (int i = 0; i < 8; i++) {
				kb[i] = keyb[i];
			}
			for (int i = keyb.length-1,j=8; i >  keyb.length-9; i--,j++) {
				kb[j] = keyb[i];
			}
		}
		return Base64Utils.encode(kb);
	}
	public void setMyBookDownLoadFlexService(MyBookDownLoadFlexService myBookDownLoadFlexService) {
		this.myBookDownLoadFlexService = myBookDownLoadFlexService;
	}
	
	
}
