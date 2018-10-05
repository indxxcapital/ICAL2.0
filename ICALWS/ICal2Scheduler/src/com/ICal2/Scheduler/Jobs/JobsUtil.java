package  com.ICal2.Scheduler.Jobs;

import com.DataService.ConfigUtil;

public class JobsUtil {

	public static void main(String[] args) 
	{
		System.out.println("HELLO REST UTIL ");
	}
	
	public static final String INDEX_CLOSE_FILE_PATH = ConfigUtil.propertiesMap.get("INDEX_CLOSE_FILE_PATH");// "c://temp//closing//";
	public static final String INDEX_OPEN_FILE_PATH = ConfigUtil.propertiesMap.get("INDEX_OPEN_FILE_PATH");;//"c://temp//opening//";

}
