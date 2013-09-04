package com.hcctech.bookshelf.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.hcctech.bookshelf.pojo.BsProducts;

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
	
	
	/**自定义进制(o,l没有加入,容易与0,1混淆)*/
	private static final char[] r = new char[] { 'Q', 'W', 'E', '8', 'A', 'S', '2', 'D', 'Z', '0', 'X', '9', 'C', '7', 'P', '5', 'K', '3', 'M', 'J', '1', 'U', 'F', 'R', '4', 'V', 'Y', 'L', 'T', 'N', '6', 'B', 'G', 'H' };
	/**自动补全组(不能与自定义进制有重复)*/
	private static final char[] b = new char[] { 'Q', 'W', 'E', 'A', 'S', 'D', 'Z', 'X', 'C', 'P', 'K', 'M', 'J', 'U', 'F', 'R', 'V', 'Y', 'T', 'N', 'B', 'G', 'H' };
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
				  sb.append(b[rnd.nextInt(22)]);
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
	private enum GradeEnum{
	    grage_1(0x0001,"一年级"),//1年级
	    grage_2(0x0002,"二年级"),//2年级
	    grage_3(0x0004,"三年级"),//3年级
	    grage_4(0x0008,"四年级"),//4年级
	    grage_5(0x0010,"五年级"),//5年级
	    grage_6(0x0020,"六年级"),//6年级
	    grage_7(0x0040,"初一"),//7年级
	    grage_8(0x0080,"初二"),//8年级
	    grage_9(0x0100,"初三"),//9年级
	    grage_10(0x0200,"高一"),//10年级
	    grage_11(0x0400,"高二"),//11年级
	    grage_12(0x0800,"高三"),//12年级
	    grage_13(0x1000,"学前");//学前
	    
	    private Integer value;
	    private String nameStr;
	    
	    private GradeEnum(Integer value,String nameStr) {
	        this.value=value;
	        this.nameStr = nameStr;
	    }
	    
	}
	public static String generateSN(Set<BsProducts> bsProducts,String areaCode,String pattern,String patternTarget,String versions,String subject,String edition,int num){
		if(bsProducts == null || bsProducts.size()<=0) {
		    return "";
		}
	    char typeStr = 'A';//数字教材：1
		int type = bsProducts.iterator().next().getProductType();
		if(type==0)typeStr='C';//数字教辅:0
		if(type==2)typeStr='B';//数字教参：2
		if(areaCode==null || areaCode.equals("")) {
			areaCode = "000";
		}
		if(areaCode.length()>3)
			areaCode=areaCode.substring(areaCode.length()-3);
		Integer sheets = 0;
		Integer grade = 0;
		Integer subject_int = 0;
		for(BsProducts products : bsProducts)
        {
		    if(products.getVolume().equals("上册"))
	            sheets += 1;
	        else if(products.getVolume().equals("下册"))
	            sheets += 2;
	        else
	            sheets += 0;
		    if(StringUtils.isNotBlank(products.getGradeCode())) {
		        Integer grade_code = Integer.valueOf(products.getGradeCode());
		        switch(grade_code) {
		            case 1: grade +=0x0001;
		                    break;
		            case 2: grade +=0x0002;
		                    break;
		            case 3: grade +=0x0004;
		                    break;
		            case 4: grade +=0x0008;
                            break;
                    case 5: grade +=0x0010;
                            break;
                    case 6: grade +=0x0020;
                            break;
                    case 7: grade +=0x0040;
                            break;
                    case 8: grade +=0x0080;
                            break;
                    case 9: grade +=0x0100;
                            break;
                    case 10: grade +=0x0200;
                            break;
                    case 11: grade +=0x0400;
                            break;
                    case 12: grade +=0x0800;
                            break;
                    case 13: grade +=0x1000;
                            break;
                    default:grade +=0;
		        }
		    }
		    if(StringUtils.isNotBlank(products.getSubject())) {
		    	if(products.getSubject().equals("通识")) {
		    		subject_int += 0x00001;
		    	}else if(products.getSubject().equals("政治/思想品德")) {
		    		subject_int += 0x00002;
		    	}else if(products.getSubject().equals("语文")) {
		    		subject_int += 0x00004;
		    	}else if(products.getSubject().equals("数学")) {
		    		subject_int += 0x00008;
		    	}else if(products.getSubject().equals("物理")) {
		    		subject_int += 0x00010;
		    	}else if(products.getSubject().equals("化学")) {
		    		subject_int += 0x00020;
		    	}else if(products.getSubject().equals("生物")) {
		    		subject_int += 0x00040;
		    	}else if(products.getSubject().equals("历史")) {
		    		subject_int += 0x00080;
		    	}else if(products.getSubject().equals("地理")) {
		    		subject_int += 0x00100;
		    	}else if(products.getSubject().equals("音乐")) {
		    		subject_int += 0x00200;
		    	}else if(products.getSubject().equals("体育")) {
		    		subject_int += 0x00400;
		    	}else if(products.getSubject().equals("美术")) {
		    		subject_int += 0x00800;
		    	}else if(products.getSubject().equals("科学")) {
		    		subject_int += 0x01000;
		    	}else if(products.getSubject().equals("其他")) {
		    		subject_int += 0x02000;
		    	}else if(products.getSubject().equals("英语PEP")) {
		    		subject_int += 0x10000;
		    	}else if(products.getSubject().equals("英语新起点")) {
		    		subject_int += 0x20000;
		    	}else if(products.getSubject().equals("英语精通")) {
		    		subject_int += 0x40000;
		    	}
		    }
		    
        }
		String grade_str = Integer.toHexString(grade);
		if(grade_str.length()<4) {
		    int other = 4-grade_str.length();
		    for(int i = 0; i < other; i++)
            {
                grade_str = "0" + grade_str;
            }
		}
		String subject_str = Integer.toHexString(subject_int);
		if(subject_str.length()<5) {
			for(int i = 0; i < 5-subject_str.length(); i++)
            {
				subject_str = "0" + subject_str;
            }
		}
		String edition_str = "Z";
		if(StringUtils.isNotBlank(edition)) {
			if(edition.equals("人教版")) {
				edition_str = "1";
			}else if(edition.equals("北师大版")) {
				edition_str = "2";
			}else if(edition.equals("苏教版")) {
				edition_str = "3";
			}else if(edition.equals("山教版")) {
				edition_str = "4";
			}else if(edition.equals("北教版")) {
				edition_str = "5";
			}else if(edition.equals("外研社")) {
				edition_str = "6";
			}else if(edition.equals("其他")) {
				edition_str = "Z";
			}
		}
		SimpleDateFormat dateFormat =new SimpleDateFormat("yy");
		String datestr = dateFormat.format(bsProducts.iterator().next().getPublishTime());//年份
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
		buffer.append(patternTarget);//渠道目标对象1-教育局2-学校3-培训机构
		buffer.append(versions);//版本:T-体验版F-分章版P-普通版H-合订版G-高级版Z-至尊版
		buffer.append(" ");//分隔
		buffer.append(grade_str);//年级
		buffer.append(subject_str);//科目
		buffer.append(" ");//分隔
		buffer.append(sheets);//册数
		buffer.append(datestr);//教材年份后两位
		buffer.append(edition_str);//教材版本：人教版-1北师大版-2苏教版-3山教版-4北教版-5外研社-6其他-Z
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
	           sb.append(b[rnd.nextInt(22)]);
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
	
	
	public static void main(String[] args)
    {
        int a = 0x0020;
        int c = 0x0040;
        int b = 0x0080;
        System.out.println(Integer.toHexString(a+b+c));
	    
    }
}
