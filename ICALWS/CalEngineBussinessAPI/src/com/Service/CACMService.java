package com.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.Bean.CACMBean;
import com.Bean.CorporateActionsFinal;
import com.DataService.CorporateActionsDao;

public class CACMService {
	
	public Map<String,CACMBean> getAllEDICAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
	{
		Map<String,CACMBean> caMap =  new HashMap<String,CACMBean>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,"EDI") ;
		caMap = convertResultSetToCACMBean(rs);
		return caMap;		
	}
	
	public Map<String,CACMBean> convertResultSetToCACMBean(ResultSet rs) throws SQLException
	{
		Map<String,CACMBean> caMap =  new HashMap<String,CACMBean>();
		while (rs.next())
		{
			CACMBean cBean = new CACMBean(); 
			getCACMBeanFromResultSet(rs,cBean);
			String strKey = cBean.getSecurityId().toString() + cBean.getEventCodeEDI();
			
			caMap.put(strKey,cBean);
	    }		
		return caMap;
	}
	
	public void getCACMBeanFromResultSet(ResultSet rs, CACMBean cBean) throws SQLException
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
			cBean.setEventCodeEDI(rs.getString("eventCode"));	
		
		if(rs.getString("currency") != null)
			cBean.setCurrencyEDI(rs.getString("currency"));	
		if(rs.getString("grossAmount") != null)
			cBean.setGrossAmountEDI(rs.getDouble("grossAmount"));	
		if(rs.getString("ratio") != null)
			cBean.setRatioEDI(rs.getDouble("ratio"));	
	}
	
	public void getCorporateActionsFinalBeanFromResultSet(ResultSet rs, CorporateActionsFinal cBean) throws SQLException
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
	
	
}
