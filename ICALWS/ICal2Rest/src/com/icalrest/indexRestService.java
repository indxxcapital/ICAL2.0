package com.icalrest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.Bean.IndexBean;
import com.Service.IndexService;
import com.Service.SecurityService;

@Path("/index") 
public class indexRestService {
	
	@POST
	@Path("/add")
	public Response addIndex(File file) 
	{
		String strMsg = "";
		System.out.println("in add index");
		try
		{
			RestUtil.writeToFile(new FileInputStream(file),RestUtil.INDEX_INPUT_FILE_PATH);
			IndexService iService = new IndexService();
			iService.importIndexDataFromCsv(RestUtil.INDEX_INPUT_FILE_PATH,true);
			strMsg = "Succesfully added Indices Details";
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			strMsg = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			strMsg = e.getMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			strMsg = e.getMessage();
		}
		ResponseBuilder rb = Response.ok(strMsg);
	    return rb.build();
	}
	
	@POST
	@Path("/add2")
	public void addIndexM(File file) throws Exception
	{
		System.out.println("in add index");
		try
		{
			RestUtil.writeToFile(new FileInputStream(file),RestUtil.INDEX_INPUT_FILE_PATH);
			IndexService iService = new IndexService();
			iService.importIndexDataFromCsv(RestUtil.INDEX_INPUT_FILE_PATH,false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/addIndex")
	public void addSingleIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in side addIndex");
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
        columnsValuesMap.put("indexWeightType", "PWI");
        columnsValuesMap.put("flag","1");
        columnsValuesMap.put("vf", strDate);
        columnsValuesMap.put("vt","9999-12-31");
		
        System.out.println(columnsValuesMap);
		
        IndexService iService = new IndexService();
		try 
		{
			
			iService.insertIndexData(columnsValuesMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/addIndex2")
	public void addSingleIndexM(JsonObject  jsonObjecty) throws Exception
	{
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
		
		Map<String,String>  columnsNameMap = new HashMap <String,String>();
		columnsNameMap.put("IndexName", "indexName");
		columnsNameMap.put("ClientName", "clientName");
		columnsNameMap.put("IndexType", "indexType");
		columnsNameMap.put("IndexTicker", "indexTicker");
		columnsNameMap.put("NCashDivAdj", "normalCashDivAdj");
		columnsNameMap.put("SNCashDivAdj", "specialCashDivAdj");
		columnsNameMap.put("Zone", "zoneType");
		columnsNameMap.put("Currency", "currency");
		columnsNameMap.put("DisseminationSource", "disseminationSource");
		columnsNameMap.put("OutputFileFormat", "OutputFileFormat");
		columnsNameMap.put("IndexLiveDate", "indexLiveDate");
		columnsNameMap.put("weightType", "indexWeightType");
		Map<String,Object>  columnsValuesMap = new HashMap <String,Object>();
		System.out.println(jsonObjecty);
		Iterator<String> it = columnsNameMap.keySet().iterator();
        
        while (it.hasNext())
        {
            String key = it.next();
            String strDbColumnName = columnsNameMap.get(key);
            String strDbColumnValue = jsonObjecty.getString(key);
            columnsValuesMap.put(strDbColumnName, strDbColumnValue);
            
        }
        columnsValuesMap.put("status", "NI");
        columnsValuesMap.put("indexWeightType", "MWI");
        columnsValuesMap.put("flag","1");
        columnsValuesMap.put("vf", strDate);
        columnsValuesMap.put("vt","9999-12-31");
        
        System.out.println(columnsValuesMap);
		
        IndexService iService = new IndexService();
		try {
			iService.insertIndexData(columnsValuesMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/map")
	public void mapSecurityies(File file)
	{
		System.out.println("in map index");
		try
		{
			RestUtil.writeToFile(new FileInputStream(file),RestUtil.SECURITY_MAP_FILE_PATH);
			SecurityService sService = new SecurityService();
			sService.mapSecurityWithIndex(RestUtil.SECURITY_MAP_FILE_PATH);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
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
	
	public static void main(String[] args) 
	{
		System.out.println("HELLO REST UTIL ");
		String strFilter ="Where status ='NI'";
		List<IndexBean> iList = new ArrayList<IndexBean>();
		IndexService iService = new IndexService();
		try {
			iList = iService.getAllIndex(strFilter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@POST
	@Path("/getnewindex")
	public Response getNewIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in getnewindex");
		System.out.println(jsonObject);
		
		List<IndexBean> iList = new ArrayList<IndexBean>();
		IndexService iService = new IndexService();
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		String strFilter = "";
		if(!strWeightType.equalsIgnoreCase(""))
			strFilter ="Where status ='NI' and indexWeightType = '" + strWeightType + "'";
		else
			strFilter ="Where status ='NI'";
		
		iList = iService.getAllIndex(strFilter);
		
		ResponseBuilder rb = Response.ok( iList);
	    return rb.build();
	}	
	
	@POST
	@Path("/getupcomongindex")
	public Response getUpcomingIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in getUpcomingIndex");
		
		List<IndexBean> iList = new ArrayList<IndexBean>();
		IndexService iService = new IndexService();
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		String strFilter = "";
		if(!strWeightType.equalsIgnoreCase(""))
			strFilter ="Where status ='UI' and indexWeightType = '" + strWeightType + "'";
		else
			strFilter ="Where status ='UI'";
		
		iList = iService.getAllIndex(strFilter);
		
		ResponseBuilder rb = Response.ok( iList);
		System.out.println(iList);
	    return rb.build();
	}	
	
	@POST
	@Path("/getrunindex")
	public Response getRunIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in getRunIndex");
		
		List<IndexBean> iList = new ArrayList<IndexBean>();
		IndexService iService = new IndexService();
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		String strFilter = "";
		if(!strWeightType.equalsIgnoreCase(""))
			strFilter ="Where status ='RI' and indexWeightType = '" + strWeightType + "'";
		else
			strFilter ="Where status ='RI'";
		
		iList = iService.getAllIndex(strFilter);
		ResponseBuilder rb = Response.ok( iList);
		System.out.println(iList);
	    return rb.build();
	}	
	
	@POST
	@Path("/getliveindex")
	public Response getLiveIndex(JsonObject  jsonObject) throws Exception
	{
		System.out.println("in add index");
		
		List<IndexBean> iList = new ArrayList<IndexBean>();
		IndexService iService = new IndexService();
		
		String strWeightType = jsonObject.getString("weightType");
		System.out.println(strWeightType);
		String strFilter = "";
		if(!strWeightType.equalsIgnoreCase(""))
			strFilter ="Where status ='LI' and indexWeightType = '" + strWeightType + "'";
		else
			strFilter ="Where status ='LI'";
		
		iList = iService.getAllIndex(strFilter);
		ResponseBuilder rb = Response.ok( iList);
	    return rb.build(); 
	}	
		
	@POST
	@Path("/addIndexValues")
	public void addIndexValues(JsonArray  jsonArray)
	{
		System.out.println("in addIndexValues");
		System.out.println(jsonArray);
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
		Map<String,String>  columnsNameMap = new HashMap <String,String>();
		Map<String,Object>  columnsValuesMap = new HashMap <String,Object>();
		
		columnsNameMap.put("indexTicker", "indexTicker");
		columnsNameMap.put("indexMarketValue", "IndexMarketValue");
		columnsNameMap.put("closeIndexValue", "CloseIndexValue");
		
		
		int arraySize = jsonArray.size();
		for(int i=0;i<arraySize;i++)
		{
			
			JsonObject indexObj = jsonArray.getJsonObject(i);
			System.out.println(indexObj);
			Iterator<String> it = columnsNameMap.keySet().iterator();
			while (it.hasNext())
	        {
	            String key = it.next();
	            String strDbColumnName = columnsNameMap.get(key);
	            String strDbColumnValue = indexObj.getString(key);
	            columnsValuesMap.put(strDbColumnName, strDbColumnValue);
	        }
			columnsValuesMap.put("IndexValuesAddedOn", strDate);
			IndexService iService = new IndexService();
			try 
			{
				iService.updateIndexValues(columnsValuesMap);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(strIndexTickers.toString());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(indexTicker);
	}
}