package com.DataService;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.CalCommon.ICalCommonUtill;

/**
 * Connect to Database
 * @author hany.said
 */
public class ConnectionFactory
{
	static Connection conn = null;
	
	private static void readPropertyFile() 
	{
		Properties prop = new Properties();
    	InputStream input = null;
    	try 
    	{
			input = new FileInputStream("../icalconfig/ical2config.properties");
			
			// load a properties file
			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements())
			{
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				ConfigUtil.propertiesMap.put(key, value);
				System.out.println("Key : " + key + ", Value : " + value);
			}
			
			// get the property value and print it out
			dbURL = ConfigUtil.propertiesMap.get("databaseUrl");
			user = ConfigUtil.propertiesMap.get("dbuser");
			pass = ConfigUtil.propertiesMap.get("dbpassword");
			dbDriver = ConfigUtil.propertiesMap.get("dbdriver");
			
			System.out.println(prop.getProperty("databaseUrl"));
			System.out.println(prop.getProperty("dbuser"));
			System.out.println(prop.getProperty("dbpassword"));
			
    	} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static String dbURL = "";
	private static String user = "";
	private static String pass = "";
	private static String dbDriver = "";
	
	//for mysql server 
//	private static final String dbURL = "jdbc:mysql://104.130.29.253:3306/ical2?allowPublicKeyRetrieval=true&useSSL=false";
//	private static final String user = "monika";
//	private static final String pass = "@ical_monika";
	 
//	 public static void closeConnection() throws ClassNotFoundException, SQLException
//	 {
//	 }
	
	 public static ResultSet Execute(String sqlQuery) 
	 {
		 ResultSet rs = null;
			 
	      try
	      {
	    	  Statement st = conn.createStatement();
			rs = st.executeQuery(sqlQuery);
	      } catch (SQLException e) {
			e.printStackTrace();
	      }
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
    	 if(conn == null)
    	 {
    		 readPropertyFile() ;
    		 Class.forName(dbDriver); 
//    		 conn = DriverManager.getConnection(dbURL, user, pass);
//    		 conn = DriverManager.getConnection(dbURL);
    		 String strConnURL = ConfigUtil.propertiesMap.get("databaseUrl") + ";user=" + ConfigUtil.propertiesMap.get("dbuser") +";password=" + 
    				 ConfigUtil.propertiesMap.get("dbpassword") + ";database=" + ConfigUtil.propertiesMap.get("dbName");
    				 
//    		 conn = DriverManager.getConnection("jdbc:sqlserver://162.242.166.128;user=Database;password=Welcome.1;database=ical2");
    		 conn = DriverManager.getConnection(strConnURL);
    		 
    		 DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
        	 System.out.println("Driver name: " + dm.getDriverName());
        	 System.out.println("Driver version: " + dm.getDriverVersion());
        	 System.out.println("Product name: " + dm.getDatabaseProductName());
        	 System.out.println("Product version: " + dm.getDatabaseProductVersion());
    	 }
    }
    //conn = DriverManager.getConnection("jdbc:sqlserver://204.80.90.133;user=crypto_user;password=Crypto@user;database=FDS_DataFeeds");
    
    
    
//    
//    public static void getMSSQLConnection() throws ClassNotFoundException, SQLException
//    {
//    	readPropertyFile() ;
//    	Class.forName(dbDriver); 
//    	if(conn == null)
//    	{
//    		conn = DriverManager.getConnection(dbURL, user, pass);
//    			
//    		DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
//    		System.out.println("Driver name: " + dm.getDriverName());
//    		System.out.println("Driver version: " + dm.getDriverVersion());
//    		System.out.println("Product name: " + dm.getDatabaseProductName());
//    		System.out.println("Product version: " + dm.getDatabaseProductVersion());
//    	}
//    	if (conn != null) 
//    	{
//        	 
////        	 DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
////        	 System.out.println("Driver name: " + dm.getDriverName());
////        	 System.out.println("Driver version: " + dm.getDriverVersion());
////        	 System.out.println("Product name: " + dm.getDatabaseProductName());
////        	 System.out.println("Product version: " + dm.getDatabaseProductVersion());
////        	 
////        	 loadSchema(dm);        	      	 
//    	}
//    	conn.setAutoCommit(false);
//    }
//    
    
//    private static void loadSchema(DatabaseMetaData dm )
//    {
//	   	 String   catalog          = null;
//	   	 String   schemaPattern    = null;
//	   	 String   tableNamePattern = null;
//	   	 String[] types            = null;
//    	 ResultSet tablesRresultSet;
//    	 ResultSet columnsRresultSet;
//		try
//		{
//			tablesRresultSet = dm.getTables(catalog, schemaPattern, tableNamePattern, types );
//			 while(tablesRresultSet.next())
//			 {
//        		 System.out.println(tablesRresultSet.getString(3));
//        	     String tableName = tablesRresultSet.getString(3);
//        	     columnsRresultSet = dm.getColumns(catalog, schemaPattern, tableName,null );
//        	     while(columnsRresultSet.next())
//        	     {
//        	    	 System.out.println("Cloumn Name - " + columnsRresultSet.getString(4) + "-----Type-" + columnsRresultSet.getString(5)  );
//        	     }
//        	 }  
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//    }
    
    
//    /**
//     * Test Connection
//     * @throws ClassNotFoundException 
//     * @throws SQLException 
//     */
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ConnectionFactory.getConnection();
////        System.out.println("Product version: " );
//    }
}
