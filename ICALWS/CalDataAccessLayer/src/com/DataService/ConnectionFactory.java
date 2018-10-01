package com.DataService;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Connect to Database
 * @author hany.said
 */
public class ConnectionFactory
{
	static Connection conn = null;
//	ConnectionFactory() throws ClassNotFoundException, SQLException
//	{
//		getConnection();
//	}
	
//	private static final String dbURL = "jdbc:mysql://localhost:3306/ical2?useSSL=false";////?useUnicode=true&characterEncoding=UTF-8";;
//	private static final String dbURL = "jdbc:mysql://localhost:3306/ical2?allowPublicKeyRetrieval=true&useSSL=false";
	
	private static final String dbURL = "jdbc:mysql://127.0.0.1:3306/ical2?allowPublicKeyRetrieval=true&useSSL=false";
	private static final String user = "root";
	private static final String pass = "root";
	//for server 
//	private static final String dbURL = "jdbc:mysql://104.130.29.253:3306/ical2?allowPublicKeyRetrieval=true&useSSL=false";
//	private static final String user = "monika";
//	private static final String pass = "@ical_monika";
	 
	 public static void closeConnection() throws ClassNotFoundException, SQLException
	 {
//		 conn.commit();
//		 conn.close();
	 }
	
	 public static ResultSet Execute(String sqlQuery) throws SQLException
	 {
		 Statement st = conn.createStatement();
	      ResultSet rs = st.executeQuery(sqlQuery);
	      return rs;		 
	 }
	 
	 public static int ExecuteUpdateInsertDelete(String sqlQuery) throws SQLException
	 {
		 Statement st = conn.createStatement();
	     int iValue = st.executeUpdate(sqlQuery);
	     return iValue;
	 }
	 
    /**
     * Get a connection to database
     * @return Connection object
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static void getConnection() throws ClassNotFoundException, SQLException
    {
    	
    	 Class.forName("com.mysql.jdbc.Driver"); 
    	 if(conn == null)
    	 {
//    	 conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
    		 conn = DriverManager.getConnection(dbURL, user, pass);
//    		 conn.a
    		 DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
        	 System.out.println("Driver name: " + dm.getDriverName());
        	 System.out.println("Driver version: " + dm.getDriverVersion());
        	 System.out.println("Product name: " + dm.getDatabaseProductName());
        	 System.out.println("Product version: " + dm.getDatabaseProductVersion());
    	 }
         if (conn != null) 
         {
        	 
//        	 DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
//        	 System.out.println("Driver name: " + dm.getDriverName());
//        	 System.out.println("Driver version: " + dm.getDriverVersion());
//        	 System.out.println("Product name: " + dm.getDatabaseProductName());
//        	 System.out.println("Product version: " + dm.getDatabaseProductVersion());
//        	 
//        	 loadSchema(dm);        	      	 
         }
//         conn.setAutoCommit(false);
    }
    
    private static void loadSchema(DatabaseMetaData dm )
    {
	   	 String   catalog          = null;
	   	 String   schemaPattern    = null;
	   	 String   tableNamePattern = null;
	   	 String[] types            = null;
    	 ResultSet tablesRresultSet;
    	 ResultSet columnsRresultSet;
		try
		{
			tablesRresultSet = dm.getTables(catalog, schemaPattern, tableNamePattern, types );
			 while(tablesRresultSet.next())
			 {
        		 System.out.println(tablesRresultSet.getString(3));
        	     String tableName = tablesRresultSet.getString(3);
        	     columnsRresultSet = dm.getColumns(catalog, schemaPattern, tableName,null );
        	     while(columnsRresultSet.next())
        	     {
        	    	 System.out.println("Cloumn Name - " + columnsRresultSet.getString(4) + "-----Type-" + columnsRresultSet.getString(5)  );
        	     }
        	 }  
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     * Test Connection
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionFactory.getConnection();
//        System.out.println("Product version: " );
    }
}
