package com.icalrest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.Bean.SecurityBean;
import com.Service.SecurityService;

@Path("/security") 
public class restService {
	
	@POST
	@Path("/parse")
	public Response parse(File file)
	{
		return parseSecurityList(file);
	}
	
	@POST
	@Path("/addnew")
	public Response AddNewSecurities(File file)
	{
		return addNewSecurities(file);
	}
	
	@POST
	@Path("/addprice")
	public Response AddSecurityPrice(File file)
	{
		return addSecuritiesPrice(file);
	}

	@GET
	@Path("/get")
	@Produces("application/vnd.ms-excel")
	public Response getFile() {

		File file = new File(RestUtil.OUT_FILE_PATH);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=missing-securities.csv");
		return response.build();
	}
	
	private Response parseSecurityList(File file)
	{
		System.out.println("inside parseSecurityList");
		SecurityService secService = new SecurityService();
		String strMsg = "";
		System.out.println(file.getName());
	    try
	    {
	    	RestUtil.writeToFile(new FileInputStream(file),RestUtil.INPUT_FILE_PATH);
			secService.parseSecurityFile(RestUtil.INPUT_FILE_PATH,RestUtil.OUT_FILE_PATH);
			strMsg = "SUCCESS";
		} 
	    catch (FileNotFoundException e) {
			e.printStackTrace();
			strMsg = e.toString();
		}
	    catch (IOException e) {
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
	
	private Response addNewSecurities(File file)
	{
		System.out.println("inside addNewSecurities");
		String strMsg = "";
		try 
		{
			RestUtil.writeToFile(new FileInputStream(file),RestUtil.ADD_SECURITY_FILE_PATH);
			SecurityService secService = new SecurityService();
			secService.importSecurityDataFromCsv(RestUtil.ADD_SECURITY_FILE_PATH);
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
	
	private Response addSecuritiesPrice(File file) 
	{
		System.out.println("inside addSecuritiesPrice");
		String strMsg = "";
		try 
		{
			RestUtil.writeToFile(new FileInputStream(file),RestUtil.ADD_SECURITY_PRICE_FILE_PATH);
			SecurityService secService = new SecurityService();
			secService.importSecurityPriceDataFromCsv(RestUtil.ADD_SECURITY_PRICE_FILE_PATH);
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
	
	@GET
	@Path("/getSecurities")
	public Response getAllSecurities()
	{
		System.out.println("in getSecurities");
		List<SecurityBean> sList = new ArrayList<SecurityBean>();
		SecurityService secService = new SecurityService();
		sList = secService.getAllSecurities("");
		ResponseBuilder rb = Response.ok( sList);
	    return rb.build(); 
	}	
	
	@POST
	@Path("/getSecuritiesForIndex")
	public Response getIndexSecurities(JsonObject  jsonArray)
	{
		System.out.println("in getSecuritiesForIndex");
		System.out.println("json:"+jsonArray);
		
		String indexTicker = jsonArray.getString("indexTicker");
		
		System.out.println("Ticker:"+indexTicker);
		
		List<SecurityBean> sList = new ArrayList<SecurityBean>();
		SecurityService secService = new SecurityService();
		
		try {
			sList = secService.getAllSecuritiesForIndex(indexTicker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseBuilder rb = Response.ok( sList);
	    return rb.build(); 
	}	
	
	@POST
	@Path("/deleteSecurityFromIndex")
	public void deleteIndexSecurity(JsonObject  jsonArray)
	{
		System.out.println("in deleteSecurityFromIndex");
		String indexTicker = jsonArray.getString("indexTicker");
		String isin = jsonArray.getString("ISIN");
		String bbgTicker = jsonArray.getString("BBGTicker");
		
		SecurityBean sBean = new SecurityBean();
		sBean.setIndexCode(indexTicker);
		sBean.setISIN(isin);
		sBean.setBBGTicker(bbgTicker);
		
		SecurityService secService = new SecurityService();
		try {
			secService.deleteSecurityFromIndex(sBean);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
}