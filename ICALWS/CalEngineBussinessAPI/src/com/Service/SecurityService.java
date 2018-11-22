package com.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Bean.SecurityBean;
import com.Bean.SecurityPriceBean;
import com.DataService.SecurityDao;
import com.Validations.CommonValidations;
import com.Validations.SecurityValidations;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class SecurityService 
{
	public boolean parseSecurityFile(String inputFilePath,String outFilePath) throws IOException,Exception
	{
		try
		{
			parseCsvFile(inputFilePath, outFilePath);
			return true;
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean importSecurityDataFromCsv(String filePath) throws IOException,Exception
	{
		try
		{
			addSerurities(filePath);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	 public void parseCsvFile(String inputFilePath,String outFilePath) throws IOException,Exception
		{	
	    	FileReader filereader = new FileReader(inputFilePath);
	    	CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	    	List<String[]> allData = csvReader.readAll();
	    	
	    	//Add validations to fix issues raised by testing team
	    	SecurityValidations.validateSecurityCheckFile(inputFilePath,allData);
	        
	        // create csvReader object and skip first Line
	        CSVWriter csvWriter = ICalUtil.generateCsvTemplate(ICalUtil.getSecurityHeaderListForMissingSecurities(), outFilePath);
	      
	        for (String[] row : allData) 
	        {
	        	if (!row[2].isEmpty()) 	
	        	{
		        	SecurityBean securityBean = new SecurityBean();
		
		        	ICalUtil.setSecurityBeanForSecurityCheck(securityBean,row);        	
		 			
					try 
					{
						if(!CommonValidations.checkForExistingSecurities(securityBean))
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
	 
	public void addSerurities(String filePath) throws Exception 
	{
		try 
		{
			List<SecurityBean> securityList = new ArrayList<>();
			SecurityDao sDao = new SecurityDao();
			
	        FileReader filereader = new FileReader(filePath);
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	        List<String[]> allData = csvReader.readAll();
	        //Add validations to fix issues raised by testing team
	        SecurityValidations.validateSecurityAddFile(filePath,allData);
	       
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
	        sDao.insertSecurity(securityList);
		}
	    catch (Exception e)
		{
	        throw e;
	    }
	}
	
	public void importSecurityPriceDataFromCsv(String filePath) throws Exception 
	{
		List<SecurityPriceBean> securityPriceList = new ArrayList<>();
		SecurityDao sDao = new SecurityDao();
		try 
		{
	        // Create an object of file reader
	        // class with CSV file as a parameter.
	        FileReader filereader = new FileReader(filePath);
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	        List<String[]> allData = csvReader.readAll();
	 
	        //Add validations to fix issues raised by testing team
	        SecurityValidations.validateSecurityPriceFile(filePath,allData);
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
	        sDao.insertSecurityPrice(securityPriceList);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	public String mapSecurityWithIndex(String outFilePath) throws Exception
	{	
		List<SecurityBean> securityList = new ArrayList<>();
		SecurityDao sDao = new SecurityDao();
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
	        sDao.insertSecurityForIndex(securityList);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	        throw e;
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
	
	public List<SecurityBean> getAllSecuritiesForIndex(String indexTicker) throws Exception
	{
		System.out.println("in security service API");
		SecurityDao sDao = new SecurityDao();
		//getSecurityForIndex
		List<SecurityBean> securityList = null;
		try {
			securityList = sDao.getSecurityListForIndex(indexTicker);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return securityList;
	}
	
	public void deleteSecurityFromIndex(SecurityBean sBean) throws Exception 
	{
		SecurityDao sDao = new SecurityDao();
		sDao.deleteSecurityFromIndex(sBean);
	}
	
	public void insertNewSecurityForIdChange(SecurityBean newSecurityBean) throws Exception  
	{
		SecurityDao sDao = new SecurityDao();
		sDao.insertNewSecurityForIdChange(newSecurityBean);;
	}
	
	public void deleteSecurity(String securityId) throws Exception 
	{
		SecurityDao sDao = new SecurityDao();
		sDao.deleteSecurity(securityId);
	}	
}
