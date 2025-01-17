package com.icalrest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.Bean.ClientBean;
import com.Service.ClientService;

@Path("/client")
public class clientRestService
{
	@GET
	@Path("/getAllClients")
	public Response getAllClients()
	{
		System.out.println("in getAllClients");
		List<ClientBean> cList = new ArrayList<ClientBean>();
		ClientService cService = new ClientService();
		try
		{
			cList = cService.getAllClients();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseBuilder rb = Response.ok( cList);
		System.out.println(rb);
	    return rb.build(); 
	}	

}
