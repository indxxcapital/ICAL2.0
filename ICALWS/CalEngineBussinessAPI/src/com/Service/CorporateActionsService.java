package com.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Bean.CorporateActionsFinal;
import com.DataService.CorporateActionsDao;
import com.Validations.CorporateActionsValidations;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CorporateActionsService 
{
//	public void adjustCorporateAction(String eventCode)
//	{
//		CAEnum CA = CAEnum.valueOf(eventCode);
//	        
//		switch(CA)
//		{
//			case SPIN_OFF:
//			break;
//			
//			case BANKRUPTCY:
//				break;
//				
//			case DVD_STOCK:
//				break;
//				
//			case RIGHTS_OFFER:
//				break;
//				
//			case STOCK_SPLIT:
//				break;
//				
//			case DIV_1:
//				break;
//				
//			case TICKER_CHANGE:
//				break;
//				
//			case MERGER:
//				break;
//				
//			case NEW_LISTING:
//				break;
//				
//			case PR_EXCH_CHG:
//				break;
//		
//			case DVD_CASH:
//				break;
//				
//			case SEC_RECLASSIFY:
//				break;
//				
//			case ACQUISITION:
//				break;		
//		}				
//	}
	
	public List<CorporateActionsFinal> getAllCAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
	{
		List<CorporateActionsFinal> CAList = new ArrayList<>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,"EDI") ;
		CAList =convertListToCorporateActionsFinalList(rs);
		return CAList;		
	}
//
//	public List<CorporateActionsFinal> getAllCAByDateAndSecurity(String securityId ,String effectiveDate) throws Exception
//	{
//		List<CorporateActionsFinal> CAList = new ArrayList<>();
//		CorporateActionsDao caDao = new CorporateActionsDao();
//		ResultSet rs = caDao.getAllCAByDateAndSecurity(securityId,effectiveDate) ;
//		CAList = convertListToCorporateActionsFinalList(rs);
//		return CAList;		
//	}
	
	public Map<String,CorporateActionsFinal> getAllCAByDateAndSecurityMap(String securityId ,String effectiveDate) throws Exception
	{
		Map<String,CorporateActionsFinal> caMap =  new HashMap<String,CorporateActionsFinal>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndSecurity(securityId,effectiveDate) ;
		if(rs != null)
			caMap = convertListToCorporateActionsFinalMap(rs);
		return caMap;		
	}
	
	public void updateCADate(CorporateActionsFinal caBean) throws ClassNotFoundException, SQLException 
	{
		CorporateActionsDao caDao = new CorporateActionsDao();
		caDao.updateCorporateActions(caBean);
	}	
	
	public void updateCAData(Map<String,Object> keyValueMap) throws Exception 
	{
		CorporateActionsDao caDao = new CorporateActionsDao();
		caDao.updateCorporateAction(keyValueMap);
	}	
	
	
	
	public boolean importCorporateActionsDataFromCsv(String filePath) throws IOException,Exception
	{
		try
		{
			addCorporateActions(filePath);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public void addCorporateActions(String filePath) throws Exception 
	{
		try 
		{
			List<CorporateActionsFinal> caList = new ArrayList<>();
			CorporateActionsDao caDao = new CorporateActionsDao();
			
	        FileReader filereader = new FileReader(filePath);
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	        List<String[]> allData = csvReader.readAll();
	        
	        CorporateActionsValidations.validateCAAddFile(filePath,allData);
	       
	        // print Data
	        for (String[] row : allData) 
	        {
	        	if (!row[1].isEmpty()) 	
	        	{
	        		CorporateActionsFinal caBean = new CorporateActionsFinal();
		        	ICalUtil.setCorporateActionsBeanToAddCAs(caBean,row);
					
		        	caList.add(caBean);
	        	}
	        }
	        caDao.insertCAs(caList);
		}
	    catch (Exception e)
		{
	        throw e;
	    }
	}
//	
//	public List<CorporateActionsFinal> getAllCAByEventCode(String eventCodeList) throws SQLException
//	{
//		List<CorporateActionsFinal> CAList = new ArrayList<>();
//		CorporateActionsDao caDao = new CorporateActionsDao();
//		ResultSet rs = caDao.getAllCAByEventCode(eventCodeList) ;
//		CAList =convertListToCorporateActionsFinalList(rs);
//		return CAList;		
//	}
	
	private List<CorporateActionsFinal> convertListToCorporateActionsFinalList(ResultSet rs) throws SQLException
	{
		List<CorporateActionsFinal> CAList = new ArrayList<>();
		while (rs.next())
		{
			CorporateActionsFinal cBean = new CorporateActionsFinal(); 
			getCorporateActionsFinalBeanFromResultSet(rs,cBean);
			CAList.add(cBean);
	    }		
		return CAList;
	}
	
	private Map<String,CorporateActionsFinal> convertListToCorporateActionsFinalMap(ResultSet rs) throws SQLException
	{
		Map<String,CorporateActionsFinal> caMap =  new HashMap<String,CorporateActionsFinal>();
		while (rs.next())
		{
			CorporateActionsFinal cBean = new CorporateActionsFinal(); 
			getCorporateActionsFinalBeanFromResultSet(rs,cBean);
			caMap.put(cBean.getEventCode(), cBean);
	    }		
		return caMap;
	}

	private void getCorporateActionsFinalBeanFromResultSet(ResultSet rs, CorporateActionsFinal cBean) throws SQLException
	{
		cBean.setId(rs.getInt("id"));
		cBean.setSecurityId(rs.getInt("securityid"));
		
		if(rs.getString("ISIN") != null)
			cBean.setISIN(rs.getString("ISIN"));	
		if(rs.getString("BBGTicker") != null)
			cBean.setBBGTicker(rs.getString("BBGTicker"));
		if(rs.getString("currency") != null)
			cBean.setCurrency(rs.getString("currency"));	
		if(rs.getString("eventCode") != null)
			cBean.setEventCode(rs.getString("eventCode"));	
		if(rs.getString("flag") != null)
			cBean.setFlag(rs.getString("flag"));
		
		cBean.setGrossAmount(rs.getDouble("grossAmount"));	
//		cBean.setNetAmount(rs.getDouble("netAmount"));
		cBean.setRatio(rs.getDouble("ratio"));
	}
}