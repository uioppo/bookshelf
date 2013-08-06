package com.hcctech.bookshelf.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.services.SchoolManagerService;
import com.hcctech.bookshelf.services.impl.BsAreaServiceImpl;
import com.hcctech.bookshelf.util.OperatorExcel;
import com.hcctech.bookshelf.util.TreeJson;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author apple
 *学校管理
 */
public class SchoolManagerAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8155717432624246059L;
	
	private static Logger loger = Logger.getLogger(SchoolManagerAction.class);
	
	private SchoolManagerService schoolManagerService;
	private BsAreaServiceImpl bsAreaService;
	
	private List<BsSchool> rows;
	private Page<BsSchool> schoolPage;
	private BsSchool bsSchool;
	private String schoolName;
	private int areaId;
	private int flag;
	private String ids;
	private int pageSize=10;
	private int page = 1;
	private long total;
	
	private String sort; // 排序字段
	private String order;// 排序类型
	
	private File excel; //上传的文件
    private String excelFileName; //文件名称
    private String excelContentType; //文件类型
    
    private InputStream excelFile;

    private String downloadFileName;

    public String exportExcel() {
    	try {
    		HttpServletResponse response = ServletActionContext.getResponse();
    		
    		HSSFWorkbook workbook = new HSSFWorkbook();
    		// 创建工作表实例
    		HSSFSheet sheet = workbook.createSheet("SchoolExcel");
    		HSSFRow row = sheet.createRow((short) 0);
    		OperatorExcel.createCell(row, 0, null, HSSFCell.CELL_TYPE_STRING, "学校名称");
    		OperatorExcel.createCell(row, 1, null, HSSFCell.CELL_TYPE_STRING, "学校地址");
    		Long pageTotal = null;
    		short i = 1;
    		do {
    			schoolPage=schoolManagerService.loadSchoolList(null,page++,pageSize,null,null);
    			if(schoolPage.getList()==null) {
    				break;
    			}
    			total=schoolPage.getTotalCount();
    			if(pageTotal == null) {
    				pageTotal = total%pageSize>0?(total/pageSize+1):(total/pageSize);
    			}
    			for (BsSchool school : schoolPage.getList()) {
    				row = sheet.createRow(i++);
    				OperatorExcel.createCell(row, 0, null, HSSFCell.CELL_TYPE_STRING, school.getSchoolName());
    	    		OperatorExcel.createCell(row, 1, null, HSSFCell.CELL_TYPE_STRING, school.getBsArea().getAreaName());
				}
    		}while(total>0 && pageTotal!=null && page<=pageTotal.intValue());
    		ByteArrayOutputStream output = new ByteArrayOutputStream();
    		workbook.write(output);
    		byte[] ba = output.toByteArray();
    		excelFile = new ByteArrayInputStream(ba);
    		output.flush();
    		output.close();
		} catch (Exception e) {
			loger.error("导出学校出错");
			return "fail";
		}
    	return "excel";
    }
    
    public String importExcel() {
    	try {
	    	if(excel!=null) {
	    		List<TreeJson> treeList = bsAreaService.findAreaAll(null);
	    		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excel));
	    		HSSFSheet sheet = wb.getSheetAt(0); 
//	    		HSSFRow row = .getRow(0); 
	    		int i = 0;
	    		BsSchool bsSchool = null;
	    		BsArea bsArea = null;
	    		for (Row row : sheet) {
					if(i++==0) continue;
					try {
						bsSchool = new BsSchool();
						bsSchool.setSchoolName(row.getCell(0).getStringCellValue());
						Integer areaId = getBsAreaIdByName(treeList,row.getCell(1).toString());
						if(areaId!=null && areaId>0) {
							bsArea = new BsArea();
							bsArea.setAreaId(areaId);
							bsSchool.setBsArea(bsArea);
						}
						schoolManagerService.addOrUpdate(bsSchool);
					}catch(Exception e) {
						e.printStackTrace();
						continue;
					}
				}
	    	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    		
    	return SUCCESS;
    }
    
    private Integer getBsAreaIdByName(List<TreeJson> treeList,String areaName) {
    	if(treeList!=null) {
    		for (TreeJson treeJson : treeList) {
				if(treeJson.getName().equals(areaName)) {
					return treeJson.getId();
				}
			}
    	}
    	return null;
    }
    
	
	public String updateSchool(){
		return SUCCESS;
	}
	
	/**
	 * 添加或者修改学校
	 * @return
	 */
	public String addOrUpdate(){
		if(bsSchool!=null&&bsSchool.getSchoolName()!=null&&!"".equals(bsSchool.getSchoolName())
				&&bsSchool.getBsArea()!=null&&bsSchool.getBsArea().getAreaId()>0){
			schoolManagerService.addOrUpdate(bsSchool);
			flag=1;
		}else{
			flag=0;
		}
		return SUCCESS;
	}
	/**
	 * 根据学校ID查询学校
	 * @return
	 */
	public String loadSchoolById(){
		bsSchool=schoolManagerService.loadSchoolById(bsSchool);
		return SUCCESS;
	}
	
	/**
	 * 删除学校
	 * @return
	 */
	public String deleteSchool(){
		if(ids!=null&&!"".equals(ids.trim())){
			schoolManagerService.deleteSchool(ids);
			flag=1;
		}else{
			flag=0;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询学校列表
	 * @return
	 */
	public String loadSchoolList(){
		schoolPage=schoolManagerService.loadSchoolList(schoolName,page,pageSize,sort,order);
		rows=schoolPage.getList();
		total=schoolPage.getTotalCount();
		return SUCCESS;
	}

	public void setSchoolManagerService(SchoolManagerService schoolManagerService) {
		this.schoolManagerService = schoolManagerService;
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

	public List<BsSchool> getRows() {
		return rows;
	}

	public Page<BsSchool> getSchoolPage() {
		return schoolPage;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public int getFlag() {
		return flag;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public BsSchool getBsSchool() {
		return bsSchool;
	}

	public void setBsSchool(BsSchool bsSchool) {
		this.bsSchool = bsSchool;
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
		String downloadFileName = (sf.format(new Date()).toString()) + "学校.xls";
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

	public void setBsAreaService(BsAreaServiceImpl bsAreaService) {
		this.bsAreaService = bsAreaService;
	}
	
}
