package com.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Bean.SecurityBean;
import com.Bean.SecurityPriceBean;
import com.DataService.SecurityDao;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class SecurityService 
{
	public boolean parseSecurityFile(String inputFilePath,String outFilePath) throws IOException
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
   	 
        // create csvReader object and skip first Line
        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
        List<String[]> allData = csvReader.readAll();
        CSVWriter csvWriter = ICalUtil.generateCsvTemplate(ICalUtil.getSecurityHeaderListForMissingSecurities(), outFilePath);
        // print Data
        for (String[] row : allData) 
        {
        	if (!row[2].isEmpty()) 	
        	{
	        	SecurityBean securityBean = new SecurityBean();
	
	        	ICalUtil.setSecurityBeanForSecurityCheck(securityBean,row);        	
	 			
				try 
				{
					if(!checkForExistingSecurities(securityBean))
					{
					    csvWriter.writeNext(ICalUtil.getSecurityValueListForMisiingSecurities(securityBean));
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
        	}
		}  
        csvReader.close();
        csvWriter.close();
	}

	public boolean checkForExistingSecurities(SecurityBean sBean) throws ClassNotFoundException, SQLException
	{
		SecurityDao sDao = new SecurityDao();
		ResultSet rs = sDao.getSecutiry(sBean);
		if(rs == null || !rs.next())
			return false;
		else
			return true;
	}
	
	public String importSecurityDataFromCsv(String outFilePath) {
		List<SecurityBean> securityList = new ArrayList<>();
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
		        	SecurityBean securityBean = new SecurityBean();
		        	ICalUtil.setSecurityBeanToAddSecurity(securityBean,row);
					
		 			securityList.add(securityBean);
	        	}
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
		SecurityDao sDao = new SecurityDao();
		try {
			sDao.insertSecurity(securityList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Success import excel to mysql table");
		return "bussinessAPI";
	}
	
	public String importSecurityPriceDataFromCsv(String outFilePath) {
		List<SecurityPriceBean> securityPriceList = new ArrayList<>();
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
	        	if (!row[0].isEmpty()) 	        	   
	        	{
		        	SecurityPriceBean securityPriceBean = new SecurityPriceBean();
		        	ICalUtil.setSecurityPriceBean(securityPriceBean,row);
		        	securityPriceList.add(securityPriceBean);
	        	}
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
		SecurityDao sDao = new SecurityDao();
		try {
			sDao.insertSecurityPrice(securityPriceList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Success import excel to mysql table");
		System.out.println("Import rows " );
		return "bussinessAPI";
	}
	
	
	public String mapSecurityWithIndex(String outFilePath) throws IOException
	{	
		List<SecurityBean> securityList = new ArrayList<>();
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
	        	if (!row[0].isEmpty()) 	
	        	{
		        	SecurityBean securityBean = new SecurityBean();
		        	ICalUtil.setSecurityBeanToMapSecurity(securityBean,row);
					
		        	securityList.add(securityBean);
	        	}
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
		SecurityDao sDao = new SecurityDao();
		try {
			sDao.insertSecurityForIndex(securityList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Success import excel to mysql table");
		System.out.println("Import rows " );
		return "mapSecurityWithIndex bussinessAPI";
	}
	
	public List<SecurityBean> getAllSecurities(String filter)
	{
		SecurityDao sDao = new SecurityDao();
		List<SecurityBean> securityList = new ArrayList<>();  
		try
		{
			securityList = sDao.getAllSecurityList(filter);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return securityList;
	}
	
	public List<SecurityBean> getAllSecuritiesForIndex(String indexTicker)
	{
		System.out.println("in security service API");
		SecurityDao sDao = new SecurityDao();
		//getSecurityForIndex
		List<SecurityBean> securityList = null;
		try {
			securityList = sDao.getSecurityListForIndex(indexTicker);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return securityList;
	}
	
	public void deleteSecurityFromIndex(SecurityBean sBean) throws Exception 
	{
		SecurityDao sDao = new SecurityDao();
		sDao.deleteSecurityFromIndex(sBean);
	}
}
