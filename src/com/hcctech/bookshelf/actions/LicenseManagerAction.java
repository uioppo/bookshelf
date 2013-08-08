package com.hcctech.bookshelf.actions;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsLicenseBatch;
import com.hcctech.bookshelf.pojo.BsLicenseKey;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.services.LicenseManagerService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @author apple
 *授权码管理
 */
public class LicenseManagerAction extends ActionSupport{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8133927813278092355L;
	
	private LicenseManagerService licenseManagerService;
	
	private List<BsLicenseKey> bsLicenseKeyList;
	private Set<BsProducts> bookSet;
	private List<BsArea> areaList;
	private List<BsSchool> schoolList;
	
	private int keyTotalSize;
	private int usedKeyTotalSize;
	
	private String batch;
	private String schoolName;
	private String schoolStage;
	private String productName;
	private String idStr;
	private String shengId;
	private String quId;
	private Integer schoolId;
	private Integer keySize;
	private String lifeTime;
	private String pattern;
	private String pattern_target;
	private String serVersion ;
	
	private int pageNo=1;
	private int pageSize=10;
	private int page = 1;
	private long total;
	
	private String sort; // 排序字段
	private String order;// 排序类型
	
	@SuppressWarnings("rawtypes")
	private List rows;
	private Page<BsLicenseBatch> bsLicenseBatchPage;
	private Page<BsProducts> bsProductsPage;
	private static final Log logger = LogFactory.getLog(LicenseManagerAction.class);
	private String authCode ;
	private BsLicenseKey bsLicenseKey ;
	/**
	 *   导出授权码
	 */
	public void exportExcel(){
		try {
			licenseManagerService.exportExcel(batch);
		} catch (Exception e) {
			logger.error("导出授权码批次"+batch+"出错");
		}
	}
	/**
	 * 添加授权码    提交到数据库
	 */
	public String addLicenseKeyBatch(){
		licenseManagerService.addLicenseKeyBatch(idStr,quId,schoolId,schoolStage,keySize,lifeTime,pattern,serVersion,pattern_target);
		return SUCCESS;
	}
	/**
	 * 添加授权码    查询学校
	 */
	public String loadSchool(){
		schoolList=licenseManagerService.loadSchool(shengId);
		return SUCCESS;
	}
	/**
	 * 添加授权码    查询地区
	 */
	public String loadSheng(){
		areaList=licenseManagerService.loadSheng(shengId);
		return SUCCESS;
	}
	
	/**
	 * 添加授权码    查询所有电子书
	 */
	public String loadProductAll(){
		bsProductsPage=licenseManagerService.loadProductAll(productName,page,pageSize,sort,order,idStr);
		rows=bsProductsPage.getList();
		total=bsProductsPage.getTotalCount();
		return SUCCESS;
	}
	/**
	 * 授权码管理    批次详细
	 */
	@SuppressWarnings("unchecked")
	public String loadKeyByBatch(){
		Map<String, Object> map=licenseManagerService.loadKeyByBatch(batch);
		bsLicenseKeyList=(List<BsLicenseKey>) map.get("list");
		usedKeyTotalSize=(Integer) map.get("usedSize");
		schoolName=(String) map.get("name");
		bookSet=(Set<BsProducts>) map.get("set");
		if(bsLicenseKeyList!=null&&bsLicenseKeyList.size()>0){
			keyTotalSize=bsLicenseKeyList.size();
			int i=bsLicenseKeyList.get(0).getBsLicenseBatch().getSchoolStage();
			if(i==1){
				schoolStage="小学";
			}else if(i==2){
				schoolStage="初中";
			}else{
				schoolStage="高中";
			}
			lifeTime=bsLicenseKeyList.get(0).getLifeTime_();
		}
		return SUCCESS;
	}
	/**
	 * 授权码管理    批次列表
	 */
	public String loadBatchList(){
		bsLicenseBatchPage=licenseManagerService.loadBatchList(batch,schoolName,page,pageSize,sort,order);
		rows=bsLicenseBatchPage.getList();
		total=bsLicenseBatchPage.getTotalCount();
		return SUCCESS;
	}
	
	/**
	 * 根据授权码查询
	 */
	
	public String loadByAuthCode(){
		bsLicenseKey = licenseManagerService.loadByAuthCode(authCode);
		return SUCCESS;
	}
	
	public void setLicenseManagerService(LicenseManagerService licenseManagerService) {
		this.licenseManagerService = licenseManagerService;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@SuppressWarnings("unchecked")
	public List<BsLicenseBatch> getRows() {
		return rows;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Page<BsLicenseBatch> getBsLicenseBatchPage() {
		return bsLicenseBatchPage;
	}
	public List<BsLicenseKey> getBsLicenseKeyList() {
		return bsLicenseKeyList;
	}
	public String getSchoolStage() {
		return schoolStage;
	}
	public void setSchoolStage(String schoolStage) {
		this.schoolStage = schoolStage;
	}
	public int getKeyTotalSize() {
		return keyTotalSize;
	}
	public void setKeyTotalSize(int keyTotalSize) {
		this.keyTotalSize = keyTotalSize;
	}
	public int getUsedKeyTotalSize() {
		return usedKeyTotalSize;
	}
	public void setUsedKeyTotalSize(int usedKeyTotalSize) {
		this.usedKeyTotalSize = usedKeyTotalSize;
	}
	public void setBookSet(Set<BsProducts> bookSet) {
		this.bookSet = bookSet;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public void setShengId(String shengId) {
		this.shengId = shengId;
	}
	public List<BsArea> getAreaList() {
		return areaList;
	}
	public List<BsSchool> getSchoolList() {
		return schoolList;
	}
	public void setQuId(String quId) {
		this.quId = quId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public void setKeySize(Integer keySize) {
		this.keySize = keySize;
	}
	
	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	public String getLifeTime() {
		return lifeTime;
	}
	public Set<BsProducts> getBookSet() {
		return bookSet;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public BsLicenseKey getBsLicenseKey() {
		return bsLicenseKey;
	}
	public void setSerVersion(String serVersion) {
		this.serVersion = serVersion;
	}
	public void setPattern_target(String pattern_target) {
		this.pattern_target = pattern_target;
	}
	
}
