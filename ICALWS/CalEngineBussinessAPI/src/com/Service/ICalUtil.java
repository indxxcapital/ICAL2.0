package com.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.Bean.CurrencyBean;
import com.Bean.IndexBean;
import com.Bean.SecurityBean;
import com.Bean.SecurityPriceBean;
import com.opencsv.CSVWriter;

public class ICalUtil
{
	public static void setSecurityBeanForSecurityCheck(SecurityBean securityBean,String[] row)
	{
		if(row[0] != null)
			securityBean.setFullName(row[0].toString());
		if(row[1] != null)
			securityBean.setISIN(row[1].toString());
		if(row[2] != null)
			securityBean.setBBGTicker(row[2].toString());
	}
	
	public static String[] getSecurityHeaderListForMissingSecurities()
	{
		String[] headerRecord = {"Security Name", "ISIN", "TICKER","Currency","SEDOL","CUSIP","Country","Sector","Industry","Sub Industry"};
		return headerRecord;
	}
	
	public static String[] getSecurityValueListForMisiingSecurities(SecurityBean sBean)
	{
		String[] headerRecord = { sBean.getFullName(), sBean.getISIN(),sBean.getBBGTicker(),"","","","","","",""};
		return headerRecord;
	}
	public static void setSecurityBeanToAddSecurity(SecurityBean securityBean,String[] row)
	{
		if(row[0] != null)
			securityBean.setFullName(row[0].toString());
		if(row[1] != null)
			securityBean.setISIN(row[1].toString());
		if(row[3] != null)
			securityBean.setBBGTicker(row[2].toString());
		if(row[3] != null)
			securityBean.setCurrency(row[3].toString());
		if(row[4] != null)
			securityBean.setSEDOL(row[4].toString());
		if(row[5] != null)
			securityBean.setCUSIP(row[5].toString());
		if(row[6] != null)
			securityBean.setCountry(row[6].toString());
		if(row[7] != null)
			securityBean.setSector(row[7].toString());
		if(row[8] != null)
			securityBean.setIndustry(row[8].toString());
		if(row[9] != null)
			securityBean.setSubIndustry(row[9].toString());
	}
	
	public static void setSecurityBeanToMapSecurity(SecurityBean securityBean,String[] row)
	{
		if(row[0] != null)
			securityBean.setIndexCode(row[0].toString());
		if(row[1] != null)
			securityBean.setISIN(row[1].toString());
		if(row[2] != null)
			securityBean.setBBGTicker(row[2].toString());
		if(row[3] != null && !row[3].toString().equalsIgnoreCase(""))
			securityBean.setWeight(Float.parseFloat(row[3]));
		if(row[4] != null && !row[4].toString().equalsIgnoreCase(""))
			securityBean.setShares(Float.parseFloat(row[4]));
	}
	
	public static void setSecurityPriceBean(SecurityPriceBean securityPriceBean,String[] row) throws ParseException
	{
		if(row[0] != null)
			securityPriceBean.setISIN(row[0].toString());
		if(row[1] != null)
			securityPriceBean.setBBGTicker(row[1].toString());
		if(row[2] != null)
			securityPriceBean.setCurrencySymbol(row[2].toString());
		if(row[3] != null)
			securityPriceBean.setCurrencyPrice(Float.parseFloat(row[3]));
		if(row[4] != null)
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = simpleDateFormat.parse(row[4].toString());
			securityPriceBean.setVd((simpleDateFormat.format(date)));
		}
	}

	public static void setIndexBean(IndexBean indexBean,String[] row, Boolean isProprietaryWeightedIndices) throws ParseException
	{
		if(row[0] != null)
			indexBean.setIndexlName(row[0].toString());
		if(row[1] != null)
			indexBean.setClientName(row[1].toString());
		if(row[2] != null)
			indexBean.setIndexTicker(row[2].toString());
		if(row[3] != null)
			indexBean.setZoneType(row[3].toString());
		if(row[4] != null)
			indexBean.setIndexType(row[4].toString());
		if(row[5] != null)			
			indexBean.setCurrency(row[5].toString());
		if(row[6] != null)
			indexBean.setCloseIndexValue(Float.parseFloat(row[6].toString()));
		if(row[7] != null)
			indexBean.setIndexMarketValue(Float.parseFloat(row[7].toString()));		
		if(row[8] != null)
		{
//			if(row[8].toString().toLowerCase().contains("divisor"))
//				indexBean.setNormalCashDivAdj("DA");
//			else if(row[8].toString().toLowerCase().contains("stock"))
				indexBean.setNormalCashDivAdj(row[8].toString());//"SA");
		}
		if(row[9] != null)
		{
//			if(row[9].toString().toLowerCase().contains("divisor"))
//				indexBean.setSpecialCashDivAdj("DA");
//			else if(row[9].toString().toLowerCase().contains("stock"))
				indexBean.setNormalCashDivAdj(row[8].toString());//"SA");
		}
		if(row[10] != null)
		{
			String sDate1=row[10].toString();//"31-10-1998";  
		    Date date=new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);  
		    
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD-MM-YYYY");
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("YYYY-MM-dd");
//			Date date = simpleDateFormat.parse(row[10].toString());
			String strDate =simpleDateFormat1.format(date);
			indexBean.setIndexLiveDate(strDate);
//			indexBean.setIndexLiveDate(row[10].toString());
//			indexBean.setIndexLiveDateStr((simpleDateFormat.format(date)));
			indexBean.setIndexLiveDateStr(row[10].toString());
		}
		if(row[11] != null)
			indexBean.setDisseminationSource(row[11].toString());
//		if(row[12] != null)
//			indexBean.setOutputFilesFormat(row[12].toString());
//		indexBean.setStatus("NI");
		indexBean.setStatus("New");
		
		if(isProprietaryWeightedIndices)
			indexBean.setIndexWeightType("PWI");
		else
			indexBean.setIndexWeightType("MWI");
	}
	
	public static String[] getClosingFileHeaderList()
	{
		String[] headerRecord = {"Effective Date", "TICKER", "Name", "ISIN","SEDOL","CUSIP","Country","Index Shares","Weight","Price",
				"Currency","Currency Factor"};
		return headerRecord;
	}
	
	public static CSVWriter generateCsvTemplate(String[] headerRecord,String filePath) throws IOException
	{
		Writer writer;
		CSVWriter csvWriter = null;
		 
		try
		{
			writer = Files.newBufferedWriter(Paths.get(filePath));
			csvWriter = new CSVWriter(writer,CSVWriter.DEFAULT_SEPARATOR,CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
	 
			csvWriter.writeNext(headerRecord);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		writer.close();
		return csvWriter;
	}
	public static String[] getCurrencyHeaderListForMissingCurrencies()
	{
		String[] headerRecord = {"Currency Symbol", "Currency Name"};
		return headerRecord;
	}
	
	public static String[] getCurrencyValueListForMisiingCurrency(CurrencyBean currencyBean)
	{
		String[] headerRecord = { currencyBean.getCurrencySymbol(),""};
		return headerRecord;
	}
	
	public static void setCurrencyBeanForCurrencyCheck(CurrencyBean currencyBean,String[] row)
	{
		if(row[0] != null)
			currencyBean.setCurrencySymbol(row[0].toString());
	}
	
	public static void setCurrencyBeanToAddCurrency(CurrencyBean currencyBean,String[] row)
	{
		if(row[0] != null)
			currencyBean.setCurrencySymbol(row[0].toString());
		if(row[1] != null)
			currencyBean.setCurrencyName(row[1].toString());
	}
}
