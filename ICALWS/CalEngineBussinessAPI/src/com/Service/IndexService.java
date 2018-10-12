package com.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.Bean.IndexBean;
import com.DataService.ConfigUtil;
import com.DataService.IndexDao;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class IndexService {

	public void testConn()
	{
		IndexDao iDao = new IndexDao();
		try
		{
			iDao.ExecuteMSSQLQuery("SELECT TOP 5 *  FROM [FDS_DataFeeds].[fp_v1].[fp_prices_last_exch] order by date desc");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String importIndexDataFromCsv(String outFilePath,Boolean isProprietaryWeightedIndices) throws Exception
	{	
		List<IndexBean> indexList = new ArrayList<>();
		try 
		{
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
		        	IndexBean indexBean = new IndexBean();
		        	ICalUtil.setIndexBean(indexBean,row,isProprietaryWeightedIndices);
					
		        	indexList.add(indexBean);
	        	}
	        }
	        csvReader.close();
	        IndexDao iDao = new IndexDao();
			iDao.insertIndex(indexList);
			System.out.println("Success import index data into table");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error import index data into table");
			System.out.println(e.getMessage());
			throw(e);
		}
		return "importIndexDataFromCsv";
	}
	
	public String insertIndexData(Map<String,Object>  columnsValuesMap) throws Exception
	{	
		IndexDao iDao = new IndexDao();
		try 
		{
			iDao.insertData("indexdescription",columnsValuesMap);
			System.out.println("Success Add Index data to table");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Error Add Index data to table");
			System.out.println(e.getMessage());
			throw(e);
		}
		
		return "insertIndexData";
	}
	
	public List<IndexBean> getAllIndex(String filter) throws Exception
	{
		IndexDao iDao = new IndexDao();
		List<IndexBean> indexList = new ArrayList<>();  
		try
		{
			indexList = iDao.getAllIndexList(filter);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw(e);
		}
		return indexList;
	}
	
	public void deleteIndex(String indexTicker) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		iDao.deleteIndex(indexTicker);
	}
	
	public void deleteSecurityFromIndex(String indexTicker,String securityId) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		iDao.deleteSecurityFromIndex(indexTicker,securityId);
	}
	
	public void updateIndexStatus(String id,String status) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		iDao.updateIndexStatus(id,status);
	}
	
	public void updateIndicesStatus(String tickers,String status) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		iDao.updateIndicesStatus(tickers,status);
	}	
	
	public void updateIndicesStatusAsRun(String tickers,String status,String runDate) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		iDao.updateIndicesStatusAsRun(tickers,status,runDate);
	}
	
	public ResultSet getAllSecuritiesForIndex(String indexTicker)
	{
		
		String STR_CLOSE_FILE_QUERY = 
				"SELECT IC.id,S.securityid securityId, (Select top 1 CloseIndexValue FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and  indexTicker = '" +indexTicker + "') IndexValue ,"
				+ "S.BBGTicker,S.fullName,S.ISIN,S.SEDOL,S.CUSIP,S.Country,IC.weight,IC.shares,S.currency Currency,"
				+ "(SELECT top 1 closePrice  FROM " + ConfigUtil.propertiesMap.get("dbName") + ".closeprice where ISIN =S.ISIN and BBGTicker = S.BBGTicker order by vd desc) Price,"
				+ "(SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where fromCurrency = S.currency and toCurrency = "
				+ "(Select currency FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and  indexTicker = '" + indexTicker+ "') "
				+ " order by vd desc) CurrencyFactor ,"
				+ "(SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where toCurrency = S.currency and fromCurrency = "
				+ "(Select currency FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and  indexTicker = '" + indexTicker+ "') " 
				+ " order by vd desc) CurrencyDivisor "
				+ " FROM " + ConfigUtil.propertiesMap.get("dbName") + ".security S, " + ConfigUtil.propertiesMap.get("dbName") + ".indexcomposition IC "
				+ " where IC.flag='1' and S.flag ='1' and  IC.indexcode = '" + indexTicker+ "' and IC.securityId = S.securityid"
				;
		
		System.out.println(STR_CLOSE_FILE_QUERY);
		
		IndexDao iDao = new IndexDao();
		ResultSet securityList = null;
		try {
			securityList = iDao.ExecuteQuery(STR_CLOSE_FILE_QUERY);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return securityList;
	}
	
	
	public String insertIndexFileData(Map<String,Object>  columnsValuesMap,String tableName) throws IOException
	{	
		IndexDao iDao = new IndexDao();
		try {
			iDao.insertData(tableName,columnsValuesMap);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Success Save data to closingfiledetails");
		return "bussinessAPI";
	}
	
	public String insertIndexOpeningFileData(Map<String,Object>  columnsValuesMap) throws IOException
	{	
		IndexDao iDao = new IndexDao();
		try {
			iDao.insertData("openingfiledetails",columnsValuesMap);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Success Save data to openingfiledetails");
		return "bussinessAPI";
	}
	
	public Map<String,Object> getCurrencyRateMap(String toCurrency,String toDate)
	{
		Map<String,Object> currencyVsCurrencyFactorMap = new HashMap<String,Object>();
		IndexDao iDao = new IndexDao();
		currencyVsCurrencyFactorMap = iDao.getCurrencyRateMap(toCurrency,toDate);
		return currencyVsCurrencyFactorMap;
	}
		
	public String updateIndexValues(Map<String,Object> keyValueMap) throws SQLException, ClassNotFoundException
	{
		IndexDao iDao = new IndexDao();
		try {
			iDao.updateIndexValue(keyValueMap);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Success updateIndexValues");
		System.out.println("Update Index Values " );
		return "bussinessAPI";
	}
	
	public List<IndexBean> getAllLiveIndexForFileGeneration(String zoneType)
	{
		IndexDao iDao = new IndexDao();
		List<IndexBean> indexList = new ArrayList<>();  
		try
		{
			indexList = iDao.getAllLiveIndexForClosingFile(zoneType);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return indexList;
	}
	
	public String generatePreClosingile(IndexBean iBean,String fileCompletePath) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		ResultSet securityList =  getAllSecuritiesForIndex( iBean.getIndexTicker());
		
		Float indexMarketValue = iBean.getIndexMarketValue();
		Float indexValue = iBean.getCloseIndexValue();
		Float divisor = (float) 0.0 ;
		Float sumOfWeights = (float) 0.0 ;
		
		String runDate = iBean.getIndexRunDate();
		
		List<String[]> securityDataList = new ArrayList();
		
		
		String[] strDateHeaderArray = {"Date",runDate};		
		String[] emptyHeaderArray = {" "," "};
		
		CSVWriter csvWriter = ICalUtil.generateCsvTemplate(strDateHeaderArray, fileCompletePath);
		
		securityDataList.add(ICalUtil.getClosingFileHeaderList());

		if(securityList !=null)
		{
			while(securityList.next())
			{
				int id =  securityList.getInt("id");
				Float sharePrice = securityList.getFloat("Price");
				Float shares = securityList.getFloat("shares");
				Float weight = securityList.getFloat("weight");
				Float currencyFactor = (securityList.getFloat("CurrencyFactor"));
				Float currencyDivisor = (securityList.getFloat("CurrencyDivisor"));
				String cuerrency = (securityList.getString("Currency"));
				if(cuerrency.compareTo(cuerrency.toUpperCase()) != 0)
					sharePrice = sharePrice/100;
				if(currencyFactor == 0 && currencyDivisor == 0)
					currencyFactor = (float) 1;
				else
				{
					if(currencyFactor == 0 && currencyDivisor != 0)
					{
						currencyFactor = 1/currencyDivisor;
					}
				}
				
				if(shares == 0.0)
				{
					shares = (indexMarketValue*weight)/(sharePrice*currencyFactor);
					try {
						iDao.updateSecurityShares(id, shares);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				else if(weight == 0.0)
				{
					weight = (shares*sharePrice*currencyFactor)/indexMarketValue;
					try {
						iDao.updateSecurityWeight(id, weight);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}				
				sumOfWeights +=  weight;				
				
				String[] strValueArray = new String[12];
				
				strValueArray[0] = runDate;
				strValueArray[1] = securityList.getString("BBGTicker");
				strValueArray[2] = securityList.getString("fullName");
				strValueArray[3] = securityList.getString("ISIN");
				strValueArray[4] = securityList.getString("SEDOL");
				strValueArray[5] = securityList.getString("CUSIP");
				strValueArray[6] = securityList.getString("Country");			
				strValueArray[7] = shares.toString();
				strValueArray[8] = weight.toString();
				strValueArray[9] = securityList.getString("Price");			
				strValueArray[10] = securityList.getString("Currency");		
				strValueArray[11] = currencyFactor.toString();	
				
				securityDataList.add(strValueArray);
			}
		}
		divisor = indexMarketValue/indexValue;
		iBean.setIndexDivisor(divisor);
		iBean.setSumOfWeights(sumOfWeights);
		
		
		String[] indexValueHeaderArray = {"Index Value",indexValue.toString()};
		String[] divisorHeaderArray = {"Divisor",divisor.toString()};
		String[] marketValueHeaderArray = {"Market Value",indexMarketValue.toString()};
		
		//Indert CloseFile Details
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
		
		csvWriter.writeNext(indexValueHeaderArray);
		csvWriter.writeNext(divisorHeaderArray);
		csvWriter.writeNext(marketValueHeaderArray);
		csvWriter.writeNext(emptyHeaderArray);
		
		csvWriter.writeAll(securityDataList);
		csvWriter.close();
		
		Map<String,Object>  columnsValuesMap =new HashMap<String,Object>();
		
		columnsValuesMap.put("indexTicker", iBean.getIndexTicker());
		columnsValuesMap.put("fileCreationDate", strDate);
		columnsValuesMap.put("indexValue", iBean.getCloseIndexValue());
		columnsValuesMap.put("marketValue", iBean.getIndexMarketValue());
		columnsValuesMap.put("divisor", divisor);		
		columnsValuesMap.put("sumOfWeights", sumOfWeights);
		
		insertIndexFileData(columnsValuesMap,"closingfiledetails");
		
		return fileCompletePath;
	}

	public void generateOpeningFile(IndexBean iBean,String toDate,String fileCompletePath) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		Map<String,Object> lastClosingFileDetailsMap = iDao.getIndexFileDetails(iBean.getIndexTicker(),"closingfiledetails");
		
		ResultSet securityList =  getAllSecuritiesForIndex( iBean.getIndexTicker());
		List<String[]> securityDataList = new ArrayList();
		
		Float divisor = (float) 0.0 ;
		Float marketValue = (float) 0.0 ;
		Float indexValue = (float) 0.0 ;
		Float sumOfWeights = (float) 0.0 ;
		
		indexValue = (Float) lastClosingFileDetailsMap.get("indexValue");
		securityDataList.add(ICalUtil.getClosingFileHeaderList());
		
		if(securityList !=null)
		{
			while(securityList.next())
			{
				Float shares = (float) 0.0 ;
				Float securityPrice = (float) 0.0 ;
				Float weights = (float) 1 ;
				
				shares = (securityList.getFloat("shares"));
				securityPrice =(securityList.getFloat("Price"));
				Float currencyFactor = (securityList.getFloat("CurrencyFactor"));
				Float currencyDivisor = (securityList.getFloat("CurrencyDivisor"));
				String cuerrency = (securityList.getString("Currency"));
				if(cuerrency.compareTo(cuerrency.toUpperCase()) != 0)
					securityPrice = securityPrice/100;
				if(currencyFactor == 0 && currencyDivisor == 0)
					currencyFactor = (float) 1;
				else
				{
					if(currencyFactor == 0 && currencyDivisor != 0)
					{
						currencyFactor = 1/currencyDivisor;
					}
				}
				
				weights = (securityList.getFloat("weight"));
				
				adjustCorporateActions(shares,weights,securityPrice);
				
				sumOfWeights +=  weights;
				marketValue += shares*securityPrice*currencyFactor;
				
				String[] strValueArray = new String[12];
				
				strValueArray[0] = toDate;
				strValueArray[1] = securityList.getString("BBGTicker");
				strValueArray[2] = securityList.getString("fullName");
				strValueArray[3] = securityList.getString("ISIN");
				strValueArray[4] = securityList.getString("SEDOL");
				strValueArray[5] = securityList.getString("CUSIP");
				strValueArray[6] = securityList.getString("Country");			
				strValueArray[7] = securityList.getString("shares");
				strValueArray[8] = securityList.getString("weight");
				strValueArray[9] = securityList.getString("Price");			
				strValueArray[10] = securityList.getString("Currency");		
				strValueArray[11] = currencyFactor.toString();//securityList.getString("CurrencyFactor");		
				
				securityDataList.add(strValueArray);
			}
		}
		
		divisor = marketValue/indexValue;
		
		String[] strDateHeaderArray = {"Date",toDate};	
		CSVWriter csvWriter = ICalUtil.generateCsvTemplate(strDateHeaderArray, fileCompletePath);
		
		String[] indexValueHeaderArray = {"Index Value",indexValue.toString()};
		String[] divisorHeaderArray = {"Divisor",divisor.toString()};
		String[] marketValueHeaderArray = {"Market Value",marketValue.toString()};		
		String[] emptyHeaderArray = {" "," "};
		
		csvWriter.writeNext(indexValueHeaderArray);
		csvWriter.writeNext(divisorHeaderArray);
		csvWriter.writeNext(marketValueHeaderArray);
		csvWriter.writeNext(emptyHeaderArray);		
		csvWriter.writeAll(securityDataList);
		csvWriter.close();
		
		Map<String,Object>  columnsValuesMap =new HashMap<String,Object>();
		columnsValuesMap.put("indexTicker",  iBean.getIndexTicker());
		columnsValuesMap.put("indexValue", indexValue);
		columnsValuesMap.put("marketValue", marketValue);
		columnsValuesMap.put("divisor", divisor);
		columnsValuesMap.put("fileCreationDate", toDate);
		columnsValuesMap.put("sumOfWeights", sumOfWeights);
		insertIndexFileData(columnsValuesMap,"openingfiledetails");
	}
	
	private void adjustCorporateActions(Float shares, Float weights, Float securityPrice) {
		
	}

	public void generateClosingFile(IndexBean iBean,String toDate,String fileCompletePath) throws Exception 
	{
		IndexDao iDao = new IndexDao();
		Map<String,Object> lastOpeningFileDetailsMap = iDao.getIndexFileDetails(iBean.getIndexTicker(),"openingfiledetails");
		if(lastOpeningFileDetailsMap.get("divisor") == null)
			return;
		ResultSet securityList =  getAllSecuritiesForIndex( iBean.getIndexTicker());
		List<String[]> securityDataList = new ArrayList();
		
		Float divisor = (float) 0.0 ;
		Float marketValue = (float) 0.0 ;
		Float indexValue = (float) 0.0 ;
		Float sumOfWeights = (float) 0.0 ;
		
		divisor = (Float) lastOpeningFileDetailsMap.get("divisor");
		securityDataList.add(ICalUtil.getClosingFileHeaderList());
		
		if(securityList !=null)
		{
			while(securityList.next())
			{
				Float shares = (float) 0.0 ;
				Float securityPrice = (float) 0.0 ;
				Float weights = (float) 1 ;
				String cuerrency="";
				
				shares = (securityList.getFloat("shares"));
				securityPrice =(securityList.getFloat("Price"));
				weights = (securityList.getFloat("weight"));
				cuerrency = (securityList.getString("Currency"));
				if(cuerrency.compareTo(cuerrency.toUpperCase()) != 0)
					securityPrice = securityPrice/100;
				Float currencyFactor = (securityList.getFloat("CurrencyFactor"));
				Float currencyDivisor = (securityList.getFloat("CurrencyDivisor"));
				
				if(currencyFactor == 0 && currencyDivisor == 0)
					currencyFactor = (float) 1;
				else
				{
					if(currencyFactor == 0 && currencyDivisor != 0)
					{
						currencyFactor = 1/currencyDivisor;
					}
				}
				
				sumOfWeights +=  weights;
				marketValue += shares*securityPrice*currencyFactor;
				
				String[] strValueArray = new String[12];
				
				strValueArray[0] = toDate;
				strValueArray[1] = securityList.getString("BBGTicker");
				strValueArray[2] = securityList.getString("fullName");
				strValueArray[3] = securityList.getString("ISIN");
				strValueArray[4] = securityList.getString("SEDOL");
				strValueArray[5] = securityList.getString("CUSIP");
				strValueArray[6] = securityList.getString("Country");			
				strValueArray[7] = securityList.getString("shares");
				strValueArray[8] = securityList.getString("weight");
				strValueArray[9] = securityList.getString("Price");			
				strValueArray[10] = securityList.getString("Currency");		
				strValueArray[11] = currencyFactor.toString();	
				
				securityDataList.add(strValueArray);
			}
		}
		
		indexValue = marketValue/divisor;
		
		String[] strDateHeaderArray = {"Date",toDate};	
		CSVWriter csvWriter = ICalUtil.generateCsvTemplate(strDateHeaderArray, fileCompletePath);
		
		String[] indexValueHeaderArray = {"Index Value",indexValue.toString()};
		String[] divisorHeaderArray = {"Divisor",divisor.toString()};
		String[] marketValueHeaderArray = {"Market Value",marketValue.toString()};		
		String[] emptyHeaderArray = {" "," "};
		
		csvWriter.writeNext(indexValueHeaderArray);
		csvWriter.writeNext(divisorHeaderArray);
		csvWriter.writeNext(marketValueHeaderArray);
		csvWriter.writeNext(emptyHeaderArray);		
		csvWriter.writeAll(securityDataList);
		csvWriter.close();
		
		Map<String,Object>  columnsValuesMap =new HashMap<String,Object>();
		columnsValuesMap.put("indexTicker",  iBean.getIndexTicker());
		columnsValuesMap.put("indexValue", indexValue);
		columnsValuesMap.put("marketValue", marketValue);
		columnsValuesMap.put("divisor", divisor);
		columnsValuesMap.put("fileCreationDate", toDate);
		columnsValuesMap.put("sumOfWeights", sumOfWeights);
		insertIndexFileData(columnsValuesMap,"closingfiledetails");
	}
	
	public void generateCompositePreClosingFile(List<IndexBean> indexBeanList,String filePath) throws IOException
	{
		String[] strDateHeaderArray = {"Index Code","Market Value","Index value","Divisor","Sum Of Weights"};	
		CSVWriter csvWriter = ICalUtil.generateCsvTemplate(strDateHeaderArray, filePath);
		for(int i=0;i < indexBeanList.size();i++)
		{
			IndexBean iBean = indexBeanList.get(i);
			String[] indexValueArray = {iBean.getIndexTicker(),iBean.getIndexMarketValue().toString(),iBean.getCloseIndexValue().toString(),iBean.getIndexDivisor().toString(),iBean.getSumOfWeights().toString()};
			csvWriter.writeNext(indexValueArray);
		}
		csvWriter.close();
	}
	public void goLiveIndex() throws ClassNotFoundException, SQLException
	{
		IndexDao dao = new IndexDao();
		dao.goLiveIndex();
	}
}
