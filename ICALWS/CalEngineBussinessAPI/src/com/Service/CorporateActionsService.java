package com.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Bean.CACMBean;
import com.Bean.CorporateActionsFinal;
import com.Bean.SecurityBean;
import com.CalCommon.ICalCommonUtill;
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
	
//	public List<CorporateActionsFinal> getAllCAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
//	{
//		List<CorporateActionsFinal> CAList = new ArrayList<>();
//		CorporateActionsDao caDao = new CorporateActionsDao();
//		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,"EDI") ;
//		CAList =convertListToCorporateActionsFinalList(rs);
//		return CAList;		
//	}
//
//	public List<CorporateActionsFinal> getAllCAByDateAndSecurity(String securityId ,String effectiveDate) throws Exception
//	{
//		List<CorporateActionsFinal> CAList = new ArrayList<>();
//		CorporateActionsDao caDao = new CorporateActionsDao();
//		ResultSet rs = caDao.getAllCAByDateAndSecurity(securityId,effectiveDate) ;
//		CAList = convertListToCorporateActionsFinalList(rs);
//		return CAList;		
//	}
	
	public Map<String,CorporateActionsFinal> getAllCAByDateAndSecurityMap(String securityId ,String effectiveDate,String source) throws Exception
	{
		Map<String,CorporateActionsFinal> caMap =  new HashMap<String,CorporateActionsFinal>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndSecurity(securityId,effectiveDate,source) ;
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
			
		if(rs.getString("eventCode") != null)
			cBean.setEventCode(rs.getString("eventCode"));	
		
		if(rs.getString("currency") != null)
			cBean.setCurrency(rs.getString("currency"));
		cBean.setGrossAmount(rs.getDouble("grossAmount"));	
		cBean.setRatio(rs.getDouble("ratio"));
		if(rs.getString("oldValue") != null)
			cBean.setOldValue(rs.getString("oldValue"));
		if(rs.getString("newValue") != null)
			cBean.setNewValue(rs.getString("newValue"));
		
		if(rs.getString("flag") != null)
			cBean.setFlag(rs.getString("flag"));
	}
	
	public List<CorporateActionsFinal> getAllCAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
	{
		List<CorporateActionsFinal> CAList = new ArrayList<>();
		
		Map<String,CorporateActionsFinal> ediCAMap = new HashMap<String,CorporateActionsFinal>();
		Map<String,Map<String,CorporateActionsFinal>> fdsCAMap = new HashMap<String,Map<String,CorporateActionsFinal>>();
		
		ediCAMap = getAllCAFinalByDateAndCode(strCode,toDate,toDate,ICalCommonUtill.PRIMARY_DATABASE_CA);
		fdsCAMap = getAllFactsetCAByDateAndCode(strCode,toDate,toDate);
		
		for (Map.Entry<String,CorporateActionsFinal> caEntry : ediCAMap.entrySet()) 
		{
			String key = caEntry.getKey();
			CorporateActionsFinal caFinalEDI = caEntry.getValue();
			
			if(fdsCAMap.get(key)!= null)
			{
				caFinalEDI.setIsPrimaryOnly("1");
				Map<String,CorporateActionsFinal> fMap = fdsCAMap.get(key);
				CorporateActionsFinal caFinalFDS;
				if(fMap.get(caFinalEDI.getCurrency())!=null)
				{
					caFinalFDS =  fMap.get(caFinalEDI.getCurrency());
				}
				else 
				{
					String strCurrency = getCurrencyBySecurityId(caFinalEDI.getSecurityId());
					if(fMap.get(strCurrency)!=null)
					{
						caFinalFDS =  fMap.get(strCurrency);
					}
					else
					{
						Map.Entry<String,CorporateActionsFinal> entry = fMap.entrySet().iterator().next();
						caFinalFDS = entry.getValue();
					}
				}	
				if(caFinalEDI.getEventCode().trim().equalsIgnoreCase("ORD_DIV") || caFinalEDI.getEventCode().trim().equalsIgnoreCase("SPL_DIV"))
				{
					if((caFinalEDI.getGrossAmount() - caFinalFDS.getGrossAmount()) == 0 )
						caFinalEDI.setHasDifference("0");
					else
					{
						caFinalEDI.setHasDifference("1");
						caFinalEDI.setIconName("images/wrong.png");
					}
				}
				if(caFinalEDI.getEventCode().trim().equalsIgnoreCase("STOCK_SPLT") || caFinalEDI.getEventCode().trim().equalsIgnoreCase("DVD_STOCK"))
				{
					if((caFinalEDI.getRatio() - caFinalFDS.getRatio()) == 0 )
						caFinalEDI.setHasDifference("0");
					else
					{
						caFinalEDI.setHasDifference("1");
						caFinalEDI.setIconName("images/wrong.png");
					}
				}
				if(caFinalEDI.getEventCode().trim().equalsIgnoreCase("CHG_ID") || caFinalEDI.getEventCode().trim().equalsIgnoreCase("CHG_NAME")
						|| caFinalEDI.getEventCode().trim().equalsIgnoreCase("CHG_TKR"))
				{
					if(caFinalEDI.getNewValue().trim().equals(caFinalFDS.getNewValue()))
						caFinalEDI.setHasDifference("0");
					else
					{
						caFinalEDI.setHasDifference("1");
						caFinalEDI.setIconName("images/wrong.png");
					}
				}
			}
			else
			{
				caFinalEDI.setIsPrimaryOnly("0");
				caFinalEDI.setHasDifference("2");
				caFinalEDI.setIconName("images/right.png");
			}
			ediCAMap.put(key, caFinalEDI);
			CAList.add(caFinalEDI);
		}
		
//		List<CorporateActionsFinal> CAList = new ArrayList<>();
//		CorporateActionsDao caDao = new CorporateActionsDao();
//		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,"EDI") ;
//		CAList =convertListToCorporateActionsFinalList(rs);
		return CAList;		
	}
	
	private String getCurrencyBySecurityId(Integer securityId)
	{
		SecurityService ss = new SecurityService();
		List<SecurityBean> securityList = ss.getAllSecurities(" Where id = " + securityId);  
		String strCurrency = securityList.get(0).getCurrency();
		return strCurrency;
	}
	public Map<String,CorporateActionsFinal> getAllCAFinalByDateAndCode(String strCode ,String fromDate,String toDate,String source) throws Exception
	{
		Map<String,CorporateActionsFinal> caMap =  new HashMap<String,CorporateActionsFinal>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,source) ;
		caMap = convertResultSetToCAFinalBean(rs);
		return caMap;		
	}
	
	public Map<String,CorporateActionsFinal> convertResultSetToCAFinalBean(ResultSet rs) throws SQLException
	{
		Map<String,CorporateActionsFinal> caMap =  new HashMap<String,CorporateActionsFinal>();
		while (rs.next())
		{
			CorporateActionsFinal cBean = new CorporateActionsFinal(); 
			getCorporateActionsFinalBeanFromResultSet(rs,cBean);
			String strKey = cBean.getSecurityId().toString() + cBean.getEventCode();
			
			caMap.put(strKey,cBean);
	    }		
		return caMap;
	}
	
	public Map<String,Map<String,CorporateActionsFinal>> getAllFactsetCAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
	{
		Map<String,Map<String,CorporateActionsFinal>> caMap =  new HashMap<String,Map<String,CorporateActionsFinal>>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,ICalCommonUtill.SECONDARY_DATA_CA) ;
		caMap =convertListToCorporateActionsFinalList1(rs);
		return caMap;		
	}
	
	private Map<String,Map<String,CorporateActionsFinal>> convertListToCorporateActionsFinalList1(ResultSet rs) throws SQLException
	{
		Integer securityId = null;
		Map<String,Map<String,CorporateActionsFinal>> caMap =  new HashMap<String,Map<String,CorporateActionsFinal>>();
		while (rs.next())
		{
			Map<String,CorporateActionsFinal> caTempMap =  new HashMap<String,CorporateActionsFinal>();
			CorporateActionsFinal cBean = new CorporateActionsFinal();
			
//			if(rs.getString("securityid") != null)
//				securityId = rs.getInt("securityid");
//			if(rs.getString("eveny") != null)
//				securityId = rs.getInt("securityid");
//			if(caMap.get(securityId) != null)
//				caTempMap = caMap.get(securityId);
//			 
			getCorporateActionsFinalBeanFromResultSet(rs,cBean);
			if(caMap.get(cBean.getSecurityId() + cBean.getEventCode()) != null)
				caTempMap = caMap.get(cBean.getSecurityId() + cBean.getEventCode());

			caTempMap.put(cBean.getCurrency(),cBean);
			String strKey = cBean.getSecurityId().toString() + cBean.getEventCode();
			caMap.put(strKey,caTempMap);
	    }		
		return caMap;
	}
	
	
	
	
}