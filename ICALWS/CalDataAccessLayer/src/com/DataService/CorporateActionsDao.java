package com.DataService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.Bean.CorporateActionsFinal;
import com.CalCommon.ICalCommonUtill;

public class CorporateActionsDao extends DefaultDao
{
	
	public ResultSet getAllCAByDateAndCode(String strCode ,String fromDate,String toDate,String source) throws Exception
	{
		//44
		readConfig();
//		String GET_ALL_DIVIDEND_CA = "SELECT TOP 10 *  FROM " + ConfigUtil.propertiesMap.get("dbName") + ".corporateactionedi "
//				+ "where eventCode in(" + strCode + ") AND  effectiveDate between '"+ fromDate + "' and '"+ toDate +  "'  order by date desc ";
//		
		String GET_ALL_DIVIDEND_CA = "SELECT * ,(select fullName  FROM " + ConfigUtil.propertiesMap.get("dbName") + ".[security] S where S.ISIN = CA.ISIN ) Name"
				+ " FROM " + ConfigUtil.propertiesMap.get("dbName") + ".corporateactionfinal CA"
				+ "where source='" +source + "' and  eventCode in('" + strCode + "') AND  effectivedate between '"+ fromDate + "' and '"+ toDate +  "'  order by effectivedate desc ";
		
		System.out.println(GET_ALL_DIVIDEND_CA);
		ResultSet rs = ExecuteQuery(GET_ALL_DIVIDEND_CA);
		return rs;
	}
	
	public ResultSet getAllCAByDateAndSecurity(String securityId ,String fromDate,String source) throws Exception
	{
		String GET_ALL_CA_FOR_SECURITY = "SELECT *  FROM " + ConfigUtil.propertiesMap.get("dbName") + ".corporateactionfinal "
				+ "where source='" + source + "' and flag = '1' and securityid ='" + securityId + "' AND  effectivedate = '"+ fromDate +  "'";
		
		System.out.println(GET_ALL_CA_FOR_SECURITY);
		ResultSet rs = ExecuteQuery(GET_ALL_CA_FOR_SECURITY);
		return rs;
	}

	public void updateCorporateActions(CorporateActionsFinal caBean) throws ClassNotFoundException, SQLException
	{
		String strUpdateCAQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".corporateactionfinal SET "
//				+ "grossAmount = " + caBean.getGrossAmount() + ", netAmount = "  + caBean.getNetAmount() + ", currency = '" + caBean.getCurrency() + "'"
				+ "grossAmount = " + caBean.getGrossAmount() + ", currency = '" + caBean.getCurrency() + "'"
				+ " Where id ='"+ caBean.getId() + "'";
		System.out.println(strUpdateCAQuery);
		updatetData(strUpdateCAQuery);
	}
	
	public void updateCorporateAction(Map<String,Object> keyValueMap) throws Exception
	{
		try
		{
//			LocalDate localDate = LocalDate.now();
//			String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
//	      
//			Map<String,Object> keyValueMap = new HashMap<String,Object>();
//			keyValueMap.put("source", caBean.getEventCode());
//			keyValueMap.put("ISIN", caBean.getISIN());
//			keyValueMap.put("BBGTicker", caBean.getBBGTicker());
//			keyValueMap.put("effectivedate", caBean.getEffectiveDate());
//			keyValueMap.put("grossAmount", caBean.getGrossAmount());
//	//				
//			keyValueMap.put("netAmount", caBean.getNetAmount());
//			keyValueMap.put("currency", caBean.getCurrency());
//			keyValueMap.put("ratio", caBean.getRatio());
//			keyValueMap.put("flag",caBean.getFlag());
			
			updateData("corporateactionfinal",keyValueMap);
		}
		catch (Exception e)
		{
			throw new Exception("Please enter valid data." + e.toString());
	    }
	}
	
	
	public void updateCorporateAction(CorporateActionsFinal caBean) throws Exception
	{
		try
		{
//			LocalDate localDate = LocalDate.now();
//			String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
	      
			Map<String,Object> keyValueMap = new HashMap<String,Object>();
			keyValueMap.put("source", caBean.getEventCode());
			keyValueMap.put("ISIN", caBean.getISIN());
			keyValueMap.put("BBGTicker", caBean.getBBGTicker());
			keyValueMap.put("effectivedate", caBean.getEffectiveDate());
			keyValueMap.put("grossAmount", caBean.getGrossAmount());
	//				
			keyValueMap.put("netAmount", caBean.getNetAmount());
			keyValueMap.put("currency", caBean.getCurrency());
			keyValueMap.put("ratio", caBean.getRatio());
			keyValueMap.put("flag",caBean.getFlag());
			
			updateData("corporateactionfinal",keyValueMap);
		}
		catch (Exception e)
		{
			throw new Exception("Please enter valid data." + e.toString());
	    }
	}
	
	public void insertCAs(List<CorporateActionsFinal> caList) throws Exception  
	{
		try
		{
//			LocalDate localDate = LocalDate.now();
//			String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
	      
			for (CorporateActionsFinal caBean : caList)
			{
				Map<String,Object> keyValueMap = new HashMap<String,Object>();
				Integer securityId = getSecurityId(caBean.getISIN(),caBean.getBBGTicker());
				
				keyValueMap.put("source",ICalCommonUtill.PRIMARY_DATABASE_CA);
				keyValueMap.put("securityid", securityId);
				keyValueMap.put("flag", "1");
				
				if(caBean.getISIN() != null)
					keyValueMap.put("ISIN", caBean.getISIN());
				if(caBean.getBBGTicker() != null)
					keyValueMap.put("BBGTicker", caBean.getBBGTicker());
				
				if(caBean.getEventCode() != null)
					keyValueMap.put("eventCode", caBean.getEventCode());
				if(caBean.getEffectiveDate() != null)
					keyValueMap.put("effectivedate", caBean.getEffectiveDate());
				
				if(caBean.getGrossAmount() != null)	
					keyValueMap.put("grossAmount", caBean.getGrossAmount());
				if(caBean.getCurrency() != null)	
					keyValueMap.put("currency", caBean.getCurrency());
				if(caBean.getRatio() != null)	
					keyValueMap.put("ratio", caBean.getRatio());
				
				if(caBean.getOldValue() != null)	
					keyValueMap.put("oldValue", caBean.getOldValue());
				if(caBean.getNewValue() != null)	
					keyValueMap.put("newValue", caBean.getNewValue());
					
				
				insertData("corporateactionfinal",keyValueMap);
			}
		}
		catch (Exception e)
		{
			throw new Exception("Please enter valid data." + e.toString());
	    }
	}
	
	public Integer getSecurityId(String ISIN ,String bbgTicker) throws Exception
	{
		Integer id =0;
		ResultSet rs = getSecutiry(ISIN ,bbgTicker);
		if(rs == null)
			throw new Exception("Security does not exist in the database");
		while (rs.next())
		{
            id = rs.getInt(1);
		}
		return  id;
	}
	
	private String SECURITY_TABLE_NAME = "security";
	public ResultSet getSecutiry(String ISIN ,String bbgTicker) throws Exception
	{		
		String strWhereClause = getSecurityExistQuerty( ISIN , bbgTicker);
		String strSelectQuery = DataUtill.createSelect(SECURITY_TABLE_NAME, strWhereClause);
		System.out.println(strSelectQuery);
		ResultSet rs = ExecuteQuery(strSelectQuery);
		return rs;		
	}

	
	private String getSecurityExistQuerty(String ISIN ,String bbgTicker)
	{
//		String strWhereClause = "Where  ISIN = '" +ISIN.trim() + "' and BBGTicker = '" +bbgTicker.trim() +  "' AND VT > 99990000";
		String strWhereClause = "Where  ISIN = '" +ISIN.trim() + "' and BBGTicker = '" +bbgTicker.trim() +  "' AND CONVERT(char(10), GetDate(),112)  BETWEEN vf AND vt";
		return strWhereClause;
		
	}
	
}