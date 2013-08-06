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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.services.BsAreaService;
import com.hcctech.bookshelf.util.OperatorExcel;
import com.hcctech.bookshelf.util.TreeJson;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zoujunpeng
 *
 */
public class BsAreaAction extends ActionSupport{

	private static final long serialVersionUID = -5264017926293904180L;
	
	private BsAreaService bsAreaService;

	private List<TreeJson> rows;
	
	private int areaId;
	
	private BsArea bsArea;
	
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
    		HSSFSheet sheet = workbook.createSheet("AreaExcel");
    		HSSFRow row = sheet.createRow((short) 0);
    		OperatorExcel.createCell(row, 0, null, HSSFCell.CELL_TYPE_STRING, "地区名称");
    		OperatorExcel.createCell(row, 1, null, HSSFCell.CELL_TYPE_STRING, "区号");
    		Long pageTotal = null;
    		short i = 1;
    		List<TreeJson> areas = bsAreaService.findAreaAll(null);
    		if(areas != null) {
    			for (TreeJson area : areas) {
    				row = sheet.createRow(i++);
    				OperatorExcel.createCell(row, 0, null, HSSFCell.CELL_TYPE_STRING, area.getName());
    				if(area.getAreaCode()!=null)
    	    		OperatorExcel.createCell(row, 1, null, HSSFCell.CELL_TYPE_STRING, area.getAreaCode());
				}
    		}
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
    
    public String importExcel() {
    	try {
	    	if(excel!=null) {
	    		List<TreeJson> treeList = bsAreaService.findAreaAll(bsArea);
	    		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excel));
	    		HSSFSheet sheet = wb.getSheetAt(0); 
//	    		HSSFRow row = .getRow(0); 
	    		Iterator<Row> rows = sheet.rowIterator();
	    		int i = 0;
	    		BsArea bsArea = null;
	    		for (Row row : sheet) {
					if(i++==0) continue;
					try {
						bsArea = new BsArea();
						bsArea.setAreaName(row.getCell(0).getStringCellValue());
						bsArea.setAreaCode(row.getCell(1).toString());
						if(row.getCell(2)!=null && !row.getCell(2).toString().equals("")) {
							BsArea parent = new BsArea();
							Integer id = getBsAreaIdByName(treeList,row.getCell(2).toString());
							if(id!=null && id>0) {
								parent.setAreaId(id);
								bsArea.setBsArea(parent);
							}
						}
						bsAreaService.saveArea(bsArea);
						TreeJson shi_t = new TreeJson();
						shi_t.setId(bsArea.getAreaId());
						shi_t.setName(bsArea.getAreaName());
						shi_t.setAreaCode(bsArea.getAreaCode());
						treeList.add(shi_t);
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
    
	/**
	 * 查询所有地区数据 整合TreeJson格式
	 * @return
	 */
	public String findAllArea(){
		rows = bsAreaService.findAreaAll(bsArea);
		return SUCCESS;
	}
	
	/**
	 * 根据areaId查询BsArea
	 * @return
	 */
	public String findAreaById(){
		bsArea = bsAreaService.findAreaById(areaId);
		return SUCCESS;
	}
	
	public String saveArea(){
		bsAreaService.saveArea(bsArea);
		return SUCCESS;
	}
	
	public String updateArea(){
		bsAreaService.updateArea(bsArea);
		return SUCCESS;
	}
	
	public void deleteArea(){
		bsAreaService.deleteArea(bsArea);
	}
	
	public void setBsAreaService(BsAreaService bsAreaService) {
		this.bsAreaService = bsAreaService;
	}

	public List<TreeJson> getRows() {
		return rows;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public BsArea getBsArea() {
		return bsArea;
	}

	public void setBsArea(BsArea bsArea) {
		this.bsArea = bsArea;
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
		String downloadFileName = (sf.format(new Date()).toString()) + "地区.xls";
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
}
