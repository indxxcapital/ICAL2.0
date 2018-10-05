package com.icalrest;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.DataService.ConfigUtil;

@Path("/template") 
public class fileTemplateRestService {
	
	public static final String TEMPLATE_FILE_PATH = ConfigUtil.propertiesMap.get("TEMPLATE_FILE_PATH");//"c://temp/template/";
	
	@GET
	@Path("/getSecurityTemplate")//1
	@Produces("application/vnd.ms-excel")
	public Response getSecurityTemplate() 
	{

		File file = new File(TEMPLATE_FILE_PATH+ "SecurityCheckInputFile.csv");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=SecurityCheckInputFile.csv");
		return response.build();
	}
	
	@GET
	@Path("/getSecurityAddTemplate")///2
	@Produces("application/vnd.ms-excel")
	public Response getSecurityAddTemplate() 
	{
		File file = new File(TEMPLATE_FILE_PATH+ "SecurityAddInputFile.csv");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=SecurityAddInputFile.csv");
		return response.build();
	}
	
	@GET
	@Path("/getIndexTemplate")//3
	@Produces("application/vnd.ms-excel")
	public Response getIndexTemplate() {

		File file = new File(TEMPLATE_FILE_PATH+ "IndexInputFile.csv");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=IndexInputFile.csv");
		return response.build();
	}
	
	@GET
	@Path("/getMapSecuritiesTemplate")//4
	@Produces("application/vnd.ms-excel")
	public Response gettMapSecuritiesTemplate() {

		File file = new File(TEMPLATE_FILE_PATH+ "SecurityMapFile.csv");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=SecurityMapFile.csv");
		return response.build();
	}
	
	@GET
	@Path("/getSecurityPriceTemplate")//5
	@Produces("application/vnd.ms-excel")
	public Response getSecurityPriceTemplate() {

		File file = new File(TEMPLATE_FILE_PATH +"SecurityPrice.csv");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=SecurityPrice.csv");
		return response.build();
	}	
	
	@GET
	@Path("/getCurrencyCheckTemplate")//6
	@Produces("application/vnd.ms-excel")
	public Response getCurrencyCheckTemplate() {

		File file = new File(TEMPLATE_FILE_PATH +"CurrencyInputFile.csv");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=CurrencyInputFile.csv");
		return response.build();
	}	
	
	@GET
	@Path("/getCurrencyAddTemplate")//7
	@Produces("application/vnd.ms-excel")
	public Response getCurrencyAddTemplate() {

		File file = new File(TEMPLATE_FILE_PATH +"CurrencyAddInputFile.csv");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=CurrencyAddInputFile.csv");
		return response.build();
	}	
}
