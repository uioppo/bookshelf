package com.hcctech.bookshelf.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * @author randyjie
 *	授权码生成
 */
public class LicenseKeyUtil {

	/**
	 * 生成授权码=+    
	 * @param num    授权码数量
	 * @return
	 */
	public static List<String> getLicenKey(int num){
		List<String> list = null;
		final String s ="-";
		if(num>0){
			list = new ArrayList<String>();
			for (int i = 0; i < num; i++) {
				String _t = Md5.getMD5Str(String.valueOf(getUUID())).substring(8,24);
				list.add(_t.substring(0,4)+s+_t.substring(4,8)+s+_t.substring(8,12)+s+_t.substring(12,16));
			}
		}
		
		return list;
	}
	
	
	/**自定义进制(0,1没有加入,容易与o,l混淆)*/
	private static final char[] r = new char[] { 'Q', 'W', 'E', '8', 'A', 'S', '2', 'D', 'Z', 'X', '9', 'C', '7', 'P', 'O', '5', 'I', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'L', 'T', 'N', '6', 'B', 'G', 'H' };
	/**自动补全组(不能与自定义进制有重复)*/
	private static final char[] b = new char[] { 'Q', 'W', 'E', 'A', 'S', 'D', 'Z', 'X', 'C', 'P', 'O', 'I', 'K', 'M', 'J', 'U', 'F', 'R', 'V', 'Y', 'T', 'N', 'B', 'G', 'H' };
	/**进制长度*/
	private static final int l = 34;
	/**序列最小长度*/
	private static final int s = 16;
	
	public static String generateLicenKey(Integer radix){
		
		String key = String.valueOf(radix);
		StringBuffer sb = new StringBuffer();
		 Random rnd = new Random();
		if(key.length()<8){
			for (int i = 0; i < 8 - key.length(); i++) {
				  sb.append(b[rnd.nextInt(24)]);
			}
		}
		sb.append(key);
		char[] dkey = sb.toString().toCharArray();
		StringBuffer keysb = new StringBuffer();
		sb = null;
		for (int i = 0; i < dkey.length; i++) {
			keysb.append(dkey[i]);
			keysb.append(r[rnd.nextInt(33)]);
		}
		dkey =keysb.toString().toCharArray();
		keysb = new StringBuffer();
		for (int i = 0; i < dkey.length; i++) {
			if(i!=0&&i%4==0)
				keysb.append("-");
			keysb.append(dkey[i]);
		}
		//
		return keysb.toString();
	}
	
	
	/**
	 * @param type 类型
	 * @param areaCode 
	 * @param pattern
	 * @param versions
	 * @param grade
	 * @param subject
	 * @param sheets
	 * @param date
	 * @param edition
	 * @param num
	 * @return
	 */
	public static String generateSN(int type,String areaCode,String pattern,String versions,String grade,String subject,String sheets,Date date,String edition,int num){
		char typeStr = 'A';//数字教材：1
		if(type==0)typeStr='C';//数字教辅:0
		if(type==2)typeStr='B';//数字教参：2
		if(areaCode==null || areaCode.equals("")) {
			areaCode = "000";
		}
		if(areaCode.length()>3)
			areaCode=areaCode.substring(areaCode.length()-3);
		if(sheets.equals("上册"))
			sheets = "1";
		else if(sheets.equals("下册"))
			sheets = "2";
		else
			sheets = "0";
		
		SimpleDateFormat dateFormat =new SimpleDateFormat("yy");
		String datestr = dateFormat.format(date);//年份
		String numStr = String.valueOf(num);
		String _temp = "";
		if(numStr.length()<8){
			for (int i = 0; i < 8-numStr.length(); i++) {
				_temp +="0";
			}
		}
		numStr = _temp + numStr;
		StringBuffer buffer = new StringBuffer();
		buffer.append(typeStr);//A-数字教材B-数字教参C-数字教辅
		buffer.append(areaCode);//地区编号后三位
		buffer.append(" ");//分隔
		buffer.append(pattern);//渠道：A-出版社B-新华书店C-省代D-市代E-区代F-oemZ-其他渠道
		buffer.append("");//渠道目标对象1-教育局2-学校3-培训机构
		buffer.append(versions);//版本:T-体验版F-分章版P-普通版H-合订版G-高级版Z-至尊版
		buffer.append(" ");//分隔
		buffer.append(grade);//年级
		buffer.append(subject);//科目
		buffer.append(" ");//分隔
		buffer.append(sheets);//册数
		buffer.append(datestr);//教材年份后两位
		buffer.append(edition);//教材版本：人教版-1北师大版-2苏教版-3山教版-4北教版-5外研社-6其他-Z
		buffer.append(" ");//分隔
		buffer.append(numStr);//8位序号
		return buffer.toString();
	}
	
	public static String toSerialNumber(long num) {
	   char[] buf = new char[32];
	   int charPos = 32;
	
	   while ((num / l) > 0) {
	       buf[--charPos] = r[(int) (num % l)];
	       num /= l;
	   }
	   buf[--charPos] = r[(int) (num % l)];
	   String str = new String(buf, charPos, (32 - charPos));
	   //不够长度的自动随机补全
	   if (str.length() < s) {
	       StringBuffer sb = new StringBuffer();
	       Random rnd = new Random();
	       for (int i = 0; i < s - str.length(); i++) {
	           sb.append(b[rnd.nextInt(24)]);
	       }
	       str += sb.toString();
	   }
	   return str;
	}
	public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    }
}
