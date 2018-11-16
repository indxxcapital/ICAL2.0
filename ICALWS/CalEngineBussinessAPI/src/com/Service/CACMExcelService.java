package com.Service;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import com.Bean.CACMBean;
import com.DataService.ConfigUtil;
import com.Service.ExcelTemplateGenerator.FormatType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CACMExcelService
{
	private HSSFWorkbook workbook;
	
	private HSSFFont boldFont;
	private FormatType[] formatTypes;
	
	public void generateCACM(String toDate) throws Exception
	{
		FileOutputStream out;
		
		workbook = new HSSFWorkbook();
		boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		format = workbook.createDataFormat();
		
		generateDividendCACM(toDate);
		generateSplitStockBonusCACM(toDate);
		generateIdChangeCACM(toDate);
		generateSpinOffCACM(toDate);
		generateOthersCACM(toDate);
		
//		String fileName = "CACM-" + toDate.trim() + ".xls" ;
		String fileName = "CACM.xls" ;
		String fileCompletePath = ConfigUtil.propertiesMap.get("CACMPath").trim() +fileName.trim();
		out = new FileOutputStream(fileCompletePath.trim());
		workbook.write(out);
        out.close();
        
	}
	
	private void generateOthersCACM(String toDate) {
		// TODO Auto-generated method stub
		
	}

	public void generateDividendCACM(String toDate) throws Exception
	{
		try 
		{
			CACMDividendService divService = new CACMDividendService(); 
			Map<String,CACMBean> divMap = divService.generateDividendCACM(toDate);
			generateDividendExcel(divMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	
	private void generateSplitStockBonusCACM(String toDate) throws Exception 
	{
		try 
		{
			CACMSplitService splitService = new CACMSplitService(); 
			Map<String,CACMBean> splitMap = splitService.generateSplitCACM(toDate);
			generateSplitExcel(splitMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}			
	}
	
	private void generateIdChangeCACM(String toDate) throws Exception 
	{
		try 
		{
			CACMIdChangeService idChangeService = new CACMIdChangeService(); 
			Map<String,CACMBean> idChangeMap = idChangeService.generateIdChanheCACM(toDate);
			generateIdChangeExcel(idChangeMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}			
	}
	
	private void generateIdChangeExcel(Map<String, CACMBean> idChangeMap)
	{
		try 
		{
			HSSFSheet idChangeSheet = workbook.createSheet("ID Change");
			int currentRow = 0;
			List<String> idChangeColumnSet = CACMUtilService.generateIdChangeCACMColumns();
			HSSFRow rowHeader = idChangeSheet.createRow(currentRow);
			writeHeaderRow(currentRow,idChangeColumnSet,rowHeader);
			currentRow++;
			for(Map.Entry<String,CACMBean> caEntry : idChangeMap.entrySet()) 
			{
				HSSFRow row = idChangeSheet.createRow(currentRow);
				writeValueRow(CACMUtilService.setIdChangeCloumnsValue(caEntry.getValue(),currentRow),row);
				currentRow++;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}

	private void generateSpinOffCACM(String toDate) {
		// TODO Auto-generated method stub
		
	}
	
	private void generateDividendExcel(Map<String, CACMBean> ediCAMap) throws Exception
	{
		
		try 
		{
			HSSFSheet divSheet = workbook.createSheet("Dividend");
			int currentDivRow = 0;
			List<String> divColumnSet = CACMUtilService.generateDividendCACMColumns();
			HSSFRow rowHeader = divSheet.createRow(currentDivRow);
			writeHeaderRow(currentDivRow,divColumnSet,rowHeader);
			currentDivRow++;
			for(Map.Entry<String,CACMBean> caEntry : ediCAMap.entrySet()) 
			{
				HSSFRow row = divSheet.createRow(currentDivRow);
				writeValueRow(CACMUtilService.setDivCloumnsValue(caEntry.getValue(),currentDivRow),row);
				currentDivRow++;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void generateSplitExcel(Map<String, CACMBean> splitMap) 
	{
		try 
		{
			HSSFSheet divSheet = workbook.createSheet("Split");
			int currentDivRow = 0;
			List<String> splitColumnSet = CACMUtilService.generateSplitCACMColumns();
			HSSFRow rowHeader = divSheet.createRow(currentDivRow);
			writeHeaderRow(currentDivRow,splitColumnSet,rowHeader);
			currentDivRow++;
			for(Map.Entry<String,CACMBean> caEntry : splitMap.entrySet()) 
			{
				HSSFRow row = divSheet.createRow(currentDivRow);
				writeValueRow(CACMUtilService.setSplitCloumnsValue(caEntry.getValue(),currentDivRow),row);
				currentDivRow++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	
	
	public void writeHeaderRow(int currentRow,List<String> columnSet,HSSFRow row)
	{
		int numCols = columnSet.size();
		boolean isAutoDecideFormatTypes;
		if (isAutoDecideFormatTypes = (formatTypes == null)) {
			formatTypes = new FormatType[numCols];
		}
		int iCell = 0;
		for (String cloumnName : columnSet)
		{
			writeCell(row, iCell, cloumnName, FormatType.TEXT, null, boldFont);
			iCell++;
		}		
		currentRow++;
	}
	
	public void writeValueRow(List<String> cellValues,HSSFRow row) 
	{
		int numCols = cellValues.size();
		boolean isAutoDecideFormatTypes;
		if (isAutoDecideFormatTypes = (formatTypes == null))
		{
			formatTypes = new FormatType[numCols];
		}
		int iCell = 0;
		for (String cloumnValue : cellValues)
		{
			writeCell(row, iCell, cloumnValue, FormatType.TEXT, null, null);
			iCell++;
		}		
//		currentRow++;
	}
	private void writeCell(HSSFRow row, int col, Object value, FormatType formatType,Short bgColor, HSSFFont font) 
	{
		HSSFCell cell = row.createCell(col);
	    if (value == null) {
	      return;
	    }
	    if (font != null) {
	    	HSSFCellStyle style = workbook.createCellStyle();
	      style.setFont(font);
	      cell.setCellStyle(style);
	    }
	    switch (formatType) 
	    {
	      case TEXT:
	        cell.setCellValue(value.toString());
	        break;
//	      case INTEGER:
//	        cell.setCellValue(((Number) value).intValue());
//	        CellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
//	            HSSFDataFormat.getBuiltinFormat(("#,##0")));
//	        break;
//	      case FLOAT:
//	        cell.setCellValue(((Number) value).doubleValue());
//	        HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
//	            HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
//	        break;
//	      case DATE:
//	        cell.setCellValue((Timestamp) value);
//	        HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
//	            HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
//	        break;
//	      case MONEY:
//	        cell.setCellValue(((Number) value).intValue());
//	        HSSFCellUtil.setCellStyleProperty(cell, workbook,
//	            CellUtil.DATA_FORMAT, format.getFormat("($#,##0.00);($#,##0.00)"));
//	        break;
//	      case PERCENTAGE:
//	        cell.setCellValue(((Number) value).doubleValue());
//	        HSSFCellUtil.setCellStyleProperty(cell, workbook,
//	        		CellUtil.DATA_FORMAT, HSSFDataFormat.getBuiltinFormat("0.00%"));
	    }
//	    if (bgColor != null) {
//	      HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_FOREGROUND_COLOR, bgColor);
//	      HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
//	    }
	}	
}
