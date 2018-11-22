package com.DataService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.Bean.SecurityBean;
import com.Bean.SecurityPriceBean;

public class SecurityDao extends DefaultDao
{
	private String SECURITY_TABLE_NAME = "security";
	private String SECURITY_PRICE_TABLE_NAME = "closeprice";
	private String INDEX_SECURITY_TABLE_NAME = "indexcomposition";
	private String GET_SECURITY_ID = "SELECT top 1 id  FROM " + ConfigUtil.propertiesMap.get("dbName") + ".security where flag ='1' order by id desc";
	
	public int getNextSecurityId() throws Exception
	{
		int nextId = 0;
		ResultSet rs = ExecuteQuery(GET_SECURITY_ID);
		while(rs.next())
		{
			nextId =  rs.getInt(1);			
		}
		return nextId + 1;
		
	}
	public void insertSecurity(List<SecurityBean> securityList) throws Exception  
	{
		try
		{
			LocalDate localDate = LocalDate.now();
//			String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
			String strDate = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
	      
			for (SecurityBean security : securityList)
			{
				int securityId = getNextSecurityId();
				Map<String,Object> keyValueMap = new HashMap<String,Object>();
//				keyValueMap.put("securityid", securityId);
				keyValueMap.put("id", securityId);
				
				keyValueMap.put("ISIN", security.getISIN());
				keyValueMap.put("BBGTicker", security.getBBGTicker());
				keyValueMap.put("fullName", security.getFullName());
				keyValueMap.put("currency", security.getCurrency());
				keyValueMap.put("SEDOL", security.getSEDOL());
				
				keyValueMap.put("CUSIP", security.getCUSIP());
				keyValueMap.put("Country", security.getCountry());
				keyValueMap.put("Sector", security.getSector());
				keyValueMap.put("Industry", security.getIndustry());
				keyValueMap.put("Subindustry", security.getSubIndustry());
				
				keyValueMap.put("flag","1");
				keyValueMap.put("vd", strDate);
				keyValueMap.put("vf", strDate);
				keyValueMap.put("vt","99991231");
				
				insertData(SECURITY_TABLE_NAME,keyValueMap);
			}
		}
		catch (Exception e)
		{
			throw new Exception("Please enter valid data." + e.toString());
	    }
	}
	
	public void insertSecurityPrice(List<SecurityPriceBean> securityPriceList) throws Exception  
	{
		for (SecurityPriceBean securityPrice : securityPriceList)
		{
			Map<String,Object> keyValueMap = new HashMap<String,Object>();
			keyValueMap.put("ISIN", securityPrice.getISIN());
			keyValueMap.put("BBGTicker", securityPrice.getBBGTicker());
			keyValueMap.put("closePrice", securityPrice.getCurrencyPrice());
			keyValueMap.put("crrySymbol", securityPrice.getCurrencySymbol());
			keyValueMap.put("vd", securityPrice.getVd());
			
			insertData(SECURITY_PRICE_TABLE_NAME,keyValueMap);
		}
	}
	
	public ResultSet getSecutiry(SecurityBean sBean) throws Exception
	{		
		return getSecutiry(sBean.getISIN(),sBean.getBBGTicker(),sBean.getCurrency());
//		String strWhereClause = getSecurityExistQuerty(sBean.getISIN(),sBean.getBBGTicker(),sBean.getCurrency());
//		String strSelectQuery = DataUtill.createSelect(SECURITY_TABLE_NAME, strWhereClause);
//		ResultSet rs = ExecuteQuery(strSelectQuery);
//		return rs;		
	}
	
	public ResultSet getSecutiry(String ISIN ,String bbgTicker,String currency) throws Exception
	{		
		String strWhereClause = getSecurityExistQuerty( ISIN , bbgTicker,currency);
		String strSelectQuery = DataUtill.createSelect(SECURITY_TABLE_NAME, strWhereClause);
		System.out.println(strSelectQuery);
		ResultSet rs = ExecuteQuery(strSelectQuery);
		return rs;		
	}
	
	private String getSecurityExistQuerty(String ISIN ,String bbgTicker,String currency)
	{
		String strWhereClause = "Where  ISIN = '" +ISIN.trim() + "' and BBGTicker = '" +bbgTicker.trim() + "' and currency = '" +currency.trim()
		+ "' and flag = '1' AND CONVERT(char(10), GetDate(),112) BETWEEN vf AND vt";
//		+ "' AND CAST(GETDATE() AS DATE) BETWEEN vf AND vt";
		// CONVERT(char(10), GetDate(),112)
		return strWhereClause;
		
	}
	
	public void deleteSecurityFromIndex(SecurityBean sBean) throws Exception 
	{
		String securityId = getSecurityId(sBean).toString();
		String filter = "Where indexCode ='"+ sBean.getIndexCode() + "' AND id='" + securityId + "'" ;
		deleteData(INDEX_SECURITY_TABLE_NAME,filter);
	}
	
	public Integer getSecurityId(String ISIN ,String bbgTicker,String currency) throws Exception
	{
		Integer id =0;
		SecurityDao sDao = new SecurityDao();
		ResultSet rs = sDao.getSecutiry(ISIN ,bbgTicker,currency);
		while (rs.next())
		{
            id = rs.getInt(1);
		}
		return  id;
	}
	
	public Integer getSecurityId(SecurityBean sBean) throws Exception
	{
		Integer id =0;
		SecurityDao sDao = new SecurityDao();
		ResultSet rs = sDao.getSecutiry(sBean);
		while (rs.next())
		{
            id = rs.getInt(1);
		}
		return  id;
	}
	
	public void insertSecurityForIndex(List<SecurityBean> securityList) throws Exception 
	{
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
		
		for (SecurityBean security : securityList)
		{
			Integer id = getSecurityId(security);
			Map<String,Object> keyValueMap = new HashMap<String,Object>();
			
			keyValueMap.put("indexCode", security.getIndexCode());
			keyValueMap.put("securityId", id);
			keyValueMap.put("weight", security.getWeight());
			keyValueMap.put("shares", security.getShares());
			
			keyValueMap.put("vf", strDate);
			keyValueMap.put("vt","9999-12-31");
			keyValueMap.put("flag","1");
			insertData(INDEX_SECURITY_TABLE_NAME,keyValueMap);
		}
	}
	
	public List<SecurityBean> getAllSecurityList(String filter) throws SQLException, ClassNotFoundException
	{
		List<SecurityBean> securityList = new ArrayList<>();
		
		ResultSet rs = getAllData(SECURITY_TABLE_NAME,filter);
		securityList = converResultToSecurityBean(rs);
		return securityList;
	}	
	
	public List<SecurityBean> converResultToSecurityBean(ResultSet rs) throws SQLException, ClassNotFoundException
	{
		List<SecurityBean> securityList = new ArrayList<>();
		while (rs.next())
		{
			SecurityBean sBean = new SecurityBean(); 
			getBeanFromResultSet(rs,sBean);
			securityList.add(sBean);
	    }		
		return securityList;
	}
	
	public List<SecurityBean> converListToSecurityBean(ResultSet rs) throws SQLException, ClassNotFoundException
	{
		List<SecurityBean> securityList = new ArrayList<>();
		while (rs.next())
		{
			SecurityBean sBean = new SecurityBean(); 
			getSecurityBeanFromResultSet(rs,sBean);
			securityList.add(sBean);
	    }		
		return securityList;
	}
	
	private void getBeanFromResultSet(ResultSet rs,SecurityBean sBean) throws SQLException
	{
		if(rs.getString("ISIN") != null)
			sBean.setISIN(rs.getString("ISIN"));	
		if(rs.getString("BBGTicker") != null)
			sBean.setBBGTicker(rs.getString("BBGTicker"));
		if(rs.getString("fullName") != null)
			sBean.setFullName(rs.getString("fullName"));
		if(rs.getString("SEDOL") != null)
			sBean.setSEDOL(rs.getString("SEDOL"));
		if(rs.getString("CUSIP") != null)
			sBean.setCUSIP(rs.getString("CUSIP"));
		if(rs.getString("Country") != null)
			sBean.setCountry(rs.getString("Country"));
		if(rs.getString("Sector") != null)
			sBean.setSector(rs.getString("Sector"));
		if(rs.getString("Industry") != null)
			sBean.setIndustry(rs.getString("Industry"));
		if(rs.getString("Subindustry") != null)
			sBean.setSubIndustry(rs.getString("Subindustry"));
		if(rs.getString("Subindustry") != null)
			sBean.setSubIndustry(rs.getString("Subindustry"));
		
	}
	
	private void getSecurityBeanFromResultSet(ResultSet rs,SecurityBean sBean) throws SQLException
	{
		if(rs.getString("ISIN") != null)
			sBean.setISIN(rs.getString("ISIN"));	
		if(rs.getString("BBGTicker") != null)
			sBean.setBBGTicker(rs.getString("BBGTicker"));
		if(rs.getString("fullName") != null)
			sBean.setFullName(rs.getString("fullName"));
		if(rs.getString("SEDOL") != null)
			sBean.setSEDOL(rs.getString("SEDOL"));
		if(rs.getString("CUSIP") != null)
			sBean.setCUSIP(rs.getString("CUSIP"));
		if(rs.getString("Country") != null)
			sBean.setCountry(rs.getString("Country"));
		
		if(rs.getString("indexcode") != null)
			sBean.setIndexCode(rs.getString("indexcode"));
		sBean.setWeight(rs.getFloat("weight"));
		sBean.setShares(rs.getFloat("shares"));
		sBean.setPrice(rs.getFloat("Price"));

		Float currencyFactor = rs.getFloat("CurrencyFactor");
		Float currencyDivisor = rs.getFloat("CurrencyDivisor");
		if(currencyFactor == 0 && currencyDivisor == 0)
			sBean.setCurrencyFactor(1);
		else
		{
			if(currencyFactor == 0 && currencyDivisor != 0)
			{
				currencyFactor = 1/currencyDivisor;
			}
			sBean.setCurrencyFactor(currencyFactor);
		}
			
		sBean.setCurrency(rs.getString("Currency"));
	}
	
	public List<SecurityBean> getSecurityListForIndex(String indexTicker) throws Exception
	{
		System.out.println("in security dao");
		List<SecurityBean> securityList = new ArrayList<>();
		
		ResultSet rs = getSecurityForIndex(indexTicker);
		securityList = converListToSecurityBean(rs);
		return securityList;
	}
	
	public ResultSet getSecurityForIndex(String indexTicker) throws Exception
	{
		System.out.println("print query");
//		String STR_CLOSE_FILE_QUERY = 
//				"SELECT IC.indexcode,S.securityid securityId, (Select  top 1 CloseIndexValue FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and indexTicker = '" +indexTicker + "') IndexValue ,"
//				+ "S.BBGTicker,S.fullName,S.ISIN,S.SEDOL,S.CUSIP,S.Country,IC.weight,IC.shares,S.currency Currency,"
//				+ "(SELECT top 1 closePrice  FROM " + ConfigUtil.propertiesMap.get("dbName") + ".closeprice where ISIN =S.ISIN and BBGTicker = S.BBGTicker order by vd desc ) Price,"
//				+ "(SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where fromCurrency = S.currency and toCurrency = "
//				+ "(Select currency FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and indexTicker = '" + indexTicker+ "') "
//				+ " order by vd desc) CurrencyFactor ,"
//				+ "(SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where toCurrency = S.currency and fromCurrency = "
//				+ "(Select currency FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and  indexTicker = '" + indexTicker+ "') " 
//				+ " order by vd desc ) CurrencyDivisor "
//				+ " FROM " + ConfigUtil.propertiesMap.get("dbName") + ".security S, " + ConfigUtil.propertiesMap.get("dbName") + ".indexcomposition IC "
//				+ " where IC.flag='1' and IC.indexcode = '" + indexTicker+ "' and IC.securityId = S.securityid"
//				;
		
		String STR_CLOSE_FILE_QUERY = 
				"SELECT IC.indexcode,S.id securityId, (Select  top 1 CloseIndexValue FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and indexTicker = '" +indexTicker + "') IndexValue ,"
				+ "S.BBGTicker,S.fullName,S.ISIN,S.SEDOL,S.CUSIP,S.Country,IC.weight,IC.shares,S.currency Currency,"
				+ "(SELECT top 1 closePrice  FROM " + ConfigUtil.propertiesMap.get("dbName") + ".closeprice where ISIN =S.ISIN and BBGTicker = S.BBGTicker order by vd desc ) Price,"
				+ "(SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where fromCurrency = S.currency and toCurrency = "
				+ "(Select currency FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and indexTicker = '" + indexTicker+ "') "
				+ " order by vd desc) CurrencyFactor ,"
				+ "(SELECT top 1 rate FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where toCurrency = S.currency and fromCurrency = "
				+ "(Select currency FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where flag ='1' and  indexTicker = '" + indexTicker+ "') " 
				+ " order by vd desc ) CurrencyDivisor "
				+ " FROM " + ConfigUtil.propertiesMap.get("dbName") + ".security S, " + ConfigUtil.propertiesMap.get("dbName") + ".indexcomposition IC "
				+ " where IC.flag='1' and S.flag='1' and IC.indexcode = '" + indexTicker+ "' and IC.securityId = S.id"
				;
		
		System.out.println(STR_CLOSE_FILE_QUERY);
//		String strFilter = "where id in(Select securityId from ical2.indexcomposition where indexcode = '" + indexTicker +"')" ;
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
	
	public void deleteSecurity(String securityId) throws Exception 
	{
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate.minusDays(1));
		strDate = strDate.replaceAll("-", "");
		String UPDATE_SECURITY_QUERT = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + "." + SECURITY_TABLE_NAME + " SET flag = 0, vt = '" 
										+ strDate + "'  Where id = '" + securityId + "'";
		
		System.out.println(UPDATE_SECURITY_QUERT);
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(UPDATE_SECURITY_QUERT);
	}
	
	public void insertNewSecurityForIdChange(SecurityBean newSecurityBean) throws Exception  
	{
		try
		{
			LocalDate localDate = LocalDate.now();
			String strDate = DateTimeFormatter.ofPattern("yyyMMdd").format(localDate);
			SecurityBean oldSecurityBean = getAllSecurityList("Where id = " + newSecurityBean.getId() ).get(0);
			Map<String,Object> keyValueMap = new HashMap<String,Object>();
			
			keyValueMap.put("id", oldSecurityBean.getId());
			
			if(newSecurityBean.getISIN() != null && !newSecurityBean.getISIN().trim().equalsIgnoreCase(""))
				keyValueMap.put("ISIN", newSecurityBean.getISIN());
			else
				keyValueMap.put("ISIN", oldSecurityBean.getISIN());
			
			if(newSecurityBean.getBBGTicker() != null && !newSecurityBean.getBBGTicker().trim().equalsIgnoreCase(""))
				keyValueMap.put("BBGTicker", newSecurityBean.getBBGTicker());
			else
				keyValueMap.put("BBGTicker", oldSecurityBean.getBBGTicker());
			
			if(newSecurityBean.getFullName() != null && !newSecurityBean.getFullName().trim().equalsIgnoreCase(""))
				keyValueMap.put("fullName", newSecurityBean.getFullName());
			else
				keyValueMap.put("fullName", oldSecurityBean.getFullName());
			
			keyValueMap.put("currency", oldSecurityBean.getCurrency());
			
			if(oldSecurityBean.getSEDOL() != null && !oldSecurityBean.getSEDOL().trim().equalsIgnoreCase(""))
				keyValueMap.put("SEDOL", oldSecurityBean.getSEDOL());
			if(oldSecurityBean.getCUSIP() != null && !oldSecurityBean.getCUSIP().trim().equalsIgnoreCase(""))
				keyValueMap.put("CUSIP", oldSecurityBean.getCUSIP());
			if(oldSecurityBean.getCountry() != null && !oldSecurityBean.getCountry().trim().equalsIgnoreCase(""))
				keyValueMap.put("Country", oldSecurityBean.getCountry());
			if(oldSecurityBean.getSector() != null && !oldSecurityBean.getSector().trim().equalsIgnoreCase(""))
				keyValueMap.put("Sector", oldSecurityBean.getSector());
			if(oldSecurityBean.getIndustry() != null && !oldSecurityBean.getIndustry().trim().equalsIgnoreCase(""))
				keyValueMap.put("Industry", oldSecurityBean.getIndustry());
			if(oldSecurityBean.getSubIndustry() != null && !oldSecurityBean.getSubIndustry().trim().equalsIgnoreCase(""))
				keyValueMap.put("Subindustry", oldSecurityBean.getSubIndustry());
			
			keyValueMap.put("flag","1");
			keyValueMap.put("vd", strDate);
			keyValueMap.put("vf", strDate);
			keyValueMap.put("vt","99991231");
			
			insertData(SECURITY_TABLE_NAME,keyValueMap);
		}
		catch (Exception e)
		{
			throw new Exception("Please enter valid data." + e.toString());
	    }
	}
	
	public ResultSet getSecutiryPrice(String ISIN ,String bbgTicker,String currency,String date) throws Exception
	{		
		String strWhereClause = getSecurityPriceExistQuerty( ISIN , bbgTicker,currency,date);
		String strSelectQuery = DataUtill.createSelect(SECURITY_PRICE_TABLE_NAME, strWhereClause);
		System.out.println(strSelectQuery);
		ResultSet rs = ExecuteQuery(strSelectQuery);
		return rs;		
	}
	
	private String getSecurityPriceExistQuerty(String ISIN ,String bbgTicker,String currency,String date)
	{
		String strWhereClause = "Where  ISIN = '" +ISIN.trim() + "' and BBGTicker = '" +bbgTicker.trim() + "' and crrysymbol = '" +currency.trim()
		+ "' and vd = '" +date.trim() + "'";
//		+ "' AND CAST(GETDATE() AS DATE) BETWEEN vf AND vt";
		// CONVERT(char(10), GetDate(),112)
		return strWhereClause;
		
	}
	
	
}
