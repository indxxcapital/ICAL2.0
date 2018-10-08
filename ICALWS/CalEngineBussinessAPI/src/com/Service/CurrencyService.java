package com.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Bean.CurrencyBean;
import com.Bean.IndexBean;
import com.DataService.ConfigUtil;
import com.DataService.DefaultDao;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class CurrencyService {

	public boolean parseCurrencyFile(String inputFilePath,String outFilePath) throws IOException
	{
		try
		{
			parseCsvFile(inputFilePath, outFilePath);
			return true;
		}
		catch (Exception e) {
			return true;
		}
	}
	 public void parseCsvFile(String inputFilePath,String outFilePath) throws IOException
		{	
	    	FileReader filereader = new FileReader(inputFilePath);
	   	 
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	        List<String[]> allData = csvReader.readAll();
	        CSVWriter csvWriter = ICalUtil.generateCsvTemplate(ICalUtil.getCurrencyHeaderListForMissingCurrencies(), outFilePath);
	        // print Data
	        for (String[] row : allData) 
	        {
	        	if (!row[0].isEmpty()) 	
	        	{
		        	CurrencyBean currencyBean = new CurrencyBean();
		
		        	ICalUtil.setCurrencyBeanForCurrencyCheck(currencyBean,row);        	
		 			
					try 
					{
						if(!checkForExistingCurrencies(currencyBean))
						{
						    csvWriter.writeNext(ICalUtil.getCurrencyValueListForMisiingCurrency(currencyBean));
						}
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
	        	}
			}  
	        filereader.close();
	        csvReader.close();
	        csvWriter.close();
		}
	 
	public boolean checkForExistingCurrencies(CurrencyBean currencyBean) throws ClassNotFoundException, SQLException
	{
		String strQuery = "SELECT * FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currency where crrysymbol='" +currencyBean.getCurrencySymbol() + "'";
		DefaultDao sDao = new DefaultDao();
		ResultSet rs = sDao.ExecuteQuery(strQuery);
		if(rs == null || !rs.next())
			return false;
		else
			return true;
	}	
	
	public String importCurrencyDataFromCsv(String outFilePath)
	{
		List<CurrencyBean> currencyList = new ArrayList<>();
		try {
	        // Create an object of file reader
	        // class with CSV file as a parameter.
	        FileReader filereader = new FileReader(outFilePath);
	 
	        // create csvReader object and skip first Line
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	        List<String[]> allData = csvReader.readAll();
	 
	        // print Data
	        for (String[] row : allData) 
	        {
	        	if (!row[1].isEmpty()) 	
	        	{
	        		CurrencyBean currencyBean = new CurrencyBean();
		        	ICalUtil.setCurrencyBeanToAddCurrency(currencyBean,row);
					
		        	currencyList.add(currencyBean);
	        	}
	        }
		
			DefaultDao dDao = new DefaultDao();
			for (CurrencyBean currency : currencyList)
			{
				Map<String,Object> keyValueMap = new HashMap<String,Object>();
				keyValueMap.put("crrysymbol", currency.getCurrencySymbol());
				keyValueMap.put("crryname", currency.getCurrencyName());
				dDao.insertData("currency",keyValueMap);
			}
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		System.out.println("Success import excel to mysql table");
		return "bussinessAPI";
	}
	
	public List<CurrencyBean> getAllCurrency()
	{
		List<CurrencyBean> currencyList = new ArrayList<>();
		
		DefaultDao dDao = new DefaultDao();
		try
		{
			String strQuery = " SELECT DISTINCT crrysymbol,crryname FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currency order by crrysymbol asc ";
			
			ResultSet rs = dDao. ExecuteQuery(strQuery) ;
			
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
}
