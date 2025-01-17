package com.Service;

import java.sql.Timestamp;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;

public class ExcelTemplateGenerator {

	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFFont boldFont;
	private HSSFDataFormat format;
	private FormatType[] formatTypes;
	List<String> columnSet;
	int currentRow = 0;
	  
	public ExcelTemplateGenerator(List<String> aColumnsList,String sheetName) {
		workbook = new HSSFWorkbook();
		this.columnSet = aColumnsList;
		sheet = workbook.createSheet(sheetName);
		boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		format = workbook.createDataFormat();
		currentRow = 0;
		writeHeaderRow();
	}
	  
	public ExcelTemplateGenerator(List<String> aColumnsList, FormatType[] formatTypes, String sheetName) {
		workbook = new HSSFWorkbook();
    	this.columnSet = aColumnsList;
	    sheet = workbook.createSheet(sheetName);
	    boldFont = workbook.createFont();
	    boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    format = workbook.createDataFormat();
	    this.formatTypes = formatTypes;
	    currentRow = 0;
	    writeHeaderRow();
	}
	  
	public void writeHeaderRow() {
		HSSFRow row = sheet.createRow(currentRow);
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

	  public void writeValueRow(List<String> cellValues) 
	  {
		  HSSFRow row = sheet.createRow(currentRow);
	      int numCols = cellValues.size();
	      boolean isAutoDecideFormatTypes;
	      if (isAutoDecideFormatTypes = (formatTypes == null)) {
	        formatTypes = new FormatType[numCols];
	      }
	      int iCell = 0;
	      for (String cloumnValue : cellValues)
	      {
	    	  writeCell(row, iCell, cloumnValue, FormatType.TEXT, null, null);
	    	  iCell++;
	      }		
	     currentRow++;
	  }
	  
	  public HSSFWorkbook getWorkBook()
	  {
		  return workbook;
	  }

//	private FormatType getFormatType(Class _class) {
//	    if (_class == Integer.class || _class == Long.class) {
//	      return FormatType.INTEGER;
//	    } else if (_class == Float.class || _class == Double.class) {
//	      return FormatType.FLOAT;
//	    } else if (_class == Timestamp.class || _class == java.sql.Date.class) {
//	      return FormatType.DATE;
//	    } else {
//	      return FormatType.TEXT;
//	    }
//	  }
	  
//	  public void generate(OutputStream outputStream) throws Exception 
//	  {
//	    try
//	    {
//	      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//	      if (formatTypes != null && formatTypes.length != resultSetMetaData.getColumnCount()) {
//	        throw new IllegalStateException("Number of types is not identical to number of resultset columns. " +
//	            "Number of types: " + formatTypes.length + ". Number of columns: " + resultSetMetaData.getColumnCount());
//	      }
//	      int currentRow = 0;
//	      HSSFRow row = sheet.createRow(currentRow);
//	      int numCols = resultSetMetaData.getColumnCount();
//	      boolean isAutoDecideFormatTypes;
//	      if (isAutoDecideFormatTypes = (formatTypes == null)) {
//	        formatTypes = new FormatType[numCols];
//	      }
//	      for (int i = 0; i < numCols; i++) {
//	        String title = resultSetMetaData.getColumnName(i + 1);
//	        writeCell(row, i, title, FormatType.TEXT, boldFont);
//	        if (isAutoDecideFormatTypes) {
//	          Class _class = Class.forName(resultSetMetaData.getColumnClassName(i + 1));
//	          formatTypes[i] = getFormatType(_class);
//	        }
//	      }
//	      currentRow++;
//	      // Write report rows
//	      while (resultSet.next()) {
//	        row = sheet.createRow(currentRow++);
//	        for (int i = 0; i < numCols; i++) {
//	          Object value = resultSet.getObject(i + 1);
//	          writeCell(row, i, value, formatTypes[i]);
//	        }
//	      }
//	      // Autosize columns
//	      for (int i = 0; i < numCols; i++) {
//	        sheet.autoSizeColumn((short) i);
//	      }
//	      workbook.write(outputStream);
//	    }
//	    finally {
//	      outputStream.close();
//	    }
//	  }
////	  public void generate(File file) throws Exception {
////	    generate(new FileOutputStream(file));
////	  }
////	  private void writeCell(HSSFRow row, int col, Object value, FormatType formatType) throws NestableException {
////	    writeCell(row, col, value, formatType, null, null);
////	  }
////	  private void writeCell(HSSFRow row, int col, Object value, FormatType formatType, HSSFFont font) throws NestableException {
////	    writeCell(row, col, value, formatType, null, font);
////	  }
	  private void writeCell(HSSFRow row, int col, Object value, FormatType formatType,Short bgColor, HSSFFont font)  {
	    HSSFCell cell = HSSFCellUtil.createCell(row, col, null);
	    if (value == null) {
	      return;
	    }
	    if (font != null) {
	      HSSFCellStyle style = workbook.createCellStyle();
	      style.setFont(font);
	      cell.setCellStyle(style);
	    }
	    switch (formatType) {
	      case TEXT:
	        cell.setCellValue(value.toString());
	        break;
	      case INTEGER:
	        cell.setCellValue(((Number) value).intValue());
	        HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
	            HSSFDataFormat.getBuiltinFormat(("#,##0")));
	        break;
	      case FLOAT:
	        cell.setCellValue(((Number) value).doubleValue());
	        HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
	            HSSFDataFormat.getBuiltinFormat(("#,##0.00")));
	        break;
	      case DATE:
	        cell.setCellValue((Timestamp) value);
	        HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
	            HSSFDataFormat.getBuiltinFormat(("m/d/yy")));
	        break;
	      case MONEY:
	        cell.setCellValue(((Number) value).intValue());
	        HSSFCellUtil.setCellStyleProperty(cell, workbook,
	            CellUtil.DATA_FORMAT, format.getFormat("($#,##0.00);($#,##0.00)"));
	        break;
	      case PERCENTAGE:
	        cell.setCellValue(((Number) value).doubleValue());
	        HSSFCellUtil.setCellStyleProperty(cell, workbook,
	        		CellUtil.DATA_FORMAT, HSSFDataFormat.getBuiltinFormat("0.00%"));
	    }
	    if (bgColor != null) {
	      HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_FOREGROUND_COLOR, bgColor);
	      HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
	    }
	  }
	  public enum FormatType {
	    TEXT,
	    INTEGER,
	    FLOAT,
	    DATE,
	    MONEY,
	    PERCENTAGE
	  }
	}