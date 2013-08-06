package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import com.hcctech.bookshelf.util.DomainUtil;

/**
 * BsProducts entity. @author MyEclipse Persistence Tools
 */

public class BsProducts implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 457058169008552997L;
	private Integer productId;
	private String productName;
	private String bookCode;
	private String keyWord;
	private Integer usePeriod;
	private String thumbnail;
	private Double price;
	private Double vipPrice;
	private Double downPrice;
	private Double accreditPrice;
	private String author;
	private String publishing;
	private Date publishTime;
	private String edition;
	private Integer pageNumber;
	private Date updateTime;
	private String format;
	private String language;
	private String isbn;
	private Integer schoolStage;
	private String grade;
	private String subject;
	private String volume;
	private Integer productType;
	private String tryUrl;
	private String applyObject;
	private String catalog;
	private String description;
	private Timestamp optionTime;
	private Integer optionAuthorid;
	private String optionAuthor;
	private Integer status;
	private Date startTime;
	private Date endTime;
	private Double tempPrice;
	private Integer switchs;		//
	private Set bsLicenseKeies = new HashSet(0);
	private Set bsMybooks = new HashSet(0);
	private Set bsOrderProducts = new HashSet(0);
	private DecimalFormat df = new DecimalFormat("#.00");

	private Double bestPrice;//对客户最优的价格
	private Double priceSetting;//折扣
	private Integer buyCount;//购买数量
	private String teachVersion ; //对应教材版本
	private Integer bookSize;	  //电子书大小
	
	private Integer downNumber; //下载次数
	
	private String gradeCode;
	// Constructors

	/** default constructor */
	public BsProducts() {
	}

	public BsProducts(Integer productId) {
		this.productId = productId;
	}

	/** full constructor */
	public BsProducts(String productName, String bookCode, String keyWord,
			Integer usePeriod, String thumbnail, Double price, Double vipPrice,
			Double downPrice, String author, String publishing,
			Date publishTime, String edition, Integer pageNumber,
			Date updateTime, String format, String language, String isbn,
			Integer schoolStage, String grade, String subject, String volume,
			Integer productType, String tryUrl, String applyObject,
			String catalog, String description, Timestamp optionTime,Integer optionAuthorid,
			String optionAuthor, Integer status, Set bsLicenseKeies,
			Set bsMybooks, Set bsOrderProducts) {
		this.productName = productName;
		this.bookCode = bookCode;
		this.keyWord = keyWord;
		this.usePeriod = usePeriod;
		this.thumbnail = thumbnail;
		this.price = price;
		this.vipPrice = vipPrice;
		this.downPrice = downPrice;
		this.author = author;
		this.publishing = publishing;
		this.publishTime = publishTime;
		this.edition = edition;
		this.pageNumber = pageNumber;
		this.updateTime = updateTime;
		this.format = format;
		this.language = language;
		this.isbn = isbn;
		this.schoolStage = schoolStage;
		this.grade = grade;
		this.subject = subject;
		this.volume = volume;
		this.productType = productType;
		this.tryUrl = tryUrl;
		this.applyObject = applyObject;
		this.catalog = catalog;
		this.description = description;
		this.optionTime = optionTime;
		this.optionAuthorid = optionAuthorid;
		this.optionAuthor = optionAuthor;
		this.status = status;
		this.bsLicenseKeies = bsLicenseKeies;
		this.bsMybooks = bsMybooks;
		this.bsOrderProducts = bsOrderProducts;
	}
	
	public BsProducts(Integer productId, String productName, String thumbnail,Double price,Double vipPrice,Double downPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.thumbnail = thumbnail;
		this.price = price ;
		this.vipPrice = vipPrice ;
		this.downPrice = downPrice ;
	}

	// Property accessors

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBookCode() {
		return this.bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getUsePeriod() {
		return this.usePeriod;
	}

	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}

	public String getThumbnail() {
		return this.thumbnail;
	}

	public String getThumbnail_1() {
		return DomainUtil.getFileDomain() + this.thumbnail;
	}
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Double getPrice() {
		return price ;
	}

	public String getPrice_() {
		if(null!=this.price){
			if(price >= 0d && price < 1d){
				return "0" + df.format(this.price);
			}
			return df.format(this.price);
		}else{
			return "0.00";
		}
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getVipPrice() {
		return vipPrice ;
	}

	
	public String getVipPrice_() {
		if(null!=this.vipPrice){
			if(vipPrice >= 0d && vipPrice < 1d ) {
				return "0" + df.format(this.vipPrice);
			}
			return df.format(this.vipPrice);
		}else{
			return null;
		}
	}

	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}

	public Double getDownPrice() {
		return downPrice ;
	}
	
	public String getDownPrice_() {
		DecimalFormat df = new DecimalFormat("#.0");
		if(null!=this.downPrice){
			if(downPrice >= 0d && downPrice < 1d) {
				return "0" + df.format(this.downPrice);
			}
			return df.format(this.downPrice);
		}
		return null;
	}
	
	public String getWebDownPrice_() {
		if(null!=this.downPrice && this.downPrice>0d){
			DecimalFormat df = new DecimalFormat("#.00");
			double p = this.downPrice*this.price/10 ;
			if(p>=0d && p<1d) {
				return "0" + df.format(p);
			}
			return df.format(p);
		}
		return null;
	}

	public void setDownPrice(Double downPrice) {
		this.downPrice = downPrice;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishing() {
		return this.publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public Date getPublishTime() {
		return publishTime ;
	}
	
	public String getPublishTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null!=this.publishTime){
			return sdf.format(this.publishTime);
		}else{
			return "";
		}
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getEdition() {
		return this.edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}


	public Integer getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Date getUpdateTime() {
		return updateTime ;
	}
	
	public String getUpdateTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(null != this.updateTime){
			return sdf.format(this.updateTime);
		}else{
			return "";
		}
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getSchoolStage() {
		return this.schoolStage;
	}

	public void setSchoolStage(Integer schoolStage) {
		this.schoolStage = schoolStage;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getVolume() {
		return this.volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Integer getProductType() {
		return this.productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getTryUrl() {
		return this.tryUrl;
	}

	public void setTryUrl(String tryUrl) {
		this.tryUrl = tryUrl;
	}

	public String getApplyObject() {
		return this.applyObject;
	}

	public void setApplyObject(String applyObject) {
		this.applyObject = applyObject;
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Timestamp getOptionTime() {
		return optionTime ;
	}

	
	public String getOptionTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null!=this.optionTime){
			return sdf.format(this.optionTime);
		}else{
			return "";
		}
	}

	public void setOptionTime(Timestamp optionTime) {
		this.optionTime = optionTime;
	}

	public String getOptionAuthor() {
		return this.optionAuthor;
	}

	public void setOptionAuthor(String optionAuthor) {
		this.optionAuthor = optionAuthor;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JSON(serialize=false)
	public Set getBsLicenseKeies() {
		return this.bsLicenseKeies;
	}
	@JSON(serialize=false)
	public void setBsLicenseKeies(Set bsLicenseKeies) {
		this.bsLicenseKeies = bsLicenseKeies;
	}

	@JSON(serialize=false)
	public Set getBsMybooks() {
		return this.bsMybooks;
	}
	@JSON(serialize=false)
	public void setBsMybooks(Set bsMybooks) {
		this.bsMybooks = bsMybooks;
	}
	@JSON(serialize=false)
	public Set getBsOrderProducts() {
		return this.bsOrderProducts;
	}
	@JSON(serialize=false)
	public void setBsOrderProducts(Set bsOrderProducts) {
		this.bsOrderProducts = bsOrderProducts;
	}

	public Double getBestPrice() {
		return bestPrice;
	}
	
	public String getBestPrice_() {
		if(null!=this.bestPrice){
			if(bestPrice >= 0d &&bestPrice < 1d) {
				return "0" + df.format(this.bestPrice);
			}
			return  df.format(this.bestPrice);
		}else{
			return null;
		}
	}
	
	public Double getPriceSetting() {
		return priceSetting;
	}
	public String getPriceSetting_(){
		if(null!=this.priceSetting){
			if(priceSetting >= 0d && priceSetting < 1d){
				return "0" + df.format(this.priceSetting) ;
			}
			return  df.format(this.priceSetting);
		}else{
			return null;
		}
	}
	public void setPriceSetting(Double priceSetting) {
		this.priceSetting = priceSetting;
	}

	public void setBestPrice(Double bestPrice) {
		this.bestPrice = bestPrice;
	}
	public Double getAccreditPrice() {
		return accreditPrice;
	}
	public String getAccreditPrice_() {
		if(null!=this.accreditPrice){
			if(accreditPrice >= 0d && accreditPrice < 1d) {
				return "0" + df.format(this.accreditPrice);
			}
			return df.format(this.accreditPrice);
		}
		return "";
	}
	public void setAccreditPrice(Double accreditPrice) {
		this.accreditPrice = accreditPrice;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStartTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null != this.startTime){
			return sdf.format(this.startTime);
		}else{
			return "";
		}
	}
	public String getEndTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null != this.endTime){
			return sdf.format(this.endTime);
		}else{
			return "";
		}
	}

	public Double getTempPrice() {
		return tempPrice;
	}

	public void setTempPrice(Double tempPrice) {
		this.tempPrice = tempPrice;
	}

	public Integer getSwitchs() {
		return switchs;
	}

	public void setSwitchs(Integer switchs) {
		this.switchs = switchs;
	}

	public String getTeachVersion() {
		return teachVersion;
	}

	public void setTeachVersion(String teachVersion) {
		this.teachVersion = teachVersion;
	}

	public Integer getBookSize() {
		return bookSize;
	}

	public void setBookSize(Integer bookSize) {
		this.bookSize = bookSize;
	}

	public Integer getOptionAuthorid() {
		return optionAuthorid;
	}

	public void setOptionAuthorid(Integer optionAuthorid) {
		this.optionAuthorid = optionAuthorid;
	}

	public Integer getDownNumber() {
		return downNumber;
	}

	public void setDownNumber(Integer downNumber) {
		this.downNumber = downNumber;
	}
	
}