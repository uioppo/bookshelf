package com.hcctech.bookshelf.actions;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsEbook;
import com.hcctech.bookshelf.services.EBookService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zoujunpeng
 * @date 2012-08-24
 * @version 1.0
 * 后台：电子书上传、电子书列表{审核、未审核}、其它电子书相关
 */
public class EBookAction extends ActionSupport{

	private static final long serialVersionUID = -7661413923348777945L;
	
	private EBookService eBookService;
	
	private List<BsEbook> rows;
	
	private int pageSize = 10;
	
	private int page = 1;
	
	private int total;
	
	private String searchValue;
	
	private String searchName;
	
	private Integer bookStatus;//审核未审核标识0：N,1：Y,null:N

	private BsEbook ebook;
	
	private Integer bookId;
	
	private String advice;
	
	private String subject ;
	
	private String bookVersion;
	
	private BsEbook bsEbook;
	
	private boolean flag ; 
	
	private String bookCode;
	
	private String bookName;
	
	private String bookSecret;
	
	/**
	 * 验证电子书名称是否重复
	 * @return
	 */
	public String checkName(){
		if(bookName!=null&&!"".equals(bookName)&&!"".equals(bookName.trim())){
			flag=eBookService.checkName(bookName);
		}
		return SUCCESS;
	}
	/**
	 * 上传电子书
	 * @return
	 */
	public String uploadEbook(){
		BsAdminUser adminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		if(adminUser!=null){
			boolean isadd = true;
			if(ebook.getBookCode()!=null&&!ebook.getBookCode().trim().equals(""))
				isadd = false;
			if(bookVersion!=null&&bookVersion!=null){
				Double bookVersion1=Double.valueOf(bookVersion);
				ebook.setVersion(bookVersion1);
			}else{
				return ERROR;
			}
			try {
				eBookService.addEbook(ebook,adminUser,Boolean.parseBoolean(bookSecret));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!isadd)
				return "list";
		}else{
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 加载更新电子书的信息
	 * @return
	 */
	public String loadEBookByUpdate(){
		
		ebook = eBookService.loadEbookByUpdate(bookId);
		if(ebook==null)
			return ERROR;
		//
		return SUCCESS;
	}
	
	

	/**
	 * 查询电子书
	 * @return
	 */
	public String findEBookList(){
		Page<BsEbook> pageObject = eBookService.findEBookList(subject,bookStatus,page,pageSize,searchName,searchValue);
		if(pageObject!=null){
			rows = pageObject.getList();
			total = (int)pageObject.getTotalCount();
		}else{
			rows = null;
			total = 0;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询电子书( 更新电子书列表)
	 * @return
	 */
	public String findUpdateEBookList(){
		Page<BsEbook> pageObject = eBookService.findUpdateEBookList(page, pageSize, searchName, searchValue);
		if(pageObject!=null){
			rows = pageObject.getList();
			total = (int)pageObject.getTotalCount();
		}else{
			rows = null;
			total = 0;
		}
		return SUCCESS;
	}
	
	/**
	 * 审核电子书
	 * status 说明                       
	 * 		0:系统上传流程中的初始状态       
	 *		1：审核通过               
     *      2：审核不通过              
	 */
	public void updateEbookStatus(){
		BsAdminUser adminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		eBookService.updateEbookStatus(bookId, bookStatus,advice,adminUser);
	}
	
	/**
	 * 删除电子书
	 */
	@Deprecated
	public void deleteEbookById(){
		
		//
		eBookService.deleteEbookById(bookId);
	}
	
	public String editEbookAttr() {
		BsAdminUser adminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		if(adminUser == null){
			return SUCCESS;
		}
		flag = eBookService.editEbookAttr(adminUser,bsEbook);
		return SUCCESS;
	}
	
	public String getEbookVersion(){
		bookVersion=eBookService.getEbookVersion(bookCode);
		return SUCCESS;
	}
	public List<BsEbook> getRows() {
		return rows;
	}

	public void seteBookService(EBookService eBookService) {
		this.eBookService = eBookService;
	}


	@JSON(serialize =false)
	public BsEbook getEbook() {
		return ebook;
	}


	public void setEbook(BsEbook ebook) {
		this.ebook = ebook;
	}


	public int getTotal() {
		return total;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}


	public Integer getBookId() {
		return bookId;
	}

	public Integer getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(Integer bookStatus) {
		this.bookStatus = bookStatus;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBookVersion(String bookVersion) {
		this.bookVersion = bookVersion;
	}
	

	public BsEbook getBsEbook() {
		return bsEbook;
	}

	public void setBsEbook(BsEbook bsEbook) {
		this.bsEbook = bsEbook;
	}

	public boolean getFlag() {
		return flag;
	}

	public String getBookVersion() {
		return bookVersion;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public void setBookSecret(String bookSecret) {
		this.bookSecret = bookSecret;
	}
	
}
