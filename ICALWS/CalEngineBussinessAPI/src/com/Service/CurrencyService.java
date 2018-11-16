package com.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.Bean.CurrencyBean;
import com.DataService.ConfigUtil;
import com.DataService.DefaultDao;
import com.DataService.IndexDao;
import com.Validations.CurrencyValidations;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class CurrencyService {

	public boolean parseCurrencyFile(String inputFilePath,String outFilePath) throws IOException,Exception
	{
		try
		{
			parseCsvFile(inputFilePath, outFilePath);
			return true;
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			return true;
		}
	}
	
	public void parseCsvFile(String inputFilePath,String outFilePath) throws Exception
	{	
		FileReader filereader = new FileReader(inputFilePath);
		
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
		List<String[]> allData = csvReader.readAll();
		CSVWriter csvWriter = ICalUtil.generateCsvTemplate(ICalUtil.getCurrencyHeaderListForMissingCurrencies(), outFilePath);

		CurrencyValidations.validateCurrencyFile(inputFilePath, allData);
		
		for (String[] row : allData) 
		{
			if (!row[0].isEmpty()) 	
			{
				CurrencyBean currencyBean = new CurrencyBean();
				ICalUtil.setCurrencyBeanForCurrencyCheck(currencyBean,row);        	
				if(!checkForExistingCurrencies(currencyBean))
				{
					csvWriter.writeNext(ICalUtil.getCurrencyValueListForMisiingCurrency(currencyBean));
				}
			}
		}  
        filereader.close();
        csvReader.close();
        csvWriter.close();
	}
	 
	public boolean checkForExistingCurrencies(CurrencyBean currencyBean) throws ClassNotFoundException, SQLException ,Exception
	{
		String strQuery = "SELECT * FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currency where crrysymbol='" +currencyBean.getCurrencySymbol() + "'";
		DefaultDao sDao = new DefaultDao();
		ResultSet rs = sDao.ExecuteQuery(strQuery);
		if(rs == null || !rs.next())
			return false;
		else
			return true;
	}	
	
//	public String importCurrencyDataFromCsv(String outFilePath)
//	{
//		List<CurrencyBean> currencyList = new ArrayList<>();
//		try {
//	        // Create an object of file reader
//	        // class with CSV file as a parameter.
//	        FileReader filereader = new FileReader(outFilePath);
//	 
//	        // create csvReader object and skip first Line
//	        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
//	        List<String[]> allData = csvReader.readAll();
//	 
//	        // print Data
//	        for (String[] row : allData) 
//	        {
//	        	if (!row[1].isEmpty()) 	
//	        	{
//	        		CurrencyBean currencyBean = new CurrencyBean();
//		        	ICalUtil.setCurrencyBeanToAddCurrency(currencyBean,row);
//					
//		        	currencyList.add(currencyBean);
//	        	}
//	        }
//		
//			DefaultDao dDao = new DefaultDao();
//			for (CurrencyBean currency : currencyList)
//			{
//				Map<String,Object> keyValueMap = new HashMap<String,Object>();
//				keyValueMap.put("crrysymbol", currency.getCurrencySymbol());
//				keyValueMap.put("crryname", currency.getCurrencyName());
//				dDao.insertData("currency",keyValueMap);
//			}
//		}
//	    catch (Exception e) {
//	        e.printStackTrace();
//	    }
//		System.out.println("Success import excel to mysql table");
//		return "bussinessAPI";
//	}
	
	public List<CurrencyBean> getAllCurrency() throws Exception
	{
		List<CurrencyBean> currencyList = new ArrayList<>();
		
		DefaultDao dDao = new DefaultDao();
		try
		{
			String strQuery = " SELECT DISTINCT crrysymbol,crryname FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currency order by crrysymbol asc ";
			
			ResultSet rs = dDao.ExecuteQuery(strQuery) ;
			
			currencyList =convertListToCurrencyBeanList(rs);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currencyList;  
	}
	
	public List<CurrencyBean> convertListToCurrencyBeanList(ResultSet rs) throws SQLException, ClassNotFoundException
	{
		List<CurrencyBean> currencyList = new ArrayList<>();
		while (rs.next())
		{
			CurrencyBean cBean = new CurrencyBean(); 
			getCurrencyyBeanFromResultSet(rs,cBean);
			currencyList.add(cBean);
	    }		
		return currencyList;
	}
	
	private void getCurrencyyBeanFromResultSet(ResultSet rs,CurrencyBean cBean) throws SQLException
	{
		if(rs.getString("crryname") != null)
			cBean.setCurrencyName(rs.getString("crryname"));	
		if(rs.getString("crrysymbol") != null)
			cBean.setCurrencySymbol(rs.getString("crrysymbol"));
	}
	
	public Double currencyMultiplier(String fromCurrency,String toCurrency)
	{
		Double currencyMultiplier = (double) 1;
		System.out.println("print query");
		String STR_CURRENCY_RATE_QUERY_1 = 
				" (SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where fromCurrency = '" + fromCurrency 
				+ "' and toCurrency = '" + toCurrency +"')";
		
		String STR_CURRENCY_RATE_QUERY_2 = 
				" (SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where fromCurrency = '" + toCurrency 
				+ "' and toCurrency = '" + fromCurrency +"')" ;
		
		System.out.println(STR_CURRENCY_RATE_QUERY_1);
		DefaultDao iDao = new DefaultDao();
		ResultSet rs = null;
		try 
		{
			rs = iDao.ExecuteQuery(STR_CURRENCY_RATE_QUERY_1);
			if(rs != null)
			{
				while (rs.next())
				{
					currencyMultiplier = rs.getDouble("rate");
			    }
			}
			else
			{
				rs = iDao.ExecuteQuery(STR_CURRENCY_RATE_QUERY_2);
				if(rs != null)
				{
					while (rs.next())
					{
						currencyMultiplier = 1/rs.getDouble("rate");
				    }
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return currencyMultiplier;		
	}
}