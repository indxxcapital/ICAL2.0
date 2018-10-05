package com.icalrest;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.Bean.IndexBean;
import com.Service.IndexService;

@Path("/index") 
public class fileGenerationRestService {
	
	@POST
	@Path("/getpreclosing")
	@Produces("application/vnd.ms-excel")
	public Response getPreOpenigFile(JsonObject  jsonObject) 
	{
		System.out.println("in getSecuritiesForIndex");
		System.out.println("json:"+jsonObject);
		
		String indexTicker = jsonObject.getString("indexTicker");
		String indexRunDate = jsonObject.getString("indexRunDate");
		
		System.out.println("Ticker:"+indexTicker);
		System.out.println("Run Date :"+indexRunDate);
		
		String fileName = "Pre-closing-" + indexTicker +"-"+ indexRunDate + ".csv" ;
		String fileCompletePath = RestUtil.INDEX_PRE_OPEN_FILE_PATH +fileName;

		File file = new File(fileCompletePath);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=" + fileName);
		return response.build();
	}
	public static void main(String[] args) 
	{
		System.out.println("HELLO REST UTIL ");
		getPreClosingFiles();
	}
//	
	public static void getPreClosingFiles()
	{
		List<IndexBean> indexBeanList = new ArrayList();
		
		try 
		{
			System.out.println("in getPreClosingFiles");
			
			LocalDate localDate = LocalDate.now();
			String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
			
			IndexService iService = new IndexService();
			String indexRunDate = "2018-10-03";//jsonObject.getString("indexRunDate");
			
			List<Integer> strIndexTickers = new ArrayList<>();
//			for(int i=0;i<arraySize;i++)
			{
				IndexBean iBean = new IndexBean();
				Integer indexId = 28;//indexObj.getInt("id");
				strIndexTickers.add(indexId);
				String indexTicker = "T5";//indexObj.getString("indexTicker");
				String indexCurrency = "USD";//indexObj.getString("currency");
//				System.out.println("12222233333111");
				iBean.setId(indexId);
//				System.out.println("444");
				iBean.setIndexTicker(indexTicker);
//				System.out.println("555");
				iBean.setCurrency(indexCurrency);
//				System.out.println("666");
				Float indexValue = (float) 617.5639049;//indexObj.getInt("closeIndexValue");
				Float  marketValue = (float) 61756.4;//indexObj.getInt("indexMarketValue");
				System.out.println(indexValue);
//				System.out.println("777");
				iBean.setCloseIndexValue(Float.parseFloat(indexValue.toString()));	
//				System.out.println("888");
				iBean.setIndexMarketValue(Float.parseFloat(marketValue.toString()));
				iBean.setIndexRunDate(indexRunDate);
//				String fileName = "Pre-closing-" + indexTicker +"-"+ strDate + "-" + indexRunDate + ".csv" ;
				String fileName = "Pre-closing-" + indexTicker +"-"+ indexRunDate + ".csv" ;
				String fileCompletePath = RestUtil.INDEX_PRE_OPEN_FILE_PATH +fileName;
				System.out.println(fileCompletePath);
					
				iService.generatePreClosingile(iBean,fileCompletePath);
				indexBeanList.add(iBean);
			}
			String strIndexList = strIndexTickers.toString();
			iService.updateIndicesStatusAsRun(strIndexList.substring(1, strIndexList.length()-1),"RI",indexRunDate);
			
			
			LocalDateTime now = LocalDateTime.now();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

			String formatDateTime = now.format(formatter);
		        
			String fileName = "composite-pre-closing-(" + formatDateTime +")-"+ indexRunDate + ".csv" ;
			String fileCompletePath = RestUtil.INDEX_PRE_OPEN_FILE_PATH +fileName;
			
			iService.generateCompositePreClosingFile(indexBeanList,fileCompletePath);
		}catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
//	
	
	@POST
	@Path("/runIndices")
	@Produces("application/vnd.ms-excel")
	public void generatePreOpeningFiles(JsonObject  jsonObject)
	{
		List<IndexBean> indexBeanList = new ArrayList();
		
		try 
		{
			System.out.println("in getPreClosingFiles");
			System.out.println(jsonObject);
			
//			LocalDate localDate = LocalDate.now();
//			String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
			
			IndexService iService = new IndexService();
			JsonArray  indexJSONArray = jsonObject.getJsonArray("indexData");
			System.out.println(indexJSONArray);
			String indexRunDate = jsonObject.getString("indexRunDate");
			
			int arraySize = indexJSONArray.size();
			List<Integer> strIndexTickers = new ArrayList<>();
			for(int i=0;i<arraySize;i++)
			{
				IndexBean iBean = new IndexBean();
				JsonObject indexObj = indexJSONArray.getJsonObject(i);
				Integer indexId = indexObj.getInt("id");
				strIndexTickers.add(indexId);
				String indexTicker = indexObj.getString("indexTicker");
				String indexCurrency = indexObj.getString("currency");
//				System.out.println("12222233333111");
				iBean.setId(indexId);
//				System.out.println("444");
				iBean.setIndexTicker(indexTicker);
//				System.out.println("555");
				iBean.setCurrency(indexCurrency);
//				System.out.println("666");
				Integer indexValue = indexObj.getInt("closeIndexValue");
				Integer marketValue = indexObj.getInt("indexMarketValue");
				System.out.println(indexValue);
//				System.out.println("777");
				iBean.setCloseIndexValue(Float.parseFloat(indexValue.toString()));	
//				System.out.println("888");
				iBean.setIndexMarketValue(Float.parseFloat(marketValue.toString()));
				iBean.setIndexRunDate(indexRunDate);
//				String fileName = "Pre-closing-" + indexTicker +"-"+ strDate + "-" + indexRunDate + ".csv" ;
				String fileName = "Pre-closing-" + indexTicker +"-"+ indexRunDate + ".csv" ;
				String fileCompletePath = RestUtil.INDEX_PRE_OPEN_FILE_PATH +fileName;
				System.out.println(fileCompletePath);
					
				iService.generatePreClosingile(iBean,fileCompletePath);
				indexBeanList.add(iBean);
			}
			String strIndexList = strIndexTickers.toString();
			iService.updateIndicesStatusAsRun(strIndexList.substring(1, strIndexList.length()-1),"RI",indexRunDate);
			
			LocalDateTime now = LocalDateTime.now();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

			String formatDateTime = now.format(formatter);
		        
			String fileName = "composite-pre-closing-(" + formatDateTime +")-"+ indexRunDate + ".csv" ;
			String fileCompletePath = RestUtil.INDEX_PRE_OPEN_FILE_PATH +fileName;
			
			iService.generateCompositePreClosingFile(indexBeanList,fileCompletePath);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
		