package com.icalrest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.Bean.CorporateActionsFinal;
import com.Service.CorporateActionsService;
import com.Service.SecurityService;

@Path("/CorporateActions") 
public class CorporateActionsRestService {

//	@POST
//	@Path("/getTodaysCA")
//	public Response getCurrentCA(JsonObject  jsonObject) throws Exception
//	{
//		System.out.println("in getTodaysCA");
//		System.out.println(jsonObject);
//		
//		String strEventCode = jsonObject.getString("eventCode");
//		System.out.println(strEventCode);
//		String strFromDate = jsonObject.getString("effectiveDateFrom");
//		System.out.println(strFromDate);
//		String strToDate = "";
////		if(jsonObject.getString("effectiveDateTo") != null)
////			strToDate = jsonObject.getString("effectiveDateTo");
////		else
//			strToDate = strFromDate; 
//		System.out.println(strToDate);
//		
//		return getCAList(strEventCode,strFromDate,strToDate);
//	}	
	
	@POST
	@Path("/getCA")
	public Response getCA(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in getCA");
		System.out.println(jsonObject);
		
		String strDivEventCode = jsonObject.getString("eventCodeDiv");
		System.out.println(strDivEventCode);
		String strSplitEventCode = jsonObject.getString("eventCodeSplit");
		System.out.println(strSplitEventCode);
		String strIdChabgeEventCode = jsonObject.getString("eventCodeIdChange");
		System.out.println(strIdChabgeEventCode);
		
		String strFromDate = jsonObject.getString("effectiveDateFrom");
		System.out.println(strFromDate);
		String strToDate = "";
//		if(jsonObject.getString("effectiveDateTo") != null)
//			strToDate = jsonObject.getString("effectiveDateTo");
//		else
		strToDate = strFromDate; 
		System.out.println(strToDate);
		
		return getCAList(strDivEventCode,strSplitEventCode,strIdChabgeEventCode,strFromDate,strToDate);
	}	
	
	
	@POST
	@Path("/updateCA")
	public Response updateCorporateActions(JsonObject  jsonObject)
	{
		String strMsg = "";
		System.out.println("in updateCorporateActions");
		try
		{
			JsonArray  caJSONArray = jsonObject.getJsonArray("CorporateActions");
			int arraySize = caJSONArray.size();
			System.out.println("Total CorporateActions:::: " + arraySize);
			validateData(caJSONArray);
			
			updateCorpActions(caJSONArray);
			strMsg = "SUCCESS";
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			strMsg = e.toString();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			strMsg = e.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			strMsg = e.toString();
		}
		System.out.println(strMsg);
		ResponseBuilder rb = Response.ok(strMsg);
	    return rb.build();
	}
	
	@POST
	@Path("/addNewCA")
	public Response AddNewCorporateActions(File file)
	{
		return addNewCorporateActions(file);
	}
	
	private Response addNewCorporateActions(File file)
	{
		System.out.println("inside addNewCorporateActions");
		String strMsg = "";
		try 
		{
			RestUtil.writeToFile(new FileInputStream(file),RestUtil.ADD_NEW_CA_FILE_PATH);
			CorporateActionsService caService = new CorporateActionsService();
			caService.importCorporateActionsDataFromCsv(RestUtil.ADD_NEW_CA_FILE_PATH);
			strMsg = "SUCCESS";
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			strMsg = e.toString();
		} catch (Exception e) {
			e.printStackTrace();
			strMsg = e.toString();
		}
		System.out.println(strMsg);
		ResponseBuilder rb = Response.ok(strMsg);
	    return rb.build();
	}
	
	private void validateData(JsonArray caJSONArray) throws Exception {
		int arraySize = caJSONArray.size();
		System.out.println("inside validateData");
		try
		{
			for(int i=0;i<arraySize;i++)
			{
				JsonObject caObj = caJSONArray.getJsonObject(i);
				System.out.println(caObj);
				
				String eventCode = caObj.getString("eventCode");
				if( eventCode.trim().equalsIgnoreCase("ORD_DIV") ||  eventCode.trim().equalsIgnoreCase("SPL_DIV"))
				{
					if(caObj.get("currency") == null ||caObj.get("grossAmount") == null )//|| caObj.get("netAmountEDI") == null)
						throw new Exception("Missing GrossAmount or Currency Value in Dividend corporate actions.");
					Double grossAmount =  caObj.getJsonNumber("grossAmount").doubleValue();//Float.parseFloat(caObj.getString("grossAmountEDI"));
					System.out.println(grossAmount);
					String currency = caObj.getString("currency");
					System.out.println(currency);
					
					if( grossAmount ==0 || currency == null || currency.trim().equalsIgnoreCase("") )
						throw new Exception("Missing GrossAmount or Currency Value ");
				}
				else if( eventCode.trim().equalsIgnoreCase("STOCK_SPLT") ||  eventCode.trim().equalsIgnoreCase("DVD_STOCK"))
				{
					if(caObj.get("ratio") == null )//|| caObj.get("netAmountEDI") == null)
						throw new Exception("Missing ratio in Stock/Split/Bonus corporate actions.");
					Double ratio =  caObj.getJsonNumber("ratio").doubleValue();
					System.out.println(ratio);
				}			
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Missing or invalid Value " + e.toString());
		}	
	}

	private void updateCorpActions(JsonArray  caJSONArray) throws Exception 
	{
		int arraySize = caJSONArray.size();
		for(int i=0;i<arraySize;i++)
		{
			Map<String,Object> keyValueMap = new HashMap<String,Object>();
			JsonObject caObj = caJSONArray.getJsonObject(i);
			
			Integer id =caObj.getInt("id");
			String flag = caObj.getString("flag");
			keyValueMap.put("id", id);
			keyValueMap.put("flag", flag);
			
			String eventCode = caObj.getString("eventCode");
			if( eventCode.trim().equalsIgnoreCase("ORD_DIV") ||  eventCode.trim().equalsIgnoreCase("SPL_DIV"))
			{
				Double grossAmount =  caObj.getJsonNumber("grossAmount").doubleValue();
				String currency = caObj.getString("currency");
				keyValueMap.put("grossAmount", grossAmount);
				keyValueMap.put("currency", currency);
				
			}
			else if( eventCode.trim().equalsIgnoreCase("STOCK_SPLT") ||  eventCode.trim().equalsIgnoreCase("DVD_STOCK"))
			{
				Double ratio =  caObj.getJsonNumber("ratio").doubleValue();
				keyValueMap.put("ratio", ratio);
			}
			
//			
//			CorporateActionsFinal caBean = new CorporateActionsFinal();
//			caBean.setId(id);
////			caBean.setNetAmount(netAmount);
//			caBean.setGrossAmount(grossAmount);
//			caBean.setCurrency(currency);
			
			System.out.println("Call CorporateActionsService to update");
			CorporateActionsService caService = new CorporateActionsService();
//			caService.updateCADate(caBean);
			
			
			caService.updateCAData(keyValueMap);
			
		}		
	}

//	private static Response getCAList(String strEventCode, String strFromDate, String strToDate)
//	{
//		try
//		{
////			String strFilter = "";
//			List<CorporateActionsFinal> iList = new ArrayList<CorporateActionsFinal>();
//			CorporateActionsService caService = new CorporateActionsService();
//			
//			try 
//			{
////				iList = caService.getAllCAByDateAndCode(strEventCode,strFromDate.replace('-',''),strToDate.replace('-',''));
//				iList = caService.getAllCAByDateAndCode(strEventCode,strFromDate.replaceAll("-", ""),strToDate.replaceAll("-", ""));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//			ResponseBuilder rb = Response.ok( iList);
//		    return rb.build();
//		} catch (Exception e)
//		{
//			e.printStackTrace();			
//			ResponseBuilder rb = Response.ok( e.toString());
//		    return rb.build();
//		}		
//	}
	
	private static Response getCAList(String strEventCode,String strSplitEventCode,String strIdChangeEventCode, String strFromDate, String strToDate)
	{
		try
		{
			Map<String,List<CorporateActionsFinal>> caMap =  new HashMap<String,List<CorporateActionsFinal>>();
			
//			String strFilter = "";
			List<CorporateActionsFinal> iList = new ArrayList<CorporateActionsFinal>();
			CorporateActionsService caService = new CorporateActionsService();
			
			try 
			{
//				iList = caService.getAllCAByDateAndCode(strEventCode,strFromDate.replace('-',''),strToDate.replace('-',''));
				iList = caService.getAllCAByDateAndCode(strEventCode,strFromDate.replaceAll("-", ""),strToDate.replaceAll("-", ""));
				caMap.put("DIV", iList);
				iList = caService.getAllCAByDateAndCode(strSplitEventCode,strFromDate.replaceAll("-", ""),strToDate.replaceAll("-", ""));
				caMap.put("SPLIT", iList);
				iList = caService.getAllCAByDateAndCode(strIdChangeEventCode,strFromDate.replaceAll("-", ""),strToDate.replaceAll("-", ""));
				caMap.put("IDCHANGE", iList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ResponseBuilder rb = Response.ok( caMap);
		    return rb.build();
		} catch (Exception e)
		{
			e.printStackTrace();			
			ResponseBuilder rb = Response.ok( e.toString());
		    return rb.build();
		}		
	}
	
	
//	public static void main(String[] args) 
//	{
////		String str = effectiveDateFrom":"2018-10-22","eventCode":"DIV"
//		getCAList("DIV","2018-10-22","2018-10-19");
//		CorporateActionsService caService = new CorporateActionsService();
//		try {
//			caService.importCorporateActionsDataFromCsv(RestUtil.ADD_NEW_CA_FILE_PATH);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		strMsg = "SUCCESS";
//
//	}
}
