package com.DataService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DefaultDao
{
	public ResultSet getAllData(String tableName) throws SQLException, ClassNotFoundException
	{
		ConnectionFactory.getConnection();
		String query = DataUtill.createSelect("currency","");
		ResultSet rs =ConnectionFactory.Execute(query);
		return rs;
	}
	
	public ResultSet getAllData(String tableName,String filter) throws SQLException, ClassNotFoundException
	{
		ConnectionFactory.getConnection();
		String query = DataUtill.createSelect(tableName,filter);
	    System.out.println("getAllDataQUERY:::::" + query);
		ResultSet rs = ConnectionFactory.Execute(query);
		return rs;
	}
	
	 public void readConfig() throws SQLException, ClassNotFoundException
	 {
		 ConnectionFactory.readPropertyFile() ;
	 }
	
	 public ResultSet ExecuteQuery(String sqlQuery) throws SQLException, ClassNotFoundException,Exception
	 {
		 System.out.println(sqlQuery);
		 ConnectionFactory.getConnection();
		 ResultSet rs = ConnectionFactory.Execute(sqlQuery);
		 return rs;		 
	 }
	
	 public Map<String,String> getAllConfigValues()
	 {
		 return ConnectionFactory.readPropertyFile();
	 }
	public int insertData(String tableName,Map<String,Object> keyValueMap) throws SQLException, ClassNotFoundException
	{
		ConnectionFactory.getConnection();
		String query = DataUtill.createInsert(tableName,keyValueMap);
		System.out.println(query);
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(query);
		return iValue;
	}
	
	
	
	
	public int updatetData(String updateQuery) throws SQLException, ClassNotFoundException
	{
		System.out.println(updateQuery);
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(updateQuery);
		return iValue;
	}
	
	public int updateData(String tableName,Map<String,Object> keyValueMap) throws SQLException, ClassNotFoundException
	{
		ConnectionFactory.getConnection();
		String query = DataUtill.createUpdate(tableName,keyValueMap);
		System.out.println(query);
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(query);
		return iValue;
	}
	
	public void executeInsert(String strInsertQuery) throws Exception
	{
		try {
			ConnectionFactory.getConnection();
			ConnectionFactory.ExecuteUpdateInsertDelete(strInsertQuery);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public int deleteData(String tableName,String filter) throws SQLException, ClassNotFoundException
	{
		ConnectionFactory.getConnection();
		String query = DataUtill.createDelete(tableName,filter);
		System.out.println(query);
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(query);
		return iValue;
	}
}