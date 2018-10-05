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
//	private String GET_SECURITY_ID = "SELECT top 1 securityid  FROM ical2.security order by securityid desc limit 1";
	private String GET_SECURITY_ID = "SELECT top 1 securityid  FROM ical2.security order by securityid desc";
	
	public int getNextSecurityId() throws ClassNotFoundException, SQLException
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
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
      
		for (SecurityBean security : securityList)
		{
			int securityId = getNextSecurityId();
			Map<String,Object> keyValueMap = new HashMap<String,Object>();
			keyValueMap.put("securityid", securityId);
			
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
			keyValueMap.put("vt","9999-12-31");
			
			insertData(SECURITY_TABLE_NAME,keyValueMap);
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
	
	public ResultSet getSecutiry(SecurityBean sBean) throws ClassNotFoundException, SQLException
	{		
		String strWhereClause = "Where  ISIN = '" +sBean.getISIN().trim() + "' and BBGTicker = '" +sBean.getBBGTicker().trim() + "' "
				+ " AND CAST(GETDATE() AS DATE) BETWEEN vf AND vt and flag = 1";
		String strSelectQuery = DataUtill.createSelect(SECURITY_TABLE_NAME, strWhereClause);
		ResultSet rs = ExecuteQuery(strSelectQuery);
		return rs;		
	}
	
	public ResultSet getSecutiry(String ISIN ,String bbgTicker) throws ClassNotFoundException, SQLException
	{		
		String strWhereClause = "Where  ISIN = '" +ISIN.trim() + "' and BBGTicker = '" +bbgTicker.trim() + "' "
				+ " AND CAST(GETDATE() AS DATE) BETWEEN vf AND vt";
		String strSelectQuery = DataUtill.createSelect(SECURITY_TABLE_NAME, strWhereClause);
		ResultSet rs = ExecuteQuery(strSelectQuery);
		return rs;		
	}
	
	public void deleteSecurityFromIndex(SecurityBean sBean) throws Exception 
	{
		String securityId = getSecurityId(sBean).toString();
		String filter = "Where indexCode ='"+ sBean.getIndexCode() + "' AND securityId='" + securityId + "'" ;
		deleteData(INDEX_SECURITY_TABLE_NAME,filter);
	}
	
	public Integer getSecurityId(String ISIN ,String bbgTicker) throws ClassNotFoundException, SQLException
	{
		Integer id =0;
		SecurityDao sDao = new SecurityDao();
		ResultSet rs = sDao.getSecutiry(ISIN ,bbgTicker);
		while (rs.next())
		{
            id = rs.getInt(2);
		}
		return  id;
	}
	
	public Integer getSecurityId(SecurityBean sBean) throws ClassNotFoundException, SQLException
	{
		Integer id =0;
		SecurityDao sDao = new SecurityDao();
		ResultSet rs = sDao.getSecutiry(sBean);
		while (rs.next())
		{
            id = rs.getInt(2);
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
	
	public List<SecurityBean> getSecurityListForIndex(String indexTicker) throws SQLException, ClassNotFoundException
	{
		System.out.println("in security dao");
		List<SecurityBean> securityList = new ArrayList<>();
		
		ResultSet rs = getSecurityForIndex(indexTicker);
		securityList = converListToSecurityBean(rs);
		return securityList;
	}
	
	public ResultSet getSecurityForIndex(String indexTicker)
	{
		System.out.println("print query");
		String STR_CLOSE_FILE_QUERY = 
				"SELECT IC.indexcode,S.securityid securityId, (Select  top 1 CloseIndexValue FROM ical2.indexdescription where indexTicker = '" +indexTicker + "') IndexValue ,"
				+ "S.BBGTicker,S.fullName,S.ISIN,S.SEDOL,S.CUSIP,S.Country,IC.weight,IC.shares,S.currency Currency,"
				+ "(SELECT top 1 closePrice  FROM ical2.closeprice where ISIN =S.ISIN and BBGTicker = S.BBGTicker order by vd desc ) Price,"
				+ "(SELECT top 1 rate FROM ical2.currencyrate where fromCurrency = S.currency and toCurrency = "
				+ "(Select currency FROM ical2.indexdescription where indexTicker = '" + indexTicker+ "') "
				+ " order by vd desc) CurrencyFactor ,"
				+ "(SELECT top 1 rate FROM ical2.currencyrate where toCurrency = S.currency and fromCurrency = "
				+ "(Select currency FROM ical2.indexdescription where indexTicker = '" + indexTicker+ "') " 
				+ " order by vd desc ) CurrencyDivisor "
				+ " FROM ical2.security S, ical2.indexcomposition IC "
				+ " where IC.flag='1' and IC.indexcode = '" + indexTicker+ "' and IC.securityId = S.securityid"
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
}
