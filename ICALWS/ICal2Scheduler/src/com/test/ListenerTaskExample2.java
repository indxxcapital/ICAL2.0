package com.test;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.ICal2.Scheduler.Jobs.OpeningJobAR;


public class ListenerTaskExample2 implements ServletContextListener {
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
//        
////        t2 =  new Thread(){
////            //task
////            public void run(){                
////                try {
////                    while(true){
////                        System.out.println("Thread 2  running every 2 hr ::::" + new Date());
////                        Thread.sleep(7200000);
////                        
////                    }
////                } catch (InterruptedException e) {}
////            }            
////        };
////        t2.start();
//        
//        
//        
////        TimerTask task = new TimerTask() {
////            @Override
////            public void run() {
////              // task to run goes here
////              System.out.println("Hello !!!");
////            }
////          };
////          Timer timer = new Timer();
////          long delay = 0;
////          long intevalPeriod = 1 * 1000; 
////          // schedules the task to be run in an interval 
////          timer.scheduleAtFixedRate(task, delay,
////                                      intevalPeriod);
//          
//        
//        
//        
        JobDetail job1 = JobBuilder.newJob(OpeningJobAR.class).withIdentity("job1", "group1").build();
		
		Trigger trigger1;
		Scheduler scheduler1;
		try {
			trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1")
//				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(14, 15))
					.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression("0/7 * * * * ?")))
					.build();
		
			scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);
		} catch (SchedulerException | ParseException e) {
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