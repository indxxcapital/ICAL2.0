package com.test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.DataService.ConfigUtil;
import com.ICal2.Scheduler.Jobs.ClosingJobAPR;
import com.ICal2.Scheduler.Jobs.ClosingJobAR;
import com.ICal2.Scheduler.Jobs.ClosingJobER;
import com.ICal2.Scheduler.Jobs.OpeningJobAPR;
import com.ICal2.Scheduler.Jobs.OpeningJobAR;
import com.ICal2.Scheduler.Jobs.OpeningJobER;
import com.Service.ConfigService;

public class ListenerTaskExample implements ServletContextListener 
{
    private Thread t1 = null;
    private ServletContext context;

	private int APR_OPENING_HOUR;
	private int APR_OPENING_MINUTE;
	private int AR_OPENING_HOUR;
	private int AR_OPENING_MINUTE;
	private int ER_OPENING_HOUR;
	private int ER_OPENING_MINUTE;
	private int APR_CLOSING_HOUR;
	private int APR_CLOSING_MINUTE;
	private int AR_CLOSING_HOUR;
	private int AR_CLOSING_MINUTE;
	private int ER_CLOSING_HOUR;
	private int ER_CLOSING_MINUTE;

    public void contextInitialized(ServletContextEvent contextEvent)
    {
        t1 =  new Thread(){
            //task
            public void run(){                
                try {
                    while(true){
                        System.out.println("Thread 1 running every minute::::" + new Date());
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException e) {}
            }            
        };
        t1.start();

		readConfig();
		// Job Opening For APR Zone 
		fileJobForAPRZone();
		
		// Job Opening For ER Zone 
		fileJobForERZone();
		
		// Job Opening For AR Zone 
		fileJobForARZone();
		
        context = contextEvent.getServletContext();
        // you can set a context variable just like this
        context.setAttribute("TEST", "TEST_VALUE");
    }
    
    private void fileJobForARZone() 
    {
    	try 
		{
    		// Opening For AR Zone 
			JobDetail job3 = JobBuilder.newJob(OpeningJobAR.class).withIdentity("Job3", "group3").build();
			Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("cronTrigger3", "group3").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(AR_OPENING_HOUR, AR_OPENING_MINUTE)).build();
		
			Scheduler scheduler3 = new StdSchedulerFactory().getScheduler();
			scheduler3.start();
			scheduler3.scheduleJob(job3, trigger3);		
			
			
			// Closing For AR Zone 
			JobDetail job6 = JobBuilder.newJob(ClosingJobAR.class).withIdentity("Job6", "group6").build();
			Trigger trigger6 = TriggerBuilder.newTrigger().withIdentity("cronTrigger6", "group6").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(AR_CLOSING_HOUR, AR_CLOSING_MINUTE)).build();
		
			Scheduler scheduler6 = new StdSchedulerFactory().getScheduler();
			scheduler6.start();
			scheduler6.scheduleJob(job6, trigger6);		
		} 
		catch (SchedulerException e) 
		{
			e.printStackTrace();
		}
	}

	private void fileJobForERZone()
    {
    	try 
		{
	    	// Opening For ER Zone 
			JobDetail job2 = JobBuilder.newJob(OpeningJobER.class).withIdentity("Job2", "group2").build();
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("cronTrigger2", "group2").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(ER_OPENING_HOUR,ER_OPENING_MINUTE )).build();
		
			Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
			scheduler2.start();
			scheduler2.scheduleJob(job2, trigger2);
			
			// Closing For ER Zone 
			JobDetail job5 = JobBuilder.newJob(ClosingJobER.class).withIdentity("Job5", "group5").build();
			Trigger trigger5 = TriggerBuilder.newTrigger().withIdentity("cronTrigger5", "group5").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(ER_CLOSING_HOUR, ER_CLOSING_MINUTE)).build();
		
			Scheduler scheduler5 = new StdSchedulerFactory().getScheduler();
			scheduler5.start();
			scheduler5.scheduleJob(job5, trigger5);
		} 
		catch (SchedulerException e) 
		{
			e.printStackTrace();
		}
	}

	private void fileJobForAPRZone() 
    {
    	try 
		{
    		// Opening For APR Zone
    		JobDetail job1 = JobBuilder.newJob(OpeningJobAPR.class).withIdentity("Job1", "group1").build();
    		Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(APR_OPENING_HOUR, APR_OPENING_MINUTE)).build();
		
    		Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);	
			
			// Closing For APR Zone 
			JobDetail job4 = JobBuilder.newJob(ClosingJobAPR.class).withIdentity("Job4", "group4").build();
			Trigger trigger4 = TriggerBuilder.newTrigger().withIdentity("cronTrigger4", "group4").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(APR_CLOSING_HOUR, APR_CLOSING_MINUTE)).build();
		
			Scheduler scheduler4 = new StdSchedulerFactory().getScheduler();
			scheduler4.start();
			scheduler4.scheduleJob(job4, trigger4);
		} 
		catch (SchedulerException e) 
		{
			e.printStackTrace();
		}
	}

	private void readConfig() 
    {
		Map<String,String> propertiesMap = new HashMap<String,String>();
		ConfigService configService = new ConfigService();
		propertiesMap = configService.getConfigValues();		
		ConfigUtil.propertiesMap.get("databaseUrl");

		APR_OPENING_HOUR 		= Integer.parseInt(ConfigUtil.propertiesMap.get("APR_OPENING_HOUR"));
		APR_OPENING_MINUTE 		= Integer.parseInt(ConfigUtil.propertiesMap.get("APR_OPENING_MINUTE"));
		AR_OPENING_HOUR			= Integer.parseInt(ConfigUtil.propertiesMap.get("AR_OPENING_HOUR"));
		AR_OPENING_MINUTE		= Integer.parseInt(ConfigUtil.propertiesMap.get("AR_OPENING_MINUTE"));
		ER_OPENING_HOUR			= Integer.parseInt(ConfigUtil.propertiesMap.get("ER_OPENING_HOUR"));
		ER_OPENING_MINUTE 		= Integer.parseInt(ConfigUtil.propertiesMap.get("ER_OPENING_MINUTE"));
		APR_CLOSING_HOUR 		= Integer.parseInt(ConfigUtil.propertiesMap.get("APR_CLOSING_HOUR"));
		APR_CLOSING_MINUTE 		= Integer.parseInt(ConfigUtil.propertiesMap.get("APR_CLOSING_MINUTE"));
		AR_CLOSING_HOUR 		= Integer.parseInt(ConfigUtil.propertiesMap.get("AR_CLOSING_HOUR"));
		AR_CLOSING_MINUTE 		= Integer.parseInt(ConfigUtil.propertiesMap.get("AR_CLOSING_MINUTE"));
		ER_CLOSING_HOUR 		= Integer.parseInt(ConfigUtil.propertiesMap.get("ER_CLOSING_HOUR"));
		ER_CLOSING_MINUTE 		= Integer.parseInt(ConfigUtil.propertiesMap.get("ER_CLOSING_MINUTE"));
	}
//    
    public void contextDestroyed(ServletContextEvent contextEvent) {
        // context is destroyed interrupts the thread
        t1.interrupt();
//        t2.interrupt();
    }            
}