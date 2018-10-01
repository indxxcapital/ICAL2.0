package com.DataService;
import java.util.Map;

public class DataUtill
{
	public static String createInsert(String tableName,Map<String,Object> keyValueMap)
	{
		String strInsertQuery = "insert into ical2." + tableName ;
		String strColumns= "";
		String strValue =  "";
		
		for (String cloumnName : keyValueMap.keySet()) 
		{
			if(strColumns.trim().equalsIgnoreCase(""))
				strColumns = cloumnName ;
			else
				strColumns = strColumns +  "," + cloumnName ; 
			if(!strValue.trim().equalsIgnoreCase(""))
				strValue = strValue + "," ; 
			Object columnValue = keyValueMap.get(cloumnName);
//			if (columnValue instanceof String)
			{				
				strValue =strValue +  "'"+ columnValue + "'";
			}
//			if (columnValue instanceof Integer)
//			{
//				strValue = strValue + columnValue;
//				
//			}
//			if (columnValue instanceof Timestamp)
//			{
//			}			
		}
		strInsertQuery = strInsertQuery + "(" + strColumns + ")" + "VALUES (" + strValue + ")";
		
		return strInsertQuery;
		
	}
	
	public String createUpdate(String tableName,Map<String,Object> keyValueMap)
	{
		String strInsertQuery = "insert into ";
		
		for (String cloumnName : keyValueMap.keySet()) 
		{
			Object columnValue = keyValueMap.get(cloumnName);
			if (columnValue instanceof String)
			{
				
				
			}
			
			
		}
		
		return strInsertQuery;
		
	}
	
	
	public static String createSelect(String tableName,Map<String,Object> filterKeyValueMap)
	{
		String strSelectQuery = "select * from " + tableName;		
		if(filterKeyValueMap != null)
		{
			for (String cloumnName : filterKeyValueMap.keySet())
			{
				Object value =  filterKeyValueMap.get(cloumnName);
				
			}
		}
		return strSelectQuery;		
	}
	
	public static String createSelect(String tableName,String filterClause)
	{
		String strSelectQuery = "select * from  ical2." + tableName + " " + filterClause;		
		
		return strSelectQuery;		
	}
	
	public static String createDelete(String tableName,String filter)
	{
		String strInsertQuery = "Delete from ical2." + tableName + " "  + filter;
		
		return strInsertQuery;
	}
}
