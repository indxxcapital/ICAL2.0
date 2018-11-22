package com.icalrest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.Bean.CurrencyBean;
import com.Service.CurrencyService;

@Path("/currency") 
public class currencyRestService {

	@POST
	@Path("/parseCurrency")
	public Response parseCurrency(File file)
	{
		return parseCurrencyList(file);
	}
	
	private Response parseCurrencyList(File file)
	{
		CurrencyService crryService = new CurrencyService();
		String strMsg = "";
		System.out.println("inside parseCurrency");
		System.out.println(file.getName());
	    try
	    {
	    	FileInputStream filereader = new FileInputStream(file);
	    	RestUtil.writeToFile(filereader,RestUtil.CURRENCY_INPUT_FILE_PATH);
	    	crryService.parseCurrencyFile(RestUtil.CURRENCY_INPUT_FILE_PATH,RestUtil.CURRENCY_OUT_FILE_PATH);
	    	filereader.close();
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
	
	@GET
	@Path("/get")
	@Produces("application/vnd.ms-excel")
	public Response getFile() {

		File file = new File(RestUtil.CURRENCY_OUT_FILE_PATH);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=" + "missing-currencies.csv");
		return response.build();
	}
	
	@POST
	@Path("/addNewCurrency")
	public void AddNewCurrency(File file)
	{
//		addNewCurrency(file);
	}
//	private void addNewCurrency(File file)
//	{
//		System.out.println("inside addNewCurrency");
//		// save it
//		try 
//		{
//			RestUtil.writeToFile(new FileInputStream(file),RestUtil.ADD_CURRENCY_FILE_PATH);
//		} 
//		catch (FileNotFoundException e) 
//		{
//			e.printStackTrace();
//		}
//		CurrencyService crryService = new CurrencyService();
//		crryService.importCurrencyDataFromCsv(RestUtil.ADD_CURRENCY_FILE_PATH);
//	}
	
	@GET
	@Path("/getCurrencies")
	public Response getAllCurrencies()
	{
		System.out.println("in getAllCurrencies");
		List<CurrencyBean> cList = new ArrayList<CurrencyBean>();
		CurrencyService crryService = new CurrencyService();
		try
		{
			cList = crryService.getAllCurrency();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseBuilder rb = Response.ok( cList);
		System.out.println(rb);
	    return rb.build(); 
	}	
}
