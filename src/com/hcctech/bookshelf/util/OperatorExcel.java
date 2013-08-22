package com.hcctech.bookshelf.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hcctech.bookshelf.pojo.BsLicenseKey;
import com.hcctech.bookshelf.pojo.BsProducts;

/**
 * 授权码导出工具
 * @author apple
 *
 */
public class OperatorExcel {
	
/**
 * 导出授权码
 * @param batch 批次实体
 * @param map 包含 bsLicenseKeyList 授权码信息、usedKeyTotalSize 已用授权码数量、schoolName 学校名、bookSet 包含电子书信息
 * @param response
 * @throws IOException 
 */
@SuppressWarnings("unchecked")
public static void exportExcel(String batch,Map<String, Object> map,HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload;charset=ISO8859_1");
		String filename = new String(("授权码-"+batch).getBytes("GBK"),"ISO8859_1");
		
		response.addHeader("Content-Disposition","attachment;filename=" + filename+".xlsx");
		OutputStream out = response.getOutputStream();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		XSSFSheet sheet = xssfWorkbook.createSheet("测试sheet");
		sheet.setColumnWidth(0, 32 * 220);
		sheet.setColumnWidth(1, 32 * 300);
		
		List<BsLicenseKey> bsLicenseKeyList=(List<BsLicenseKey>) map.get("list");
		int usedKeyTotalSize=(Integer) map.get("usedSize");
		String schoolName=(String) map.get("name");
		String schoolStage=null;
		int keyTotalSize=0;
		Set<BsProducts> bookSet=(Set<BsProducts>) map.get("set");
		String lifeTime="";
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
		String str="共 "+keyTotalSize+"个        已用  "+usedKeyTotalSize+"个";
		//导出批次信息
		XSSFRow row0 = sheet.createRow((short)0);
		XSSFCell cell00 =  row0.createCell((short)0);
		cell00.setCellValue("批次号");
		XSSFCell cell01 =  row0.createCell((short)1);
		cell01.setCellValue(batch);

		XSSFRow row1 = sheet.createRow((short)1);
		XSSFCell cell10 =  row1.createCell((short)0);
		cell10.setCellValue("学校名称");
		XSSFCell cell11 =  row1.createCell((short)1);
		cell11.setCellValue(schoolName);

		XSSFRow row2 = sheet.createRow((short)2);
		XSSFCell cell20 =  row2.createCell((short)0);
		cell20.setCellValue("学段");
		XSSFCell cell21 =  row2.createCell((short)1);
		cell21.setCellValue(schoolStage);
		
		XSSFRow row3 = sheet.createRow((short)3);
		XSSFCell cell30 =  row3.createCell((short)0);
		cell30.setCellValue("授权码有效截止日期");
		XSSFCell cell31 =  row3.createCell((short)1);
		cell31.setCellValue(lifeTime);
		
		//电子书信息
		Iterator<BsProducts> iterator=bookSet.iterator();
		short cell = 4;
		while(iterator.hasNext()){
			BsProducts bsProducts= iterator.next();
			
			XSSFRow row5 = sheet.createRow((short)cell++);
			XSSFCell cell50 =  row5.createCell((short)0);
			cell50.setCellValue("电子书名称");
			XSSFCell cell51 =  row5.createCell((short)1);
			cell51.setCellValue(bsProducts.getProductName());
			
			XSSFRow row6 = sheet.createRow((short)cell++);
			XSSFCell cell60 =  row6.createCell((short)0);
			cell60.setCellValue("学科");
			XSSFCell cell61 =  row6.createCell((short)1);
			cell61.setCellValue(bsProducts.getSubject());
			
			XSSFRow row7 = sheet.createRow((short)cell++);
			XSSFCell cell70 =  row7.createCell((short)0);
			cell70.setCellValue("价格");
			XSSFCell cell71 =  row7.createCell((short)1);
			cell71.setCellValue(bsProducts.getPrice_());
			
			XSSFRow row8 = sheet.createRow((short)cell++);
			XSSFCell cell80 =  row8.createCell((short)0);
			cell80.setCellValue("年级");
			XSSFCell cell81 =  row8.createCell((short)1);
			cell81.setCellValue(bsProducts.getGrade());
			
			/*XSSFRow row9 = sheet.createRow((short)8);
			XSSFCell cell90 =  row9.createCell((short)0);
			cell90.setCellValue("编号");
			XSSFCell cell91 =  row9.createCell((short)1);
			cell91.setCellValue(bsProducts.getIsbn());*/
		}
		
		int i=cell;
		//授权码
		XSSFFont font_=xssfWorkbook.createFont();
		font_.setBold(true);
		XSSFCellStyle cellStyle=xssfWorkbook.createCellStyle();
		cellStyle.setFont(font_);
		XSSFRow row10 = sheet.createRow((short)i+1);
		XSSFCell cell100 =  row10.createCell((short)0);
		cell100.setCellValue("授权码");
		XSSFCell cell101 =  row10.createCell((short)1);
		cell101.setCellValue(str);
		i=i+1;
		XSSFRow row11 = sheet.createRow((short)i+2);
		XSSFCell cell110 =  row11.createCell((short)0);
		cell110.setCellValue("授权码");
		cell110.setCellStyle(cellStyle);
		XSSFCell cell111 =  row11.createCell((short)1);
		cell111.setCellStyle(cellStyle);
		cell111.setCellValue("序列号");
		/*XSSFCell cell72 =  row7.createCell((short)2);
		cell72.setCellValue("有效期");*/
		
		for(int j=0;j<bsLicenseKeyList.size();j++){
			BsLicenseKey bsLicenseKey=bsLicenseKeyList.get(j);
			XSSFRow rows = sheet.createRow((short)i+3+j);
			XSSFCell cells0 =  rows.createCell((short)0);
			cells0.setCellValue(bsLicenseKey.getKeyId());
			XSSFCell cells1 =  rows.createCell((short)1);
			cells1.setCellValue(bsLicenseKey.getSerialNum());
			/*XSSFCell cells2 =  rows.createCell((short)2);
			cells2.setCellValue(bsLicenseKey.getLifeTime_());*/
		}
		xssfWorkbook.write(out);
		out.close();
	}

	public static void createCell(HSSFRow row, int column, HSSFCellStyle style,
			int cellType, Object value) {
		if(value==null) return;
		HSSFCell cell = row.createCell(column);
		if (style != null) {
			cell.setCellStyle(style);
		}
		switch (cellType) {
		case HSSFCell.CELL_TYPE_BLANK: {
		}
			break;
		case HSSFCell.CELL_TYPE_STRING: {
			cell.setCellValue(value.toString());
		}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(value.toString()));
		}
			break;
		default:
			break;
		}

	}

	public static void main(String[] args) {
		System.out.println(new Double("11").intValue());
	}
}
