package com.Validations;

public class ValidateColumnsOrder 
{
	public static boolean validateHeaderOrderForCheckSecuritiesFile(String[] row)
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
	
	public static boolean validateHeaderOrderForSecuritiesAddFile(String[] row) 
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
	
	public static boolean validateHeaderOrderForSecuritiesPriceFile(String[] row)
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


}
