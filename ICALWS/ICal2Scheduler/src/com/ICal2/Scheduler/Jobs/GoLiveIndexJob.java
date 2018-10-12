package com.ICal2.Scheduler.Jobs;

import java.sql.SQLException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.Service.IndexService;

public class GoLiveIndexJob implements Job{ 

	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		System.out.println("Generation Closing Files for ER Zone --->>> Time is " + new Date());
		try
		{
			goLive();
		} catch (ClassNotFoundException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void goLive() throws ClassNotFoundException, SQLException
	{
		IndexService iservice = new IndexService();
		try 
		{
			iservice.goLiveIndex();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw(e);
		}
	} 
}
