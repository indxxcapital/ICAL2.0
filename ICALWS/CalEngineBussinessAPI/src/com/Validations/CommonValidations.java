package com.Validations;

import java.sql.ResultSet;

import com.Bean.SecurityBean;
import com.DataService.SecurityDao;

public class CommonValidations 
{
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
	
	public static boolean validateAmount(Double dblValue) throws Exception
	{
		if(dblValue <= 0)
			return false;
		else
			return true;			
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
