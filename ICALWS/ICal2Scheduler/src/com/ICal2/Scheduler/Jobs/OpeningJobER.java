package com.ICal2.Scheduler.Jobs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.Bean.IndexBean;
import com.Service.IndexService;

public class OpeningJobER implements Job{ 
	
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		System.out.println("Generation Opening Files for ER Zone  --->>> Time is " + new Date());
		generateOpeningFilesER();
	} 
	
	public void generateOpeningFilesER()
	{
		System.out.println("inside generateOpeningFilesER");
		LocalDate localDate = LocalDate.now();
		String strDate = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
		
		IndexService iservice = new IndexService();
		List<IndexBean> iList = iservice.getAllLiveIndexForFileGeneration("ER");
		System.out.println("Total Index:::"+ iList.size());
		for(int i=0;i < iList.size();i++)
		{
			IndexBean iBean = iList.get(i);
			
			String fileName = "Opening-" + iBean.getIndexTicker() +"-"+ strDate + ".csv" ;
			String fileCompletePath = JobsUtil.INDEX_OPEN_FILE_PATH +fileName;
			try 
			{
				System.out.println("START generating file for index ::::" + iBean.getIndexTicker());
				iservice.generateOpeningFile(iBean,strDate,fileCompletePath);
				System.out.println("END generating file for index ::::" + iBean.getIndexTicker());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
}