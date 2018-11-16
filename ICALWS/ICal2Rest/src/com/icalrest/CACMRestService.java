package com.icalrest;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.websocket.server.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.Bean.IndexBean;
import com.Service.CACMExcelService;
import com.Service.IndexService;
import com.sun.research.ws.wadl.Request;

@Path("/CACM")
public class CACMRestService 
{

	public static void main(String[] args) 
	{
		CACMExcelService cacmService = new CACMExcelService();
		String CACMDate = "20181105";
		try {
			cacmService.generateCACM(CACMDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@POST
	@Path("/CACMDownload")
	@Produces("application/vnd.ms-excel")
	public Response generateCACMFiles(JsonObject  jsonObject)
	{
		String strMsg = "";
		try 
		{
			System.out.println("in generateCACMFiles");
			System.out.println(jsonObject);
			
			CACMExcelService cacmService = new CACMExcelService();
			String CACMDate = jsonObject.getString("effectiveDateFrom");
			cacmService.generateCACM(CACMDate.replaceAll("-", ""));
		        
//			String fileName = "CACM-" + CACMDate.replaceAll("-", "").trim() + ".xlsx" ;
////			String fileName = "Pre-opening-" + indexTicker +"-"+ indexRunDate + ".csv" ;
//			String fileCompletePath = RestUtil.CACM_FILE_PATH +fileName;
//
//			File file = new File(fileCompletePath);
//			ResponseBuilder response = Response.ok((Object) file);
//			response.header("Content-Disposition","attachment; filename=" + fileName);
//			return response.build();
			
			strMsg = "SUCCESS";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
//			strMsg = e.toString();
		}
		System.out.println(strMsg);
		ResponseBuilder rb = Response.ok(strMsg);
	    return rb.build();
//		return null;
	}
	
	
//	
//	@GET
//	@Path("/CACMFILE")
//	@Produces("application/vnd.ms-excel")
//	public Response getPreOpenigFile(JsonObject  jsonObject) 
//	{
//		System.out.println("in getSecuritiesForIndex");
//		System.out.println("json:"+jsonObject);
//		
////		String indexTicker = jsonObject.getString("indexTicker");
////		String indexRunDate = jsonObject.getString("indexRunDate");
////		
////		System.out.println("Ticker:"+indexTicker);
////		System.out.println("Run Date :"+indexRunDate);
//		
////		String fileName = "Pre-opening-" + indexTicker +"-"+ indexRunDate + ".csv" ;
////		String fileName = "input-file.csv";
//		String fileName = "CACM-20181105.xls";
//		String fileCompletePath = RestUtil.CACM_FILE_PATH +fileName;
//
//		File file = new File(fileCompletePath);
//		ResponseBuilder response = Response.ok((Object) file);
//		response.header("Content-Disposition","attachment; filename=" + fileName);
//		return response.build();
//	}
	
	@GET
	@Path("/CACM")//1
	@Produces("application/vnd.ms-excel")
	public Response CACM(JsonObject  jsonObject) 
	{
//		String fileName = "SecurityCheckInputFile." +TEMPLATE_FILE_FORMAT;
		System.out.println("json:"+jsonObject);
		String fileName = "CACM-20181105.xls";
		return getTemplate(fileName);
	}	
	
	
	@GET
	@Path("/getCACM")//1
	@Produces("application/vnd.ms-excel")
	public Response getCACM() 
	{
//		String fileName = "SecurityCheckInputFile." +TEMPLATE_FILE_FORMAT;
//		System.out.println("json:"+jsonObject);
		String fileName = "CACM.xls";
		return getTemplate(fileName);
	}	
	
	private Response getTemplate(String fileName)
	{
		File file = new File(RestUtil.CACM_FILE_PATH + fileName );
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=" + fileName);
		return response.build();
	}
	
}
