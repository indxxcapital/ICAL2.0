package com.Validations;

import java.io.FileReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CurrencyValidations
{
	public static void validateCurrencyFile(String inputFilePath,List<String[]> allData) throws Exception
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
    	
        if(!validateHeaderOrderForCurrencyFile(headerRow))
        {
        	System.out.println("Input file should have cloumn(Currency Symbol).");
        	throw new Exception("Input file should have cloumn(Currency Symbol).");
        }
        validateCurrencyFileData(allData);
	}
	
	private static boolean validateHeaderOrderForCurrencyFile(String[] row)
	{
		//Currency Symbol
		if(row[0] != null && !row[0].toString().trim().equals("Currency Symbol"))
			return false;
		
		return true;		
	}
	
	public static void validateCurrencyFileData(List<String[]> allData) throws Exception
	{
		boolean isEmpty = true;
		for (String[] row : allData) 
        {
			if(!row[0].isEmpty())
			{
				isEmpty = false;
			}
        }	
		if(isEmpty)
        {
        	System.out.println("Input file is empty");
        	throw new Exception("Input file is empty");
        }
		
		for (String[] row : allData) 
        {
			if(row[0].toString().equalsIgnoreCase(""))
			{
				throw new Exception("Please check input file for Missing data(Currency Symbol)");
			}
        }
	}
}
