package com.icalrest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.Bean.IndexBean;
import com.Service.IndexService;
import com.Service.SecurityService;

@Path("/index") 
public class indexRestService {
	
	@POST
	@Path("/add")
	public Response addIndex(File file) 
	{
		return addMultipleIndex(file,true);
	}
	
	@POST
	@Path("/add2")
	public Response addIndexM(File file) throws Exception
	{
		return addMultipleIndex(file,false);
	}

	@POST
	@Path("/addIndex")
	public Response addSingleIndex(JsonObject  jsonObject) throws Exception
	{
		return addSingleIndex(true,jsonObject);
	}
	
	@POST
	@Path("/addIndex2")
	public Response addSingleIndexM(JsonObject  jsonObject) throws Exception
	{
		return addSingleIndex(false,jsonObject);
	}
	
	@POST
	@Path("/map")
	public Response mapSecurityies(File file)
	{
		String strMsg = "";
		System.out.println("in map index");
		try
		{
			RestUtil.writeToFile(new FileInputStream(file),RestUtil.SECURITY_MAP_FILE_PATH);
			SecurityService sService = new SecurityService();
			sService.mapSecurityWithIndex(RestUtil.SECURITY_MAP_FILE_PATH);
			strMsg = "SUCCESS";
		} catch (Exception e)
		{
			e.printStackTrace();
			strMsg = e.toString();
		}	
		ResponseBuilder rb = Response.ok(strMsg);
	    return rb.build();		
	}
	
	@POST
	@Path("/deleteindex")
	public void deleteIndex(JsonObject  jsonObject)
	{
		System.out.println("in deleteindex");
		
		JsonArray  indexJSONArray = jsonObject.getJsonArray("indexData");
		int arraySize = indexJSONArray.size();
		List<Integer> strIndexTickers = new ArrayList<>();
		for(int i=0;i<arraySize;i++)
		{
			JsonObject indexObj = indexJSONArray.getJsonObject(i);
			int indexTicker = indexObj.getInt("id");
			strIndexTickers.add(indexTicker);
		}
		
		IndexService iService = new IndexService();
		try 
		{
			String strIndexList = strIndexTickers.toString();
			System.out.println(strIndexList);
			iService.deleteIndex(strIndexList.substring(1, strIndexList.length()-1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/getnewindex")
	public Response getNewIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in getnewindex");
		System.out.println(jsonObject);
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		
		return getIndexList("'NI'",strWeightType);
	}	
	
	@POST
	@Path("/getupcomongindex")
	public Response getUpcomingIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in getUpcomingIndex");
		System.out.println(jsonObject);
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		
		return getIndexList("'UI'",strWeightType);
	}	
	
	@POST
	@Path("/getrunindex")
	public Response getRunIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in getRunIndex");
		System.out.println(jsonObject);
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		
		return getIndexList("'RI','AI'",strWeightType);
	}	
	
	@POST
	@Path("/getliveindex")
	public Response getLiveIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in add index");
		System.out.println(jsonObject);
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		
		return getIndexList("'LI'",strWeightType);
	}	
	
	@POST
	@Path("/updateIndicesStatus")
	public void updateIndicesStatus(JsonObject  jsonObject)
	{
		System.out.println("in updateIndexStatus");
		JsonArray  indexJSONArray = jsonObject.getJsonArray("indexTicker");
		String indexStatus = jsonObject.getString("indexStatus");
		int arraySize = indexJSONArray.size();
		List<Integer> strIndexTickers = new ArrayList<>();
		for(int i=0;i<arraySize;i++)
		{
			JsonObject indexObj = indexJSONArray.getJsonObject(i);
			int indexTicker = indexObj.getInt("id");
			strIndexTickers.add(indexTicker);
		}
		IndexService iService = new IndexService();
		try 
		{
			String strIndexList = strIndexTickers.toString();
			System.out.println(strIndexList);
			iService.updateIndicesStatus(strIndexList.substring(1, strIndexList.length()-1),indexStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@POST
	@Path("/deleteSecurityFromIndex")
	public void deleteSecurityFromIndex(JsonObject  jsonObject)
	{
		System.out.println("in deleteSecurityFromIndex");
		String indexTicker = jsonObject.getString("indexTicker");
		String securityId = jsonObject.getString("securityId");
		IndexService iService = new IndexService();
		try
		{
			iService.deleteSecurityFromIndex(indexTicker,securityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(indexTicker);
	}
	
	private Response addMultipleIndex(File file,boolean isProprietaryWeightedIndices)
	{
		String strMsg = "";
		System.out.println("in add index");
		try
		{
			InputStream uploadedInputStream = new FileInputStream(file);
			RestUtil.writeToFile(uploadedInputStream,RestUtil.INDEX_INPUT_FILE_PATH);
			uploadedInputStream.close();
			IndexService iService = new IndexService();
			iService.importIndexDataFromCsv(RestUtil.INDEX_INPUT_FILE_PATH,isProprietaryWeightedIndices);
//			strMsg = "Succesfully added Indices Details";
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
	
	private Response addSingleIndex(boolean isProprietaryWeightedIndices,JsonObject  jsonObject)
	{
		String strMsg = "";
		System.out.println("in side addIndex");
		try 
		{
			LocalDate localDate = LocalDate.now();
			String strDate = DateTimeFormatter.ofPattern("YYYY-MM-dd").format(localDate);
			
			Map<String,String>  columnsNameMap = new HashMap <String,String>();
			columnsNameMap.put("IndexName", "indexName");
			columnsNameMap.put("ClientName", "clientName");
			columnsNameMap.put("IndexType", "indexType");
			columnsNameMap.put("IndexTicker", "indexTicker");
			columnsNameMap.put("NCashDivAdj", "normalCashDivAdj");
			columnsNameMap.put("SNCashDivAdj", "specialCashDivAdj");
			columnsNameMap.put("Zone", "zoneType");
			columnsNameMap.put("Currency", "currency");
			columnsNameMap.put("IndexMarketValue", "IndexMarketValue");
			columnsNameMap.put("IndexValue", "CloseIndexValue");
			columnsNameMap.put("DisseminationSource", "disseminationSource");
	//		columnsNameMap.put("OutputFileFormat", "OutputFileFormat");
			columnsNameMap.put("IndexLiveDate", "indexLiveDate");
			columnsNameMap.put("weightType", "indexWeightType");
			
			Map<String,Object>  columnsValuesMap = new HashMap <String,Object>();
			System.out.println(jsonObject);
			Iterator<String> it = columnsNameMap.keySet().iterator();
	        
	        while (it.hasNext())
	        {
	            String key = it.next();
	            System.out.println(key);
	            String strDbColumnName = columnsNameMap.get(key);
	            System.out.println(strDbColumnName);
	            String strDbColumnValue = jsonObject.getString(key);
	            System.out.println(strDbColumnValue);
	            System.out.println(strDbColumnName + "::::::::::" + strDbColumnValue);
	            columnsValuesMap.put(strDbColumnName, strDbColumnValue);
	            
	        }
	        columnsValuesMap.put("status", "NI");
	        if(isProprietaryWeightedIndices)
	        	columnsValuesMap.put("indexWeightType", "PWI");
			else
				columnsValuesMap.put("indexWeightType", "MWI");
	        columnsValuesMap.put("indexWeightType", "PWI");
	        columnsValuesMap.put("flag","1");
	        columnsValuesMap.put("vf", strDate);
	        columnsValuesMap.put("vt","9999-12-31");
			
	        System.out.println(columnsValuesMap);
			
	        IndexService iService = new IndexService();
		
			iService.insertIndexData(columnsValuesMap);
			strMsg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			strMsg = e.toString();
		}		
		System.out.println(strMsg);
		ResponseBuilder rb = Response.ok(strMsg);
	    return rb.build();
	}
	
	private Response getIndexList(String status,String weightType)
	{
		String strFilter = "";
		List<IndexBean> iList = new ArrayList<IndexBean>();
		IndexService iService = new IndexService();
//		if(!weightType.equalsIgnoreCase(""))
//			strFilter ="Where status ='" + status + "' and indexWeightType = '" + weightType + "'";
//		else
//			strFilter ="Where status ='" + status + "'";
		
		if(!weightType.equalsIgnoreCase(""))
			strFilter ="Where status In (" + status + ")  and indexWeightType = '" + weightType + "'";
		else
			strFilter ="Where status In (" + status + ")";
		
		
		try 
		{
			iList = iService.getAllIndex(strFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ResponseBuilder rb = Response.ok( iList);
	    return rb.build();
	}

//	public static void main(String[] args) 
//	{
//		System.out.println("HELLO REST UTIL ");
//		String strFilter ="Where status ='NI'";
//		List<IndexBean> iList = new ArrayList<IndexBean>();
//		IndexService iService = new IndexService();
//		try {
//			iList = iService.getAllIndex(strFilter);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
}