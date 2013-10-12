package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BsEbook entity. @author MyEclipse Persistence Tools
 */

public class BsEbook implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7842943758005665458L;
	private Integer id;
	private String bookCode;
	private String bookName;
	private String bookPath;
	private Integer bookSize;
	private Timestamp createTime;
	private Timestamp uploadTime;
	private Double version;			
	private String bookKey;
	private String bookUrl;
	private String operator;
	private String subject;	//科目
	private String serVersion ;
	private String eduVersion ;		//教材版本
	private String versionStr;
	private String teachVersion ; //对应教材版本
	private Integer schoolStage;  //学段
	private String grade;		  //年级
	private String volume;		  //册
	private Integer productType;  //商品类型
	private String applyObject;	  //使用对象
	private String language;	  //语种
	private String publishing;    //出版社
	private String author;		  //开发者
	private Integer pageNumber;	  //页数
	private String edition;       //版本
	private String gradeCode;     //年级代码
	
	private Integer auId;		 //后台用户ID
	
	private Timestamp operatorTime;//查询记录操作时间 
	
	private String bookType;//电子书类型
	private String bookType_name; //电子书类型名称
	private String realFileName;//实际名称
	private String secret; //是否加密false:不加密，true：加密
	// Constructors

	/** default constructor */
	public BsEbook() {
	}

	/** minimal constructor */
	public BsEbook(String bookCode) {
		this.bookCode = bookCode;
	}
	

	/** full constructor */
	public BsEbook(String bookCode, String bookName, String bookPath,
			Integer bookSize, Timestamp createTime, Timestamp uploadTime,
			Double version, String bookKey, String bookUrl, String operator) {
		this.bookCode = bookCode;
		this.bookName = bookName;
		this.bookPath = bookPath;
		this.bookSize = bookSize;
		this.createTime = createTime;
		this.uploadTime = uploadTime;
		this.version = version;
		this.bookKey = bookKey;
		this.bookUrl = bookUrl;
		this.operator = operator;
	}

	// Property accessors

	public BsEbook(BsEbook ebook,Date datetime) {
		this.id = ebook.getId();
		this.bookCode = ebook.getBookCode();
		this.bookName = ebook.getBookName();
		this.bookPath = ebook.getBookPath();
		this.bookSize = ebook.getBookSize();
		this.createTime = ebook.getCreateTime();
		this.uploadTime = ebook.getUploadTime();
		this.version = ebook.getVersion();
		this.bookKey = ebook.getBookKey();
		this.bookUrl = ebook.getBookUrl();
		this.operator = ebook.getOperator();
		this.subject = ebook.getSubject();
		this.serVersion = ebook.getSerVersion();
		this.eduVersion = ebook.getEduVersion();
		this.versionStr = ebook.getVersionStr();
		this.teachVersion = ebook.getTeachVersion();
		this.schoolStage = ebook.getSchoolStage();
		this.grade = ebook.getGrade();
		this.volume = ebook.getVolume();
		this.productType = ebook.getProductType();
		this.applyObject = ebook.getApplyObject();
		this.language = ebook.getLanguage();
		this.publishing = ebook.getPublishing();
		this.author = ebook.getAuthor();
		this.pageNumber = ebook.getPageNumber();
		this.edition = ebook.getEdition();
		this.gradeCode = ebook.getGradeCode();
		this.operatorTime = new Timestamp(datetime.getTime());
		this.bookType = ebook.getBookType();
	}

	public BsEbook(BsEbook ebook) {
		this.bookCode = ebook.getBookCode();
		this.bookName = ebook.getBookName();
		this.bookPath = ebook.getBookPath();
		this.bookSize = ebook.getBookSize();
		this.createTime = ebook.getCreateTime();
		this.uploadTime = ebook.getUploadTime();
		this.version = ebook.getVersion();
		this.bookKey = ebook.getBookKey();
		this.bookUrl = ebook.getBookUrl();
		this.operator = ebook.getOperator();
		this.subject = ebook.getSubject();
		this.serVersion = ebook.getSerVersion();
		this.eduVersion = ebook.getEduVersion();
		this.versionStr = ebook.getVersionStr();
		this.teachVersion = ebook.getTeachVersion();
		this.schoolStage = ebook.getSchoolStage();
		this.grade = ebook.getGrade();
		this.volume = ebook.getVolume();
		this.productType = ebook.getProductType();
		this.applyObject = ebook.getApplyObject();
		this.language = ebook.getLanguage();
		this.publishing = ebook.getPublishing();
		this.author = ebook.getAuthor();
		this.pageNumber = ebook.getPageNumber();
		this.edition = ebook.getEdition();
		this.gradeCode = ebook.getGradeCode();
		this.bookType = ebook.getBookType();
		this.secret = ebook.getSecret();
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookCode() {
		return this.bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookPath() {
		return this.bookPath;
	}

	public void setBookPath(String bookPath) {
		this.bookPath = bookPath;
	}

	public Integer getBookSize() {
		return this.bookSize;
	}

	public void setBookSize(Integer bookSize) {
		this.bookSize = bookSize;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}
	
	public String getCreateTime_() {
		if(null!= this.createTime){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.createTime);
		}else{
			return "";
		}
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUploadTime() {
		return this.uploadTime;
	}
	
	public String getUploadTime_() {
		if(null!= this.uploadTime){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.uploadTime);
		}else{
			return "";
		}
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Double getVersion() {
		return this.version;
	}
	
	public String getVersion_() {
		if(this.version!=null){
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(this.version);
		}else{
			return "";
		}
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public String getBookKey() {
		return this.bookKey;
	}

	public void setBookKey(String bookKey) {
		this.bookKey = bookKey;
	}

	public String getBookUrl() {
		return this.bookUrl;
	}

	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getVersionStr() {
		String s = "";
		if(this.versionStr!=null && !"".equals(this.versionStr)){
			int p = this.versionStr.indexOf("@");
			if(p!=-1){
				s = this.versionStr.substring(0,p);
			}else{
				s = this.getVersion_();
			}
		}else{
			s = this.getVersion_();
		}
		return s;
		
	}

	public void setVersionStr(String versionStr) {
		this.versionStr = versionStr;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSerVersion() {
		return serVersion;
	}

	public void setSerVersion(String serVersion) {
		this.serVersion = serVersion;
	}

	public String getEduVersion() {
		return eduVersion;
	}

	public void setEduVersion(String eduVersion) {
		this.eduVersion = eduVersion;
	}

	public String getTeachVersion() {
		return teachVersion;
	}

	public void setTeachVersion(String teachVersion) {
		this.teachVersion = teachVersion;
	}

	public Integer getSchoolStage() {
		return schoolStage;
	}

	public void setSchoolStage(Integer schoolStage) {
		this.schoolStage = schoolStage;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getApplyObject() {
		return applyObject;
	}

	public void setApplyObject(String applyObject) {
		this.applyObject = applyObject;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public Integer getAuId() {
		return auId;
	}

	public void setAuId(Integer auId) {
		this.auId = auId;
	}

	public Timestamp getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Timestamp operatorTime) {
		this.operatorTime = operatorTime;
	}
	public String getOperatorTime_() {
		if(null!= this.operatorTime){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.operatorTime);
		}else{
			return "";
		}
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getBookType_name() {
		if("01".equals(bookType)){
			return "文科";
		}
		if("02".equals(bookType)) {
			return "理科";
		}
		return bookType_name;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}

	public void setBookType_name(String bookType_name) {
		this.bookType_name = bookType_name;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}