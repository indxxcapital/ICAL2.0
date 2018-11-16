package com.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.Bean.CACMBean;
import com.Bean.CorporateActionsFinal;
import com.Bean.SecurityBean;
import com.DataService.CorporateActionsDao;

public class CACMIdChangeService extends CACMService
{
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
			getCAFinalBeanFromResultSet(rs,cBean);
			String strKey = cBean.getSecurityId().toString() + cBean.getEventCode();
			
			caMap.put(strKey,cBean);
	    }		
		return caMap;
	}
		
	public void getCAFinalBeanFromResultSet(ResultSet rs, CorporateActionsFinal cBean) throws SQLException
	{
		if(rs.getString("id") != null)
			cBean.setId(rs.getInt("id"));
		if(rs.getString("securityid") != null)
			cBean.setSecurityId(rs.getInt("securityid"));
		if(rs.getString("ISIN") != null)
			cBean.setISIN(rs.getString("ISIN"));	
		if(rs.getString("BBGTicker") != null)
			cBean.setBBGTicker(rs.getString("BBGTicker"));
		
		if(rs.getString("eventCode") != null)
			cBean.setEventCode(rs.getString("eventCode"));	
		
		if(rs.getString("currency") != null)
			cBean.setCurrency(rs.getString("currency"));	
		if(rs.getString("grossAmount") != null)
			cBean.setGrossAmount(rs.getDouble("grossAmount"));	
		if(rs.getString("ratio") != null)
			cBean.setRatio(rs.getDouble("ratio"));
	}
	
	
	public Map<String,CACMBean> generateIdChanheCACM(String toDate) throws Exception
	{
		Map<String,CACMBean> CACMMap = new HashMap<String,CACMBean>();
		Map<String,CorporateActionsFinal> ediCAMap = new HashMap<String,CorporateActionsFinal>();
		Map<String,CorporateActionsFinal> fdsCAMap = new HashMap<String,CorporateActionsFinal>();
		try 
		{
			ediCAMap = getAllCAFinalByDateAndCode("CHG_ID','CHG_NAME','CHG_TKR" ,toDate,toDate,"EDI");
			fdsCAMap = getAllCAFinalByDateAndCode("CHG_ID','CHG_NAME','CHG_TKR",toDate,toDate,"FACTSET");
			
			for (Map.Entry<String,CorporateActionsFinal> caEntry : ediCAMap.entrySet()) 
			{
				CACMBean cacmBean = new CACMBean();
				String key = caEntry.getKey();
				CorporateActionsFinal caFinalEDI = caEntry.getValue();
				
				SecurityService ss = new SecurityService();
				SecurityBean sBean = ss.getAllSecurities("Where id =" + caFinalEDI.getSecurityId()).get(0);
				
				cacmBean.setSecurityId(caFinalEDI.getSecurityId());
				
				cacmBean.setISIN(caFinalEDI.getISIN());
				cacmBean.setBBGTicker(caFinalEDI.getBBGTicker());
				cacmBean.setName(sBean.getFullName());
				
				cacmBean.setEventCodeEDI(caFinalEDI.getEventCode());
				cacmBean.setOldValueEDI(caFinalEDI.getOldValue());
				cacmBean.setNewValueEDI(caFinalEDI.getNewValue());
				
				if(fdsCAMap.get(key)!= null)
				{
					CorporateActionsFinal caFinalFDS = fdsCAMap.get(key);
					
					cacmBean.setEventCodeFDS(caFinalFDS.getEventCode());
					cacmBean.setOldValueFDS(caFinalFDS.getOldValue());
					cacmBean.setNewValueFDS(caFinalFDS.getNewValue());
					fdsCAMap.remove(key);
				}
					
				CACMMap.put(key, cacmBean);
				
			}
			for (Map.Entry<String,CorporateActionsFinal> caEntry : fdsCAMap.entrySet()) 
			{
				String key = caEntry.getKey();
				CorporateActionsFinal caFinalFDS = caEntry.getValue();
				CACMBean cacmBean = new CACMBean();
				
				SecurityService ss = new SecurityService();
				SecurityBean sBean = ss.getAllSecurities("Where id =" + caFinalFDS.getSecurityId()).get(0);
				
				cacmBean.setSecurityId(caFinalFDS.getSecurityId());
				
				cacmBean.setISIN(caFinalFDS.getISIN());
				cacmBean.setBBGTicker(caFinalFDS.getBBGTicker());
				cacmBean.setName(sBean.getFullName());
				
				cacmBean.setEventCodeFDS(caFinalFDS.getEventCode());
				cacmBean.setOldValueFDS(caFinalFDS.getOldValue());
				cacmBean.setNewValueFDS(caFinalFDS.getNewValue());
				
				CACMMap.put(key, cacmBean);
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
		return CACMMap;
	}	
}
