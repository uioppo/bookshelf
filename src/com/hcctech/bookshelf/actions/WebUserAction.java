package com.hcctech.bookshelf.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.BsAreaService;
import com.hcctech.bookshelf.services.RegisterService;
import com.hcctech.bookshelf.services.WebUserService;
import com.hcctech.bookshelf.util.OperatorExcel;
import com.hcctech.bookshelf.util.TreeJson;
import com.opensymphony.xwork2.ActionSupport;
/**
 * web用户管理
 */
public class WebUserAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2697514241715750912L;
	private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	private static final DecimalFormat df = new DecimalFormat("0"); 
	private WebUserService webUserService;
	private BsAreaService bsAreaService;
	private RegisterService registerService;
	private List<BsWebUser> rows;
	private Page<BsWebUser> webUserPage;
	private int pageSize=10;
	private int page = 1;
	private long total;
	
	private String sort; // 排序字段
	private String order;// 排序类型
	
	private String wuEmail;
	private BsWebUser bsWebUser;
	private String msg;
	private static final Log logger = LogFactory.getLog(WebUserAction.class);
	
	private File excel; //上传的文件
    private String excelFileName; //文件名称
    private String excelContentType; //文件类型
    
    private InputStream excelFile;

    private String downloadFileName;
    
    private String error;

    public String exportExcel() {
    	try {
    		List<TreeJson> treeList = bsAreaService.findAreaAll(null);
    		HttpServletResponse response = ServletActionContext.getResponse();
    		
    		HSSFWorkbook workbook = new HSSFWorkbook();
    		// 创建工作表实例
    		HSSFSheet sheet = workbook.createSheet("UserExcel");
    		HSSFRow row = sheet.createRow((short) 0);
    		OperatorExcel.createCell(row, 0, null, HSSFCell.CELL_TYPE_STRING, "用户名	");
    		OperatorExcel.createCell(row, 1, null, HSSFCell.CELL_TYPE_STRING, "姓名");
    		OperatorExcel.createCell(row, 2, null, HSSFCell.CELL_TYPE_STRING, "省");
    		OperatorExcel.createCell(row, 3, null, HSSFCell.CELL_TYPE_STRING, "市");
    		OperatorExcel.createCell(row, 4, null, HSSFCell.CELL_TYPE_STRING, "县");
    		OperatorExcel.createCell(row, 5, null, HSSFCell.CELL_TYPE_STRING, "学校");
    		OperatorExcel.createCell(row, 6, null, HSSFCell.CELL_TYPE_STRING, "昵称");
    		OperatorExcel.createCell(row, 7, null, HSSFCell.CELL_TYPE_STRING, "性别");
    		OperatorExcel.createCell(row, 8, null, HSSFCell.CELL_TYPE_STRING, "联系电话");
    		OperatorExcel.createCell(row, 9, null, HSSFCell.CELL_TYPE_STRING, "出生日期");
    		Long pageTotal = null;
    		short i = 1;
    		do {
    			webUserPage=webUserService.loadWebUserList(null, page++, pageSize, null, null);
    			if(webUserPage.getList()==null) {
    				break;
    			}
    			total=webUserPage.getTotalCount();
    			if(pageTotal == null) {
    				pageTotal = total%pageSize>0?(total/pageSize+1):(total/pageSize);
    			}
    			for (BsWebUser bsWebUser : webUserPage.getList()) {
    				row = sheet.createRow(i++);
    				OperatorExcel.createCell(row, 0, null, HSSFCell.CELL_TYPE_STRING, bsWebUser.getWuEmail());
    	    		OperatorExcel.createCell(row, 1, null, HSSFCell.CELL_TYPE_STRING, bsWebUser.getBsUserInfo().getRealName());
    	    		OperatorExcel.createCell(row, 2, null, HSSFCell.CELL_TYPE_STRING, getAreaNameById(treeList,bsWebUser.getBsUserInfo().getSheng()));
    	    		OperatorExcel.createCell(row, 3, null, HSSFCell.CELL_TYPE_STRING, getAreaNameById(treeList,bsWebUser.getBsUserInfo().getShi()));
    	    		OperatorExcel.createCell(row, 4, null, HSSFCell.CELL_TYPE_STRING, getAreaNameById(treeList,bsWebUser.getBsUserInfo().getXian()));
    	    		OperatorExcel.createCell(row, 5, null, HSSFCell.CELL_TYPE_STRING, bsWebUser.getBsUserInfo().getSchool());
    	    		OperatorExcel.createCell(row, 6, null, HSSFCell.CELL_TYPE_STRING, bsWebUser.getBsUserInfo().getNickName());
    	    		OperatorExcel.createCell(row, 7, null, HSSFCell.CELL_TYPE_STRING, getSex_String(bsWebUser.getBsUserInfo().getSex()));
    	    		OperatorExcel.createCell(row, 8, null, HSSFCell.CELL_TYPE_STRING, bsWebUser.getBsUserInfo().getMobile());
    	    		if(bsWebUser.getBsUserInfo().getBirthday()!=null) {
    	    			OperatorExcel.createCell(row, 9, null, HSSFCell.CELL_TYPE_STRING, format.format(bsWebUser.getBsUserInfo().getBirthday()));
    	    		}
    	    		
				}
    		}while(total>0 && pageTotal!=null && page<=pageTotal.intValue());
    		ByteArrayOutputStream output = new ByteArrayOutputStream();
    		workbook.write(output);
    		byte[] ba = output.toByteArray();
    		excelFile = new ByteArrayInputStream(ba);
    		output.flush();
    		output.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
    	return "excel";
    }
    
    private String getSex_String(Integer sexCode)
    {
    	String ret = "保密";
    	if(sexCode==null) return ret;
    	switch(sexCode) {
    	case 1: ret = "男";break;
    	case 2: ret = "女";break;
    	case 3: ret = "保密";break;
    	default:;
    	}
    	return ret;
    }
    private Integer getSex_cod(String value) {
    	if(value==null || "".equals(value)) {
    		return 3;
    	}
    	if("男".equals(value)){
    		return 1;
    	}else if("女".equals(value)) {
    		return 2;
    	}else {
    		return 3;
    	}
    }
    
    private String getAreaNameById(List<TreeJson> treeList,String areaId) {
    	if(areaId==null || "".equals(areaId)) {
    		return "";
    	}
    	if(treeList!=null) {
    		for (TreeJson treeJson : treeList) {
				if(areaId.equals(treeJson.getId())) {
					return treeJson.getName();
				}
			}
    	}
    	return "";
    }
    private String getAreaIdByName(List<TreeJson> treeList,String areaName) {
    	if(areaName==null || "".equals(areaName)) {
    		return "";
    	}
    	if(treeList!=null) {
    		for (TreeJson treeJson : treeList) {
				if(areaName.equals(treeJson.getName())) {
					return String.valueOf(treeJson.getId());
				}
			}
    	}
    	return "";
    }
    private String getAreaIdByNameAndParentId(List<TreeJson> treeList,String areaName,String parentId) {
    	if(areaName==null || "".equals(areaName)) {
    		return "";
    	}
    	if(treeList!=null) {
    		for (TreeJson treeJson : treeList) {
				if(areaName.equals(treeJson.getName()) && parentId.equals(treeJson.get_parentId())) {
					return String.valueOf(treeJson.getId());
				}
			}
    	}
    	return "";
    }
    public String importExcel() {
    	try {
	    	if(excel!=null) {
	    		List<TreeJson> treeList = bsAreaService.findAreaAll(null);
	    		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excel));
	    		HSSFSheet sheet = wb.getSheetAt(0); 
	    		int i = 0;
	    		for (Row row : sheet) {
					if(i++==0) continue;
					BsWebUser webUser = new BsWebUser();
					if(!row.getCell(0).getStringCellValue().matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
//							   System.out.println("邮箱格式不正确");
						msg = row.getCell(0).getStringCellValue().trim()+"不是合法邮箱";
						throw new Exception(msg);
					}
					webUser.setWuEmail(row.getCell(0).getStringCellValue());
					webUser.setWuUserName(row.getCell(0).getStringCellValue());
					webUser.setWuActivestatus(1);
					webUser.setWuToken("");
					Page<BsWebUser> ret = webUserService.loadWebUserList(webUser.getWuEmail(), 1, 10, null, null);
					if(ret!=null && ret.getList()!=null && ret.getList().size()>0) {
//						continue;
						msg = row.getCell(0).getStringCellValue()+"已经存在";
						throw new Exception(msg);
					}
					webUser.setWuPassword("666666");
					int flag = registerService.registerWebUser4Client(webUser);
					if(flag==1) {
						BsUserInfo userInfo = webUser.getBsUserInfo();
						userInfo.setRealName(row.getCell(1).toString());
						if(row.getCell(2)!=null) {
							userInfo.setSheng(getAreaIdByName(treeList, row.getCell(2).toString()));
						}
						if(row.getCell(3)!=null) {
							if(row.getCell(3).equals("市辖区") || row.getCell(3).equals("县")) {
								userInfo.setShi(getAreaIdByNameAndParentId(treeList, row.getCell(3).toString(),userInfo.getSheng()));
							}else {
								userInfo.setShi(getAreaIdByName(treeList, row.getCell(3).toString()));
							}
						}
						if(row.getCell(4)!=null) {
							userInfo.setXian(getAreaIdByName(treeList, row.getCell(4).toString()));
						}
						userInfo.setSchool(row.getCell(5).toString());
						userInfo.setNickName(row.getCell(6).toString());
						userInfo.setSex(getSex_cod(row.getCell(7).toString()));
						if(row.getCell(8).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							userInfo.setMobile(df.format(row.getCell(8).getNumericCellValue()));
						}else {
							userInfo.setMobile(row.getCell(8).toString());
						}
							
						userInfo.setBirthday(row.getCell(9).getDateCellValue());
						webUserService.updateWebUser(webUser);
					}
				}
	    	}else {
	    	    return "fail";
	    	}
    	}catch(Exception e) {
    		if(StringUtils.isBlank(msg)) {
    			msg = e.getMessage();
    		}
    		e.printStackTrace();
    		return "fail";
    	}
    	return SUCCESS;
    }
	
	/**
	 * web用户管理  修改用户信息  提交到数据库
	 */
	public String updateWebUser(){
		if(bsWebUser.getWuId()==null) {
			bsWebUser.setWuPassword("666666");
			int flag = registerService.registerWebUser4Client(bsWebUser);
			if(flag == 0) {
				error = "系统错误";
			}else if(flag == 502) {
				error = "用户已存在";
			}
		}else {
			webUserService.updateWebUser(bsWebUser);
			logger.info("修改web用户"+bsWebUser.getWuId()+"详细信息");
		}
		if(StringUtils.isNotBlank(error)) {
			return "error";
		}
		return SUCCESS;
	}
	/**
	 * 用ID查询web用户信息
	 */
	public String loadWebUserById(){
		if(bsWebUser!=null && bsWebUser.getWuId()!=null) {
			bsWebUser=webUserService.loadWebUserById(bsWebUser);
		}
		return SUCCESS;
	}
	/**
	 * 修改用户状态  禁用启用
	 */
	public String updateWebUserStatus(){
		msg=webUserService.updateWebUserStatus(bsWebUser);
		if(bsWebUser.getWuActivestatus()==1){
			logger.info("启用web用户"+bsWebUser.getWuId());
		}else{
			logger.info("禁用web用户"+bsWebUser.getWuId());;
		}
		return SUCCESS;
	}
	/**
	 * 查询用户列别
	 */
	public String loadWebUserList(){
		webUserPage=webUserService.loadWebUserList(wuEmail,page,pageSize,sort,order);
		rows=webUserPage.getList();
		total=webUserPage.getTotalCount();
		return SUCCESS;
	}

	public void setWebUserService(WebUserService webUserService) {
		this.webUserService = webUserService;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<BsWebUser> getRows() {
		return rows;
	}

	public void setWuEmail(String wuEmail) {
		this.wuEmail = wuEmail;
	}

	public Page<BsWebUser> getWebUserPage() {
		return webUserPage;
	}
	public BsWebUser getBsWebUser() {
		return bsWebUser;
	}
	public void setBsWebUser(BsWebUser bsWebUser) {
		this.bsWebUser = bsWebUser;
	}
	public String getMsg() {
		return msg;
	}
	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getExcelContentType() {
		return excelContentType;
	}

	public void setExcelContentType(String excelContentType) {
		this.excelContentType = excelContentType;
	}

	public InputStream getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}

	public String getDownloadFileName() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd ");
		String downloadFileName = (sf.format(new Date()).toString()) + "用户.xls";
		try {
			downloadFileName = new String(downloadFileName.getBytes(),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public void setBsAreaService(BsAreaService bsAreaService) {
		this.bsAreaService = bsAreaService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
