package com.Validations;

import java.io.FileReader;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Bean.SecurityBean;
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
    	
        if(!validateheckHeaderOrderForCheckSecuritiesFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency).");
        	throw new Exception("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency).");
        }
        validateSecurityCheckFileData(allData);
        
	}
	
	private static boolean validateheckHeaderOrderForCheckSecuritiesFile(String[] row)
	{
//		if(row.length != 3)
//			return false;
		if(row[0] != null && !row[0].toString().trim().equals("Security Name"))
			return false;
		if(row[1] != null && !row[1].toString().trim().equals("ISIN"))
			return false;
		if(row[2] != null && !row[2].toString().trim().equals("TICKER"))
			return false;
		if(row[3] != null && !row[3].toString().trim().equals("Currency"))
			return false;
		return true;		
	}
	
	public static void validateSecurityCheckFileData(List<String[]> allData) throws Exception
	{
		for (String[] row : allData) 
        {
			if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase(""))
			{
				throw new Exception("Please check input file for Missing data(ISIN, TICKER,Currency)");
			}
			validateSecurityISIN(row[1].toString());
//			validateSecurityTicker(row[2].toString());
        }
	}
	
	public static void validateSecurityISIN(String strISN) throws Exception
	{
		//text.matches("^[a-zA-Z0-9]+$")
		String strRegExp = "^[a-zA-Z]{2}[a-zA-Z0-9]{10}$";
		
		if(!strISN.matches(strRegExp))
			throw new Exception("Please check input file for Invalid data(ISIN should be of 12 digits with first 2 digits as Alphabet and remaining 10 as Alphanumeric.)");
	}
	
	public static void validateSecurityTicker(String strTicker) throws Exception
	{
		//text.matches("^[a-zA-Z0-9]+$")
//		String strRegExp = "^[a-zA-Z0-9]*$";
		String strRegExp = "^[a-zA-Z ]*$";
		
		if(!strTicker.matches(strRegExp))
			throw new Exception("Please check input file for Invalid data(Please enter valid Ticker.)");
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
    	
        if(!validateHeaderOrderForSecuritiesAddFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency,SEDOL,CUSIP,Country,Sector,Industry,Sub Industry).");
        	throw new Exception("Input file should have cloumns in order(Security Name, ISIN, TICKER,Currency,SEDOL,CUSIP,Country,Sector,Industry,Sub Industry).");
        }
        validateSecurityAddFileData(allData);
	}
	
	private static boolean validateHeaderOrderForSecuritiesAddFile(String[] row) 
	{
		try
		{
		//"Security Name", "ISIN", "TICKER","Currency","SEDOL","CUSIP","Country","Sector","Industry","Sub Industry"};
		if(row[0] != null && !row[0].toString().trim().equals("Security Name"))
			return false;
		if(row[1] != null && !row[1].toString().trim().equals("ISIN"))
			return false;
		if(row[2] != null && !row[2].toString().trim().equals("TICKER"))
			return false;
		if(row[3] != null && !row[3].toString().trim().equals("Currency"))
			return false;
		if(row[4] != null && !row[4].toString().trim().equals("SEDOL"))
			return false;
		if(row[5] != null && !row[5].toString().trim().equals("CUSIP"))
			return false;
		if(row[6] != null && !row[6].toString().trim().equals("Country"))
			return false;
		if(row[7] != null && !row[7].toString().trim().equals("Sector"))
			return false;
		if(row[8] != null && !row[8].toString().trim().equals("Industry"))
			return false;
		if(row[9] != null && !row[9].toString().trim().equals("Sub Industry"))
			return false;
		}
		catch (IndexOutOfBoundsException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;		
	}
	
	public static void validateSecurityAddFileData(List<String[]> allData) throws Exception
	{
		SecurityDao sDao = new SecurityDao();
		Map<String,String> securityDuplicateMap = new HashMap<String,String>();
		for (String[] row : allData) 
        {
			String strKey = row[1].toString() + "-" + row[2].toString() + "-" + row[3].toString();
			
			if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase(""))
			{
				throw new Exception("Please check input file for Missing data(ISIN, TICKER,Currency)");
			}
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
			validateSecurityISIN(row[1].toString());
			validateSecurityTicker(row[2].toString());
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
    	
        if(!validateHeaderOrderForSecuritiesPriceFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(ISIN, BBGTICKER, CURRENCY, PRICE,	DATE).");
        	throw new Exception("Input file should have cloumns in order(ISIN, BBGTICKER, CURRENCY, PRICE,	DATE).");
        }
        validateSecurityPriceFileData(allData);
	}

	private static boolean validateHeaderOrderForSecuritiesPriceFile(String[] row)
	{
		//ISIN	BBGTICKER	CURRENCY	PRICE	DATE

		if(row[0] != null && !row[0].toString().trim().equals("ISIN"))
			return false;
		if(row[1] != null && !row[1].toString().trim().equals("BBGTICKER"))
			return false;
		if(row[2] != null && !row[2].toString().trim().equals("CURRENCY"))
			return false;
		if(row[3] != null && !row[3].toString().trim().equals("PRICE"))
			return false;
		if(row[4] != null && !row[4].toString().trim().equals("DATE"))
			return false;
		
		return true;	
	}

	private static void validateSecurityPriceFileData(List<String[]> allData) throws Exception
	{
		for (String[] row : allData) 
        {
			if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase("")
					|| row[0].toString().equalsIgnoreCase("") || row[4].toString().equalsIgnoreCase("") )
			{
				throw new Exception("Please check input file for Missing or invalid data(ISIN, BBGTICKER, CURRENCY, PRICE,	DATE)");
			}
			validateSecurityISIN(row[0].toString());
			validateSecurityTicker(row[1].toString());
        }		
	}

	public static boolean checkForExistingSecurities(SecurityBean sBean) throws Exception
	{
		SecurityDao sDao = new SecurityDao();
		ResultSet rs = sDao.getSecutiry(sBean);
		if(rs == null || !rs.next())
			return false;
		else
			return true;
	}
}