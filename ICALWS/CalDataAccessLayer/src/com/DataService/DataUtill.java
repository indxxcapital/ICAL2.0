package com.DataService;

import java.util.Map;

public class DataUtill
{
	public static String createInsert(String tableName,Map<String,Object> keyValueMap)
	{
//		String strInsertQuery = "insert into ical2." + tableName ;
		String strInsertQuery = "insert into " + ConfigUtil.propertiesMap.get("dbName") + "." + tableName ;
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
	
	public static String createUpdate(String tableName,Map<String,Object> keyValueMap)
	{
		String strUpdateQuery = "UPDATE  " + ConfigUtil.propertiesMap.get("dbName") + "." + tableName + " SET ";
		String strUpdateValues = "";
		
		for (Map.Entry<String,Object> Entry : keyValueMap.entrySet()) 
		{
			
			String columnName = Entry.getKey();
			if(!columnName.trim().equals("id"))
			{
				if(!strUpdateValues.trim().equalsIgnoreCase(""))
					strUpdateValues += ",";
				Object columnValue = Entry.getValue();
				if (columnValue instanceof Double)
					strUpdateValues += columnName + "= " + columnValue ;//+ ",";
				else
					strUpdateValues += columnName + "= '" + columnValue + "'";	
			}
		}
		strUpdateQuery += strUpdateValues + " Where id ='"+ keyValueMap.get("id") + "'";
		
		return strUpdateQuery;		
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
//		String strSelectQuery = "select * from  ical2." + tableName + " " + filterClause;		
		String strSelectQuery = "select * from  " + ConfigUtil.propertiesMap.get("dbName") + "." + tableName + " " + filterClause;
		
		
		return strSelectQuery;		
	}
	
	public static String createDelete(String tableName,String filter)
	{
//		String strInsertQuery = "Delete from ical2." + tableName + " "  + filter;
		String strInsertQuery = "Delete from " + ConfigUtil.propertiesMap.get("dbName") + "." + tableName + " "  + filter;
		
		return strInsertQuery;
	}
}
