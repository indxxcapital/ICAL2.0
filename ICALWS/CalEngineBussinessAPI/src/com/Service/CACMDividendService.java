package com.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.Bean.CACMBean;
import com.Bean.CorporateActionsFinal;
import com.Bean.SecurityBean;
import com.CalCommon.ICalCommonUtill;
import com.DataService.CorporateActionsDao;

public class CACMDividendService extends CACMService
{
	public Map<String,CACMBean> generateDividendCACM(String toDate) throws Exception
	{
		Map<String,CACMBean> ediCAMap = new HashMap<String,CACMBean>();
		try 
		{
			ediCAMap = getAllEDICAByDateAndCode("ORD_DIV','SPL_DIV" ,toDate,toDate);
			Map<String,Map<String,CorporateActionsFinal>> factsetCAMap = getAllFactsetCAByDateAndCode("ORD_DIV','SPL_DIV",toDate,toDate);
			
			for (Map.Entry<String,CACMBean> caEntry : ediCAMap.entrySet()) 
			{
				String key = caEntry.getKey();
				CACMBean cacmBean = caEntry.getValue();
				
				if(factsetCAMap.get(key)!= null)
				{
					Map<String,CorporateActionsFinal> fMap = factsetCAMap.get(key);
					CorporateActionsFinal caFinal;
					if(fMap.get(cacmBean.getCurrencyEDI())!=null)
					{
						caFinal =  fMap.get(cacmBean.getCurrencyEDI());
					}
					else 
					{
						String strCurrency = getCurrencyBySecurityId(cacmBean.getSecurityId());
						if(fMap.get(strCurrency)!=null)
						{
							caFinal =  fMap.get(strCurrency);
						}
						else
						{
							Map.Entry<String,CorporateActionsFinal> entry = fMap.entrySet().iterator().next();
							caFinal = entry.getValue();
						}
					}	
					factsetCAMap.remove(key);
					cacmBean.setCurrencyFDS(caFinal.getCurrency());
					cacmBean.setGrossAmountFDS(caFinal.getGrossAmount());
					cacmBean.setEventCodeFDS(caFinal.getEventCode());
					ediCAMap.put(key, cacmBean);
				}			 
			}
			for (Map.Entry<String,Map<String,CorporateActionsFinal>> caEntry : factsetCAMap.entrySet()) 
			{
				String  key = caEntry.getKey();
				Map<String,CorporateActionsFinal> fMap = caEntry.getValue();
				Map.Entry<String,CorporateActionsFinal> entry = fMap.entrySet().iterator().next();
				CACMBean cacmBean = new CACMBean();
				
				cacmBean.setId(entry.getValue().getId());
				cacmBean.setSecurityId(entry.getValue().getSecurityId());
				cacmBean.setISIN(entry.getValue().getISIN());
				cacmBean.setBBGTicker(entry.getValue().getBBGTicker());
				
				cacmBean.setCurrencyFDS(entry.getValue().getCurrency());
				cacmBean.setGrossAmountFDS(entry.getValue().getGrossAmount());
				cacmBean.setEventCodeFDS(entry.getValue().getEventCode());
				ediCAMap.put(key, cacmBean);
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
		return ediCAMap;
	}	
	
	private String getCurrencyBySecurityId(Integer securityId)
	{
		SecurityService ss = new SecurityService();
		List<SecurityBean> securityList = ss.getAllSecurities(" Where id = " + securityId);  
		String strCurrency = securityList.get(0).getCurrency();
		return strCurrency;
	}

//	public FileOutputStream parseInputFile(FileInputStream input,String outFilePath) throws IOException
//	{	
//		ExcelTemplateGenerator templateGenerator = ICalUtil.generateTemplate();
//		List<SecurityBean> beanList = new ArrayList<>();
//		POIFSFileSystem fs = new POIFSFileSystem (input);
//		HSSFWorkbook wb = new HSSFWorkbook(fs);
//		HSSFSheet sheet = wb.getSheetAt(0);
//		for(int i=1; i<=sheet.getLastRowNum(); i++)
//		{
//			SecurityBean securityBean = new SecurityBean();
//			HSSFRow HSSFRow = sheet.getRow(i);
//			
//			String isin = HSSFRow.getCell(2).getStringCellValue();
//			String ticker = HSSFRow.getCell(3).getStringCellValue();
//
//			securityBean.setISIN(isin);
//			securityBean.setBBGTicker(ticker);
//			
//			try {
//				if(!checkForExistingSecurities(securityBean))
//				{
//					beanList.add(securityBean);
//					templateGenerator.writeValueRow(ICalUtil.setCloumnsValue(securityBean));
//				}
//			} catch (ClassNotFoundException | SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		FileOutputStream out = new FileOutputStream(outFilePath);
////		templateGenerator.getWorkBook().write(out);
////        out.close();
//        return out;
//	}
	
//	public Map<Integer,CACMBean> getAllEDICAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
//	{
//		Map<Integer,CACMBean> caMap =  new HashMap<Integer,CACMBean>();
//		CorporateActionsDao caDao = new CorporateActionsDao();
//		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,"EDI") ;
//		caMap = convertResultSetToCACMBean(rs);
//		return caMap;		
//	}
//	
	public Map<String,Map<String,CorporateActionsFinal>> getAllFactsetCAByDateAndCode(String strCode ,String fromDate,String toDate) throws Exception
	{
		Map<String,Map<String,CorporateActionsFinal>> caMap =  new HashMap<String,Map<String,CorporateActionsFinal>>();
		CorporateActionsDao caDao = new CorporateActionsDao();
		ResultSet rs = caDao.getAllCAByDateAndCode(strCode,fromDate,toDate,ICalCommonUtill.SECONDARY_DATA_CA) ;
		caMap =convertListToCorporateActionsFinalList(rs);
		return caMap;		
	}
	
	private Map<String,Map<String,CorporateActionsFinal>> convertListToCorporateActionsFinalList(ResultSet rs) throws SQLException
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
