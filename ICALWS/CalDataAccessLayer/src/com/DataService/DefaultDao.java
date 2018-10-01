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
	
	 public ResultSet ExecuteQuery(String sqlQuery) throws SQLException, ClassNotFoundException
	 {
		 ConnectionFactory.getConnection();
		 ResultSet rs = ConnectionFactory.Execute(sqlQuery);
		 return rs;		 
	 }
	
	public int insertData(String tableName,Map<String,Object> keyValueMap) throws SQLException, ClassNotFoundException
	{
		ConnectionFactory.getConnection();
		String query = DataUtill.createInsert(tableName,keyValueMap);
	      
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(query);
		ConnectionFactory.closeConnection();
		return iValue;
	}
	
	public int updatetData(String updateQuery) throws SQLException, ClassNotFoundException
	{
		System.out.println(updateQuery);
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(updateQuery);
		ConnectionFactory.closeConnection();
		return iValue;
	}
	
	public int updateData(String tableName,Map<String,Object> keyValueMap) throws SQLException, ClassNotFoundException
	{
		ConnectionFactory.getConnection();
		String query = DataUtill.createInsert(tableName,keyValueMap);
	      
		int iValue =ConnectionFactory.ExecuteUpdateInsertDelete(query);
		ConnectionFactory.closeConnection();
		return iValue;
	}
	
	public void executeInsert(String strInsertQuery) throws Exception
	{
		try {
			ConnectionFactory.getConnection();
			ConnectionFactory.ExecuteUpdateInsertDelete(strInsertQuery);
			ConnectionFactory.closeConnection();
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
		ConnectionFactory.closeConnection();
		return iValue;
	}
}
