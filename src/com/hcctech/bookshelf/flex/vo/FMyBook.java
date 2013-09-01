package com.hcctech.bookshelf.flex.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.hcctech.bookshelf.util.DomainUtil;

public class FMyBook implements Serializable {
	private static final long serialVersionUID = -4297398840258454944L;
	
	private Integer mybookId ;		//电子书ID		mybook
	private Integer goodsId;
	private Timestamp addTime;		//购买时间		mybook
	private Timestamp deadline;		//截止日期		mybook
	private Integer downNumbers;	//剩余下载次数	mybook
	private Integer total ;			//总次数			mybook
	private String bookCode ;		//电子书代码		product
	private String bookName ;		//电子书名称		product
	private Integer schoolStage;	//学段			product
	private String thumbnail;		//缩略图			product
	private String subject ;		//科目			product
	private Double version ;		//版本			ebook
	private Integer bookSize ; 		//电子书大小		ebook
	private String bookType ; 		//电子书类型    01 文科教材  02理科教材
	private String realFileName;    //电子书实际名称
	private String grade;			//年级
	private String grade_code;      //年级编号

	
	public FMyBook() {
		
	}
	public FMyBook(Integer mybookId) {
		this.mybookId = mybookId;
	}
	
	public FMyBook(String bookCode, Double version) {
		super();
		this.bookCode = bookCode;
		this.version = version;
	}
	public FMyBook(Integer mybookId,Integer goodsId, Timestamp addTime, Timestamp deadline,
			Integer downNumbers, String bookCode, String bookName,
			Integer schoolStage, String thumbnail, String subject,
			Double version, Integer bookSize,String bookType,String grade) {
		super();
		this.goodsId = goodsId;
		this.mybookId = mybookId;
		this.addTime = addTime;
		this.deadline = deadline;
		this.downNumbers = downNumbers;
		this.bookCode = bookCode;
		this.bookName = bookName;
		this.schoolStage = schoolStage;
		this.thumbnail = thumbnail;
		this.subject = subject;
		this.version = version;
		this.bookSize = bookSize;
		this.bookType = bookType;
		this.grade = grade;
	}
	public Integer getMybookId() {
		return mybookId;
	}
	public void setMybookId(Object mybookId) {
		if(mybookId != null) {
			setMybookId(Integer.parseInt(mybookId.toString()));
		}
	}
	public void setMybookId(Integer mybookId) {
		this.mybookId = mybookId ;
	}
	public String getBookCode() {
		return bookCode;
	}
	public void setBookCode(Object bookCode) {
		setBookCode(String.valueOf(bookCode));
	}
	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(Object bookName) {
		if(bookName != null) {
			setBookName(String.valueOf(bookName));
		}
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Integer getBookSize() {
		return bookSize;
	}
	public void setBookSize(Object bookSize) {
		if(bookSize != null) {
			setBookSize(Integer.parseInt(bookSize.toString()));
		}
	}
	public void setBookSize(Integer bookSize) {
		this.bookSize = bookSize;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(Object subject) {
		if(subject != null) {
			setSubject(String.valueOf(subject));
		}
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Object addTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			setAddTime(new Timestamp(sdf.parse(String.valueOf(addTime)).getTime()));
		} catch (ParseException e) {
		}
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Timestamp getDeadline() {
		return deadline;
	}
	public void setDeadline(Object deadline) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			setDeadline(new Timestamp(sdf.parse(String.valueOf(addTime)).getTime()));
		} catch (ParseException e) {
		}
	}
	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}
	public Integer getSchoolStage() {
		return schoolStage;
	}
	public void setSchoolStage(Object schoolStage) {
		if(schoolStage != null) {
			setSchoolStage(Integer.parseInt(schoolStage.toString()));
		}
	}
	public void setSchoolStage(Integer schoolStage) {
		this.schoolStage = schoolStage;
	}
	public String getThumbnail() {
		return thumbnail;
	}
//	public void setThumbnail(String fileDomain,Object thumbnail) {
//		if(thumbnail != null) {
//			try {
//				String data = Base64Utils.encodeImageByUrl(fileDomain+thumbnail.toString());
//				setThumbnail(data);
//			} catch (Exception e) {
//			}
//		}
//	}
	public void setThumbnail(String thumbnail) {
	//	System.err.println(DomainUtil.getFileDomain() + thumbnail);
		this.thumbnail = DomainUtil.getFileDomain() + thumbnail;
	}
	public Integer getDownNumbers() {
		return downNumbers;
	}
	public void setDownNumbers(Object downNumbers) {
		if(downNumbers != null) {
			setDownNumbers(Integer.parseInt(downNumbers.toString()));
		}
	}
	public void setDownNumbers(Integer downNumbers) {
		this.downNumbers = downNumbers;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Object total) {
		if(total != null){
			setTotal(Integer.parseInt(total.toString()));
		}
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(Object version) {
		
		if(version != null && !"".equals(version)){
			String s = String.valueOf(version.toString());
			int p = s.indexOf("@");
			if(p!=-1){
				s = s.substring(0,p);
			}
			setVersion(Double.parseDouble(s));
		}
		
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	public String getRealFileName() {
		return realFileName;
	}
	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGrade_code() {
		return grade_code;
	}
	public void setGrade_code(String grade_code) {
		this.grade_code = grade_code;
	}
	
	
	

}
