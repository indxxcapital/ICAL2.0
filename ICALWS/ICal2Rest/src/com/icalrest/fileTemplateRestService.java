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
	
	String TEMPLATE_FILE_PATH = ConfigUtil.propertiesMap.get("TEMPLATE_FILE_PATH");
	String TEMPLATE_FILE_FORMAT = ConfigUtil.propertiesMap.get("TEMPLATE_FILE_FORMAT");
	
	@GET
	@Path("/getSecurityTemplate")//1
	@Produces("application/vnd.ms-excel")
	public Response getSecurityTemplate() 
	{
		String fileName = "SecurityCheckInputFile." +TEMPLATE_FILE_FORMAT;
		return getTemplate(fileName);
	}
	
	@GET
	@Path("/getSecurityAddTemplate")//2
	@Produces("application/vnd.ms-excel")
	public Response getSecurityAddTemplate() 
	{
		String fileName = "SecurityAddInputFile." +TEMPLATE_FILE_FORMAT;
		return getTemplate(fileName);
	}
	
	@GET
	@Path("/getIndexTemplate")//3
	@Produces("application/vnd.ms-excel")
	public Response getIndexTemplate()
	{
		String fileName = "IndexInputFile." +TEMPLATE_FILE_FORMAT;
		return getTemplate(fileName);
	}
	
	@GET
	@Path("/getMapSecuritiesTemplate")//4
	@Produces("application/vnd.ms-excel")
	public Response gettMapSecuritiesTemplate()
	{
		String fileName = "SecurityMapFile." +TEMPLATE_FILE_FORMAT;
		return getTemplate(fileName);
	}
	
	@GET
	@Path("/getSecurityPriceTemplate")//5
	@Produces("application/vnd.ms-excel")
	public Response getSecurityPriceTemplate() 
	{
		String fileName = "SecurityPrice." +TEMPLATE_FILE_FORMAT;
		return getTemplate(fileName);
	}	
	
	@GET
	@Path("/getCurrencyCheckTemplate")//6
	@Produces("application/vnd.ms-excel")
	public Response getCurrencyCheckTemplate() 
	{
		String fileName = "CurrencyInputFile." +TEMPLATE_FILE_FORMAT;
		return getTemplate(fileName);
	}	
	
	@GET
	@Path("/getCurrencyAddTemplate")//7
	@Produces("application/vnd.ms-excel")
	public Response getCurrencyAddTemplate()
	{
		String fileName = "CurrencyAddInputFile." +TEMPLATE_FILE_FORMAT;
		return getTemplate(fileName);
	}	
	
	private Response getTemplate(String fileName)
	{
		File file = new File(TEMPLATE_FILE_PATH + fileName );
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=" + fileName);
		return response.build();
	}
}