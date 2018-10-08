package com.DataService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.Bean.IndexBean;
import com.CalCommon.ICalCommonUtill;

public class IndexDao extends DefaultDao
{
	private String INDEX_TABLE_NAME = "indexdescription";
	
	 public ResultSet ExecuteMSSQLQuery(String sqlQuery) throws SQLException, ClassNotFoundException
	 {
		 
		 ResultSet rs = ConnectionFactory.Execute(sqlQuery);
		 return rs;		 
	 }
	 
	public void insertIndex(List<IndexBean> indexBeanList) throws Exception 
	{
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
		
		for (IndexBean indexBean : indexBeanList)
		{
			Map<String,Object> keyValueMap = new HashMap<String,Object>();
			keyValueMap.put("indexName", indexBean.getIndexName());
			keyValueMap.put("clientName", indexBean.getClientName());
			keyValueMap.put("indexType", indexBean.getIndexType());
			keyValueMap.put("indexTicker", indexBean.getIndexTicker());
			keyValueMap.put("normalCashDivAdj", indexBean.getNormalCashDivAdj());
			keyValueMap.put("specialCashDivAdj", indexBean.getSpecialCashDivAdj());
			keyValueMap.put("zoneType", indexBean.getZoneType());
			keyValueMap.put("status", indexBean.getStatus());
			keyValueMap.put("indexLiveDate", indexBean.getIndexLiveDate());
			keyValueMap.put("currency", indexBean.getCurrency());
			keyValueMap.put("disseminationSource", indexBean.getDisseminationSource());
//			keyValueMap.put("OutputFileFormat", indexBean.getOutputFilesFormat());
			keyValueMap.put("indexWeightType", indexBean.getIndexWeightType());
			keyValueMap.put("CloseIndexValue", indexBean.getCloseIndexValue());
			keyValueMap.put("IndexMarketValue", indexBean.getIndexMarketValue());
			keyValueMap.put("flag","1");
			keyValueMap.put("vf", strDate);
			keyValueMap.put("vt","9999-12-31");
			
			insertData(INDEX_TABLE_NAME,keyValueMap);
		}
	}
	
	public List<IndexBean> getAllIndexList(String filter) throws SQLException, ClassNotFoundException
	{
		List<IndexBean> indexList = new ArrayList<>();
		if(filter.equalsIgnoreCase(""))
			filter = " Where flag = 1 AND CAST(GETDATE() AS DATE) BETWEEN vf AND vt ";
		else
			filter += " AND  flag = 1 AND CAST(GETDATE() AS DATE) BETWEEN vf AND vt "; 
		
		ResultSet rs = getAllData(INDEX_TABLE_NAME,filter);
		indexList = converResultToIndexBean(rs);
		return indexList;
	}
	
	public List<IndexBean> converResultToIndexBean(ResultSet rs) throws SQLException, ClassNotFoundException
	{
		List<IndexBean> indexList = new ArrayList<>();
		while (rs.next())
		{
			IndexBean iBean = new IndexBean(); 
			getBeanFromResultSet(rs,iBean);
			indexList.add(iBean);
	    }
		
		return indexList;
	}
	
	private void getBeanFromResultSet(ResultSet rs,IndexBean iBean ) throws SQLException
	{
		iBean.setId(rs.getInt("id"));
		if(rs.getString("clientName") != null)
			iBean.setClientName(rs.getString("clientName"));	
		if(rs.getString("indexLiveDate") != null)
		{
			System.out.println(rs.getString("indexLiveDate"));
			iBean.setIndexLiveDateStr(rs.getString("indexLiveDate"));
			System.out.println(iBean.getIndexLiveDateStr());
		}
		if(rs.getString("indexName") != null)
			iBean.setIndexlName(rs.getString("indexName"));
		if(rs.getString("indexTicker") != null)
			iBean.setIndexTicker(rs.getString("indexTicker"));
		if(rs.getString("indexType") != null)
			iBean.setIndexType(rs.getString("indexType"));
		if(rs.getString("normalCashDivAdj") != null)
			iBean.setNormalCashDivAdj(rs.getString("normalCashDivAdj"));
		if(rs.getString("specialCashDivAdj") != null)
			iBean.setSpecialCashDivAdj(rs.getString("specialCashDivAdj"));
		if(rs.getString("zoneType") != null)
			iBean.setZoneType(rs.getString("zoneType"));
		if(rs.getString("currency") != null)
			iBean.setCurrency(rs.getString("currency"));
		if(rs.getString("disseminationSource") != null)
			iBean.setDisseminationSource(rs.getString("disseminationSource"));
//		if(rs.getString("OutputFileFormat") != null)
//			iBean.setOutputFilesFormat(rs.getString("OutputFileFormat"));
		if(rs.getString("status") != null)
		{
			String strStatus = ICalCommonUtill.getIndexStatusCodeNameMap().get(rs.getString("status"));
			iBean.setStatus(strStatus);
		}
		if(rs.getString("indexWeightType") != null)
		{
			String strWeightType = ICalCommonUtill.getIndexWeightCodeTypeMap().get(rs.getString("indexWeightType"));
			iBean.setIndexWeightType(strWeightType);
		}
		iBean.setCloseIndexValue(rs.getFloat("CloseIndexValue"));
		iBean.setIndexMarketValue(rs.getFloat("IndexMarketValue"));
		if(rs.getString("runIndexDate") != null)
			iBean.setIndexRunDate(rs.getString("runIndexDate"));
		System.out.println(iBean.getIndexMarketValue());
	}
	
	public void deleteIndex(String indexIds) throws Exception 
	{
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate.minusDays(1));
		
//		String UPDATE_QUERT = "UPDATE ical2.indexcomposition SET flag = 0, vt = '" + strDate + "'  "
		String UPDATE_QUERT = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexcomposition SET flag = 0, vt = '" + strDate + "'  "
		 						+ " Where indexCode in ( SELECT indexTicker FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription where id in (" + indexIds + "))";

		
		System.out.println(UPDATE_QUERT);
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(UPDATE_QUERT);
		
//		String INDEX_UPDATE_QUERY = "UPDATE ical2.indexdescription SET flag = 0 ,vt = '" + strDate + "'  where id in (" + indexIds + ")";
		String INDEX_UPDATE_QUERY = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription SET flag = 0 ,vt = '" + strDate + "'  where id in (" + indexIds + ")";
		System.out.println(INDEX_UPDATE_QUERY);
		int iValue2 =ConnectionFactory.ExecuteUpdateInsertDelete(INDEX_UPDATE_QUERY);
	}
	
	public void updateIndexStatus(String indexTicker,String status) throws Exception 
	{
//		String strUpdateQuery = "UPDATE ical2.indexdescription SET status = '" +status + "' WHERE indexTicker = '" + indexTicker + "'";
		String strUpdateQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription SET status = '" +status + "' WHERE indexTicker = '" + indexTicker + "'";

		updatetData(strUpdateQuery);
	}
	
	public void updateIndicesStatus(String indexIds,String status) throws Exception 
	{
//		String strUpdateQuery = "UPDATE ical2.indexdescription SET status = '" +status + "' WHERE id  in (" + indexIds + ")";
		String strUpdateQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription SET status = '" +status + "' WHERE id  in (" + indexIds + ")";
		System.out.println(strUpdateQuery);
		updatetData(strUpdateQuery);
	}
	
	public void updateIndicesStatusAsRun(String indexIds,String status,String runDate) throws Exception 
	{
//		String strUpdateQuery = "UPDATE ical2.indexdescription SET status = '" +status + "',runIndexDate='" +runDate + "' WHERE id  in (" + indexIds + ")";
		String strUpdateQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription SET status = '" +status + "',runIndexDate='" +runDate + "' WHERE id  in (" + indexIds + ")";
		System.out.println(strUpdateQuery);
		updatetData(strUpdateQuery);
	}
	
	
	public Map<String,Object> getCurrencyRateMap(String toCurrency,String toDate)
	{
		Map<String,Object> keyValueMap = new HashMap<String,Object>();
//		String strQuery = "SELECT * FROM ical2.currencyrate where toCurrency = '" + toCurrency +
		String strQuery = "SELECT * FROM " + ConfigUtil.propertiesMap.get("dbName") + ".currencyrate where toCurrency = '" + toCurrency +
				"' and vd = '" + toDate + "'";
		ResultSet rs = null;
		try 
		{
			rs = ExecuteQuery(strQuery);
			while (rs.next())
			{
				if(rs.getString("fromCurrency") != null )
				{
					keyValueMap.put(rs.getString("fromCurrency"), rs.getFloat("rate"));
				}
			}
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return keyValueMap;
	}
	
	public void deleteSecurityFromIndex(String indexTicker,String securityId) throws Exception 
	{
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate.minusDays(1));
		
//		String strQuery = "UPDATE ical2.indexcomposition SET flag = '0' and vt = "+ strDate  + "Where indexCode ='"+ indexTicker + "' and securityId='" +securityId + "'";
		String strQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexcomposition SET flag = '0' and vt = "+ strDate  + "Where indexCode ='"+ indexTicker + "' and securityId='" +securityId + "'";

		System.out.println(strQuery);
		ConnectionFactory.getConnection();
		  
		ConnectionFactory.ExecuteUpdateInsertDelete(strQuery);
	}
	
	public int updateIndexValue(Map<String,Object> keyValueMap) throws SQLException, ClassNotFoundException
	{
//		String strQuery = "UPDATE ical2.indexdescription SET IndexMarketValue = "+ keyValueMap.get("IndexMarketValue")
		String strQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription SET IndexMarketValue = "+ keyValueMap.get("IndexMarketValue")
				+ ", CloseIndexValue =" + keyValueMap.get("CloseIndexValue") 
				+ ", IndexValuesAddedOn = '" +  keyValueMap.get("IndexValuesAddedOn")  
				+ "'  WHERE (indexTicker = '" +keyValueMap.get("indexTicker") + "')";

		System.out.println(strQuery);
		ConnectionFactory.getConnection();
	      
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(strQuery);
//		ConnectionFactory.closeConnection();
		return iValue;
	}
	
	public Map<String,Object> getIndexFileDetails(String indexTicker,String tableName) throws ClassNotFoundException, SQLException
	{
		Map<String,Object> closingFileDetailsMap = new HashMap<String,Object>();
		
//		String strQuery = "Select top 1 * from ical2.closingfiledetails where indexTicker = '" +indexTicker+ "' order by fileCreationDate desc " ;
		String strQuery = "Select top 1 * from " + ConfigUtil.propertiesMap.get("dbName") + ".closingfiledetails where indexTicker = '" +indexTicker+ "' order by fileCreationDate desc " ;
		ResultSet rs;
		try
		{
			rs = ExecuteQuery(strQuery);
			while (rs.next())
			{
//				if(rs.getString("securityId") != null )
				{
					closingFileDetailsMap.put("indexTicker", rs.getString("indexTicker"));
					closingFileDetailsMap.put("indexValue", rs.getFloat("indexValue"));
					closingFileDetailsMap.put("marketValue", rs.getFloat("marketValue"));
					closingFileDetailsMap.put("divisor", rs.getFloat("divisor"));
					closingFileDetailsMap.put("sumOfWeights", rs.getFloat("sumOfWeights"));
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		return closingFileDetailsMap;
	}
	
	public Map<String,Object> getIndexOpeningFileDetails(String indexTicker)
	{
		Map<String,Object> closingFileDetailsMap = new HashMap<String,Object>();
		
//		String strQuery = "Select top 1 * from ical2.openingfiledetails where indexTicker = '" +indexTicker+ "' order by fileCreationDate desc" ;
		String strQuery = "Select top 1 * from " + ConfigUtil.propertiesMap.get("dbName") + ".openingfiledetails where indexTicker = '" +indexTicker+ "' order by fileCreationDate desc" ;
		ResultSet rs;
		try
		{
			rs = ExecuteQuery(strQuery);
			if(rs != null)
			while (rs.next())
			{
				if(rs.getString("securityId") != null )
				{
					closingFileDetailsMap.put("indexTicker", rs.getString("indexTicker"));
					closingFileDetailsMap.put("indexValue", rs.getFloat("indexValue"));
					closingFileDetailsMap.put("marketValue", rs.getFloat("marketValue"));
					closingFileDetailsMap.put("divisor", rs.getFloat("divisor"));
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return closingFileDetailsMap;
	}
	
	public int updateSecurityShares(int id,float shares) throws SQLException, ClassNotFoundException
	{
//		String strQuery = "UPDATE ical2.indexcomposition SET shares = "+ shares + " WHERE (id = '" +id + "')";
		String strQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexcomposition SET shares = "+ shares + " WHERE (id = '" +id + "')";

		System.out.println(strQuery);
		ConnectionFactory.getConnection();
	      
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(strQuery);
//		ConnectionFactory.closeConnection();
		
		return iValue;
	}
	
	public int updateSecurityWeight(int id,float weight) throws SQLException, ClassNotFoundException
	{
//		String strQuery = "UPDATE ical2.indexcomposition SET weight = "+ weight + " WHERE (id = '" +id + "')";
		String strQuery = "UPDATE " + ConfigUtil.propertiesMap.get("dbName") + ".indexcomposition SET weight = "+ weight + " WHERE (id = '" +id + "')";

		System.out.println(strQuery);
		ConnectionFactory.getConnection();
	      
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(strQuery);
//		ConnectionFactory.closeConnection();

		return iValue;
	}
	
	public List<IndexBean> getAllLiveIndexForClosingFile(String zone) throws SQLException, ClassNotFoundException
	{
		List<IndexBean> indexList = new ArrayList<>();
//		String strQuery  = "SELECT * FROM ical2.indexdescription "
		String strQuery  = "SELECT * FROM " + ConfigUtil.propertiesMap.get("dbName") + ".indexdescription "
				+ "where  status='LI' and flag=1 and indexLiveDate >= CAST(GETDATE() AS DATE)  "
				+ "AND CAST(GETDATE() AS DATE) BETWEEN vf AND vt AND zoneType='" + zone + "'";
		
		System.out.println(strQuery);
		ResultSet rs = ExecuteQuery(strQuery);
		indexList = converResultToIndexBean(rs);
		return indexList;
	}	
}
