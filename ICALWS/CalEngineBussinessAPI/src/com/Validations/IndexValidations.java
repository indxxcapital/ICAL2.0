package com.Validations;

import java.io.FileReader;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class IndexValidations 
{
	public static void validateIndexFile(String inputFilePath,List<String[]> allData) throws Exception
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
    	
        if(!validateheckHeaderOrderForIndexFile(headerRow))
        {
        	System.out.println("Input file should have cloumns in order(Index Name,	Client Name, Index Ticker, Zone, Index Type, Currency, Index Value, Index Market Value,	Normal Cash Dividend Adjustment, Special Cash Dividend Adjustment, Index Live Date,	Dissemination Source).");
        	throw new Exception("Input file should have cloumns in order(Index Name,	Client Name, Index Ticker, Zone, Index Type, Currency, Index Value, Index Market Value,	Normal Cash Dividend Adjustment, Special Cash Dividend Adjustment, Index Live Date,	Dissemination Source).");
        }
        validateIndexFileData(allData);
	}
	
	private static boolean validateheckHeaderOrderForIndexFile(String[] row)
	{
		//Index Name	Client Name	Index Ticker	Zone	Index Type	Currency	Index Value	Index Market Value	
//		Normal Cash Dividend Adjustment	Special Cash Dividend Adjustment	Index Live Date	Dissemination Source

//		if(row.length != 3)
//			return false;
		if(row[0] != null && !row[0].toString().trim().equals("Index Name"))
			return false;
		if(row[1] != null && !row[1].toString().trim().equals("Client Name"))
			return false;
		if(row[2] != null && !row[2].toString().trim().equals("Index Ticker"))
			return false;
		if(row[3] != null && !row[3].toString().trim().equals("Zone"))
			return false;
		if(row[4] != null && !row[4].toString().trim().equals("Index Type"))
			return false;
		if(row[5] != null && !row[5].toString().trim().equals("Currency"))
			return false;
		if(row[6] != null && !row[6].toString().trim().equals("Index Value"))
			return false;
		if(row[7] != null && !row[7].toString().trim().equals("Index Market Value"))
			return false;
		if(row[8] != null && !row[8].toString().trim().equals("Normal Cash Dividend"))
			return false;
		if(row[9] != null && !row[9].toString().trim().equals("Special Cash Dividend"))
			return false;
		if(row[10] != null && !row[10].toString().trim().equals("Index Live Date"))
			return false;
		if(row[11] != null && !row[11].toString().trim().equals("Dissemination Source"))
			return false;
		
		return true;		
	}
	
	public static void validateIndexFileData(List<String[]> allData) throws Exception
	{
		boolean isEmpty = true;
		for (String[] row : allData) 
        {
			if(row[0].isEmpty() && row[1].isEmpty() && row[2].isEmpty() && row[3].isEmpty() && row[4].isEmpty() && row[5].isEmpty() && row[6].isEmpty()
					&& row[7].isEmpty() && row[8].isEmpty() && row[9].isEmpty() && row[10].isEmpty() && row[11].isEmpty())
			{
//				isEmpty = true;
			}
			else
			{
				isEmpty = false;
				if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase("") 
						|| row[4].toString().equalsIgnoreCase("") || row[5].toString().equalsIgnoreCase("") || row[6].toString().equalsIgnoreCase("") 
						|| row[7].toString().equalsIgnoreCase("") || row[8].toString().equalsIgnoreCase("") || row[9].toString().equalsIgnoreCase("") 
						|| row[10].toString().equalsIgnoreCase("") )
				{
					throw new Exception("Please check input file for Missing data(Index Name,	Client Name, Index Ticker, Zone, Index Type, Currency, Index Value, Index Market Value,	Normal Cash Dividend Adjustment, Special Cash Dividend Adjustment, Index Live Date)");
				}
			}
        }	
		if(isEmpty)
        {
        	System.out.println("Input file is empty");
        	throw new Exception("Input file is empty");
        }
		
//		for (String[] row : allData) 
//        {
//			if(row[1].toString().equalsIgnoreCase("") || row[2].toString().equalsIgnoreCase("") || row[3].toString().equalsIgnoreCase(""))
//			{
//				throw new Exception("Please check input file for Missing data(ISIN, TICKER,Currency)");
//			}
////			validateSecurityISIN(row[1].toString());
////			validateSecurityTicker(row[2].toString());
//        }
	}
}
