package com.icalrest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.Service.ConfigService;

@Path("/Config")
public class ICalConfigRestService 
{
	@GET
	@Path("/readConfigData")
	public Response getAllConfigValues()
	{
		System.out.println("in getAllConfigValues");
		Map<String,String> propertiesMap = new HashMap<String,String>();
		ConfigService configService = new ConfigService();
		propertiesMap = configService.getConfigValues();
		ResponseBuilder rb = Response.ok( propertiesMap);
		System.out.println(rb);
	    return rb.build(); 
	}	
}
