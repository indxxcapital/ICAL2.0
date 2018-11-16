package com.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.Bean.CACMBean;
import com.Bean.CorporateActionsFinal;
import com.DataService.CorporateActionsDao;

public class CACMSplitService  extends CACMService
{
	public Map<String,CACMBean> generateSplitCACM(String toDate) throws Exception
	{
		Map<String,CACMBean> ediCAMap = new HashMap<String,CACMBean>();
		Map<String,CorporateActionsFinal> fdsCAMap = new HashMap<String,CorporateActionsFinal>();
		try 
		{
			ediCAMap = getAllEDICAByDateAndCode("STOCK_SPLT','DVD_STOCK" ,toDate,toDate);
			fdsCAMap = getAllFactsetCAByDateAndCode("STOCK_SPLT','DVD_STOCK",toDate,toDate);
			
			for (Map.Entry<String,CACMBean> caEntry : ediCAMap.entrySet()) 
			{
				String key = caEntry.getKey();
				CACMBean cacmBean = caEntry.getValue();
				
				if(fdsCAMap.get(key)!= null)
				{
					CorporateActionsFinal caFinal = fdsCAMap.get(key);
					
					cacmBean.setEventCodeFDS(caFinal.getEventCode());
					cacmBean.setRatioFDS(caFinal.getRatio());
					
					ediCAMap.put(key, cacmBean);
					fdsCAMap.remove(key);
				}			 
			}
			for (Map.Entry<String,CorporateActionsFinal> caEntry : fdsCAMap.entrySet()) 
			{
				String key = caEntry.getKey();
				CorporateActionsFinal caFinal1 = caEntry.getValue();
				
				CACMBean cacmBean = new CACMBean();
				
				cacmBean.setId(caFinal1.getId());
				cacmBean.setISIN(caFinal1.getISIN());
				cacmBean.setBBGTicker(caFinal1.getBBGTicker());
				cacmBean.setSecurityId(caFinal1.getSecurityId());
				cacmBean.setEventCodeFDS(caFinal1.getEventCode());
				cacmBean.setRatioFDS(caFinal1.getRatio());
				
				ediCAMap.put(key, cacmBean);
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
		return ediCAMap;
	}	
	
	public Map<String,CorporateActionsFinal>  getAllFactsetCAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
	{
		Map<String,CorporateActionsFinal>  fdsCAMap = new HashMap<String,CorporateActionsFinal>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,"FACTSET") ;
		fdsCAMap =convertListToCorporateActionsFinalList(rs);
		return fdsCAMap;		
	}
	
	private Map<String,CorporateActionsFinal>  convertListToCorporateActionsFinalList(ResultSet rs) throws SQLException
	{
		Map<String,CorporateActionsFinal> fdsCAMap = new HashMap<String,CorporateActionsFinal>();
		while (rs.next())
		{
			CorporateActionsFinal cBean = new CorporateActionsFinal();
			getCorporateActionsFinalBeanFromResultSet(rs,cBean);
			String strKey = cBean.getSecurityId().toString() + cBean.getEventCode();
			fdsCAMap.put(strKey,cBean);
	    }		
		return fdsCAMap;
	}	
}
