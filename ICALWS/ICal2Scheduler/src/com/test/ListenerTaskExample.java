package com.test;
import java.util.Date;
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

import com.ICal2.Scheduler.Jobs.ClosingJobAPR;
import com.ICal2.Scheduler.Jobs.ClosingJobAR;
import com.ICal2.Scheduler.Jobs.ClosingJobER;
import com.ICal2.Scheduler.Jobs.OpeningJobAPR;
import com.ICal2.Scheduler.Jobs.OpeningJobAR;
import com.ICal2.Scheduler.Jobs.OpeningJobER;

public class ListenerTaskExample implements ServletContextListener {
    private Thread t1 = null;
//    private Thread t2 = null;
    private ServletContext context;
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
       
		
		try 
		{
			// Opening For APR Zone 
			JobDetail job1 = JobBuilder.newJob(OpeningJobAPR.class).withIdentity("Job1", "group1").build();
			Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(02, 00)).build();
//			Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 05)).build();
		
			Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);
			
			// Closing For APR Zone 
			JobDetail job4 = JobBuilder.newJob(ClosingJobAPR.class).withIdentity("Job4", "group4").build();
			Trigger trigger4 = TriggerBuilder.newTrigger().withIdentity("cronTrigger4", "group4").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(17, 30)).build();
//			Trigger trigger4 = TriggerBuilder.newTrigger().withIdentity("cronTrigger4", "group4").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 10)).build();
		
			Scheduler scheduler4 = new StdSchedulerFactory().getScheduler();
			scheduler4.start();
			scheduler4.scheduleJob(job4, trigger4);
			
			// Opening For ER Zone 
			JobDetail job2 = JobBuilder.newJob(OpeningJobER.class).withIdentity("Job2", "group2").build();
			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("cronTrigger2", "group2").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 00)).build();
//			Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("cronTrigger2", "group2").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(16, 15)).build();
		
			Scheduler scheduler2 = new StdSchedulerFactory().getScheduler();
			scheduler2.start();
			scheduler2.scheduleJob(job2, trigger2);
			
			// Closing For ER Zone 
			JobDetail job5 = JobBuilder.newJob(ClosingJobER.class).withIdentity("Job5", "group5").build();
			Trigger trigger5 = TriggerBuilder.newTrigger().withIdentity("cronTrigger5", "group5").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(21, 30)).build();
//			Trigger trigger5 = TriggerBuilder.newTrigger().withIdentity("cronTrigger5", "group5").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(17, 05)).build();
		
			Scheduler scheduler5 = new StdSchedulerFactory().getScheduler();
			scheduler5.start();
			scheduler5.scheduleJob(job5, trigger5);
			
			
			// Opening For AR Zone 
			JobDetail job3 = JobBuilder.newJob(OpeningJobAR.class).withIdentity("Job3", "group3").build();
			Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("cronTrigger3", "group3").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(18, 00)).build();
//			Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("cronTrigger3", "group3").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(16, 20)).build();
		
			Scheduler scheduler3 = new StdSchedulerFactory().getScheduler();
			scheduler3.start();
			scheduler3.scheduleJob(job3, trigger3);		
			
			
			// Closing For AR Zone 
			JobDetail job6 = JobBuilder.newJob(ClosingJobAR.class).withIdentity("Job6", "group6").build();
			Trigger trigger6 = TriggerBuilder.newTrigger().withIdentity("cronTrigger6", "group6").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(04, 30)).build();
//			Trigger trigger6 = TriggerBuilder.newTrigger().withIdentity("cronTrigger6", "group6").withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(17, 10)).build();
		
			Scheduler scheduler6 = new StdSchedulerFactory().getScheduler();
			scheduler6.start();
			scheduler6.scheduleJob(job6, trigger6);
		}
		catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        context = contextEvent.getServletContext();
        // you can set a context variable just like this
        context.setAttribute("TEST", "TEST_VALUE");
    }
//    
    public void contextDestroyed(ServletContextEvent contextEvent) {
        // context is destroyed interrupts the thread
        t1.interrupt();
//        t2.interrupt();
    }            
}