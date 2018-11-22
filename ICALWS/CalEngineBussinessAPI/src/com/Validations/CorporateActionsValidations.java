package com.Validations;

import java.io.FileReader;
import java.util.List;
import com.Service.CAEnum;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CorporateActionsValidations
{
	public static void validateCAAddFile(String inputFilePath,List<String[]> allData) throws Exception
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
    	
        if(!validateHeaderOrderForCAAddFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(ISIN, BBGTicker,Event Code,Effective Date,Gross Amount,Currency,Ratio,Old Value,New Value).");
        	throw new Exception("Input file should have cloumns in order(ISIN, BBGTicker,Event Code,Effective Date,Gross Amount,Currency,Ratio,Old Value,New Value).");
        }
        validateCAAddFileData(allData);
	}
	
	
	private static boolean validateHeaderOrderForCAAddFile(String[] row)
	{
		//ISIN, BBGTicker,Event Code,Effective Date,Gross Amount,Net Amount,Currency,Ratio).");
		if(row[0] != null && !row[0].toString().trim().equals("ISIN"))
			return false;
		if(row[1] != null && !row[1].toString().trim().equals("BBGTicker"))
			return false;
		if(row[2] != null && !row[2].toString().trim().equals("Event Code"))
			return false;
		if(row[3] != null && !row[3].toString().trim().equals("Effective Date"))
			return false;
		if(row[4] != null && !row[4].toString().trim().equals("Gross Amount"))
			return false;
		if(row[5] != null && !row[5].toString().trim().equals("Currency"))
			return false;
		if(row[6] != null && !row[6].toString().trim().equals("Ratio"))
			return false;
		if(row[7] != null && !row[7].toString().trim().equals("Old Value"))
			return false;
		if(row[8] != null && !row[8].toString().trim().equals("New Value"))
			return false;
		
		return true;		
	}
	
	public static void validateCAAddFileData(List<String[]> allData) throws Exception
	{
		boolean isEmpty = true;
		for (String[] row : allData) 
        {
			if(row[0].isEmpty() && row[1].isEmpty() && row[2].isEmpty() && row[3].isEmpty() && row[4].isEmpty() && row[5].isEmpty() && row[6].isEmpty()
					&& row[7].isEmpty()  && row[8].isEmpty())
			{
//				return;
			}
			else
			{
				isEmpty = false;
				if(row[0].isEmpty() || row[1].isEmpty() || row[2].isEmpty() || row[3].isEmpty())
				{
					throw new Exception("Please check input file for Missing data(ISIN, BBGTicker,EventCode,Effective Date)");
				}
				if(row[2].toString().equalsIgnoreCase(CAEnum.ORD_DIV.toString()) || row[2].toString().equalsIgnoreCase(CAEnum.SPL_DIV.toString()))
				{
					if(row[4].isEmpty()  ||  row[5].isEmpty() )
						throw new Exception("Please check input file for Missing data(Gross Amount,Currency)");
					if(Double.parseDouble(row[4]) <=0 )
						throw new Exception("Gross amount should be greater than zero.");
	//				if(Double.parseDouble(row[5]) <=0 )
	//					throw new Exception("New amount should be greater than zero.");
				}
				else if(row[2].toString().equalsIgnoreCase(CAEnum.STOCK_SPLT.toString()) || row[2].toString().equalsIgnoreCase(CAEnum.DVD_STOCK.toString()))
				{
					if(row[6].isEmpty())
						throw new Exception("Please check input file for Missing data(Ratio)");
					if(Double.parseDouble(row[6]) <=0 )
						throw new Exception("Ratio should be greater than zero.");
				}	
				else if(row[2].toString().equalsIgnoreCase(CAEnum.CHG_ID.toString()) || row[2].toString().equalsIgnoreCase(CAEnum.CHG_NAME.toString())
						 || row[2].toString().equalsIgnoreCase(CAEnum.CHG_TKR.toString()))
				{
					if(row[7].isEmpty())
						throw new Exception("Please check input file for Missing data(Old Value)");
					if(row[8].isEmpty())
						throw new Exception("Please check input file for Missing data(New Value)");
				}	
				duplicateCACheck(row);
				
				validateISIN(row[0].toString());
			}
			if(isEmpty)
	        {
	        	System.out.println("Input file is empty");
	        	throw new Exception("Input file is empty");
	        }
//			validateSecurityTicker(row[2].toString());
        }
	}

	private static void duplicateCACheck(String[] row) throws Exception 
	{
//		throw new Exception("Corporate action with ISIN (" + row[0].toString() +"),BBGTicke("+row[1].toString()+"),Event Code ("+row[2].toString()
//				+") and Effective Date("+row[3].toString()+ ") already exists");
		// TODO Auto-generated method stub
		
	}


	public static void validateISIN(String strISN) throws Exception
	{
		//text.matches("^[a-zA-Z0-9]+$")
		String strRegExp = "^[a-zA-Z]{2}[a-zA-Z0-9]{10}$";
		
		if(!strISN.matches(strRegExp))
			throw new Exception("Please check input file for Invalid data(ISIN should be of 12 digits with first 2 digits as Alphabet and remaining 10 as Alphanumeric.)");
	}
	
}
