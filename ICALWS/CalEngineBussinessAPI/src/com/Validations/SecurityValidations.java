package com.Validations;

import java.io.FileReader;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.DataService.SecurityDao;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class SecurityValidations
{
	public static void validateSecurityCheckFile(String inputFilePath,List<String[]> allData) throws Exception
	{
		FileReader filereader = new FileReader(inputFilePath);
    	
    	CSVReader csvHeaderReader = new CSVReaderBuilder(filereader).build();
    	String[] headerRow = csvHeaderReader.readNext();
    	csvHeaderReader.close();
    	
        if(allData.size() == 0)
        {
        	System.out.println("Input file is empty");
        	throw new Exception("Input file is empty");
        }
       
        if(!ValidateColumnsOrder.validateHeaderOrderForCheckSecuritiesFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency).");
        	throw new Exception("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency).");
        }
        validateSecurityCheckFileData(allData);
	}
	
	public static void validateSecurityCheckFileData(List<String[]> allData) throws Exception
	{
		boolean isEmpty = true;
		for (String[] row : allData) 
        {
			if(row[0].isEmpty() && row[1].isEmpty() && row[2].isEmpty() && row[3].isEmpty())
			{
			}
			else
			{
				isEmpty = false;
				if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase(""))
				{
					throw new Exception("Please check input file for Missing data(ISIN, TICKER,Currency)");
				}
				CommonValidations.validateSecurityISIN(row[1].toString());
			}
        }	
		if(isEmpty)
        {
        	System.out.println("Input file is empty");
        	throw new Exception("Input file is empty");
        }
	}
	
	public static void validateSecurityAddFile(String inputFilePath,List<String[]> allData) throws Exception
	{
		FileReader filereader = new FileReader(inputFilePath);
    	
    	CSVReader csvHeaderReader = new CSVReaderBuilder(filereader).build();
    	String[] headerRow = csvHeaderReader.readNext();
    	csvHeaderReader.close();
    	
        if(allData.size() == 0)
        {
        	System.out.println("Input file is empty");
        	throw new Exception("Input file is empty");
        }
    	
        if(!ValidateColumnsOrder.validateHeaderOrderForSecuritiesAddFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency,SEDOL,CUSIP,Country,Sector,Industry,Sub Industry).");
        	throw new Exception("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency,SEDOL,CUSIP,Country,Sector,Industry,Sub Industry).");
        }
        validateSecurityAddFileData(allData);
	}	
	
	public static void validateSecurityAddFileData(List<String[]> allData) throws Exception
	{
		SecurityDao sDao = new SecurityDao();
		Map<String,String> securityDuplicateMap = new HashMap<String,String>();
		
		boolean isEmpty = true;
		for (String[] row : allData) 
        {
			if(row[0].isEmpty() && row[1].isEmpty() && row[2].isEmpty() && row[3].isEmpty() && row[4].isEmpty() && row[5].isEmpty() && row[6].isEmpty() && row[7].isEmpty()
					 && row[8].isEmpty() && row[9].isEmpty())
			{
			}
			else
			{
				isEmpty = false;
				String strKey = row[1].toString() + "-" + row[2].toString() + "-" + row[3].toString();
				
				if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase(""))
				{
					throw new Exception("Please check input file for Missing data(ISIN, TICKER,Currency)");
				}
				CommonValidations.validateSecurityISIN(row[1].toString());
				CommonValidations.validateSecurityTicker(row[2].toString());
				
				if(securityDuplicateMap.get(strKey) != null)
				{
					throw new Exception("Please check input file for duplicate data");
				}
				else if(sDao.getSecurityId(row[1].toString(),row[2].toString(),row[3].toString()) != 0)
				{
					throw new Exception("Please check input file.Security already exist with ISIN("+row[1].toString()+") and BBGTicker("+row[2].toString()+") and Currency (" + row[3].toString()+").");
				}			
				else
					securityDuplicateMap.put(strKey,strKey);
			}
        }	
		if(isEmpty)
        {
        	System.out.println("Input file is empty");
        	throw new Exception("Input file is empty");
        }
	}
	
	public static void validateSecurityPriceFile(String inputFilePath,List<String[]> allData) throws Exception
	{
		FileReader filereader = new FileReader(inputFilePath);
    	
    	CSVReader csvHeaderReader = new CSVReaderBuilder(filereader).build();
    	String[] headerRow = csvHeaderReader.readNext();
    	csvHeaderReader.close();
    	
        if(allData.size() == 0)
        {
        	System.out.println("Input file is empty");
        	
        	throw new Exception("Input file is empty");
        }
        System.out.println(allData.size());
        if(!ValidateColumnsOrder.validateHeaderOrderForSecuritiesPriceFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(ISIN, BBGTICKER, CURRENCY, PRICE,DATE).");
        	throw new Exception("Input file should have cloumns in order(ISIN, BBGTICKER, CURRENCY, PRICE,DATE).");
        }
        validateSecurityPriceFileData(allData);
	}

	
	private static void validateSecurityPriceFileData(List<String[]> allData) throws Exception
	{
		boolean isEmpty = true;
		Map<String,String> securityPriceDuplicateMap = new HashMap<String,String>();
		SecurityDao sDao = new SecurityDao();
		for (String[] row : allData) 
        {
			if(row[0].isEmpty() && row[1].isEmpty() && row[2].isEmpty() && row[3].isEmpty() && row[4].isEmpty())
			{
			}
			else
			{
				isEmpty = false;
				String strKey = row[0].toString() + "-" + row[1].toString() + "-" + row[2].toString() + "-" + row[4].toString();
				
				if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase("")
						|| row[0].toString().equalsIgnoreCase("") || row[4].toString().equalsIgnoreCase("") )
				{
					throw new Exception("Please check input file for Missing or invalid data(ISIN, BBGTICKER, CURRENCY, PRICE, DATE)");
				}
				
				CommonValidations.validateSecurityISIN(row[0].toString());
				CommonValidations.validateSecurityTicker(row[1].toString());
				if(!CommonValidations.validateAmount(Double.parseDouble(row[3])))
					throw new Exception("Please check input file for Price(Security price should be greater then zero.)");
				
				if(securityPriceDuplicateMap.get(strKey) != null)
				{
					throw new Exception("Please check input file for duplicate data for same ISIN, BBGTICKER, CURRENCY and DATE");
				}
				else
				{
					ResultSet rs = sDao.getSecutiryPrice(row[0].toString(),row[1].toString(),row[2].toString(),row[4].toString());
					int id = 0;
					while(rs.next())
					{
						id =  rs.getInt(0);			
					}
					
					if( rs != null && id != 0)
					{
						throw new Exception("Please check input file.Security Price already exist with ISIN("+row[0].toString()+") and BBGTicker("+row[1].toString()+") and Currency (" + row[2].toString()+") for date (" + row[4].toString() + ").");
					}
					else
						securityPriceDuplicateMap.put(strKey,strKey);
				}
			}
        }	
		if(isEmpty)
        {
        	System.out.println("Input file is empty");
        	throw new Exception("Input file is empty");
        }
	}
}