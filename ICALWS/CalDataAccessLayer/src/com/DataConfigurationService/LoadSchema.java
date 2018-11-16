package com.DataConfigurationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadSchema
{
	public static Map<String,String> tableVsClassMap = new HashMap<>();
	public static Map<String,List<String>> tableVsColumnNameMap = new HashMap<>();

//	private static void loadSchema(DatabaseMetaData dm )
//	{
//		String   catalog          = null;
//		String   schemaPattern    = null;
//		String   tableNamePattern = null;
//		String[] types            = null;
//		ResultSet tablesRresultSet;
//		ResultSet columnsRresultSet;
//		try
//		{
//			tablesRresultSet = dm.getTables(catalog, schemaPattern, tableNamePattern, types );
//			while(tablesRresultSet.next())
//			{
//				System.out.println(tablesRresultSet.getString(3));
//				String tableName = tablesRresultSet.getString(3);
//				columnsRresultSet = dm.getColumns(catalog, schemaPattern, tableName,null );
//				while(columnsRresultSet.next())
//				{
//					System.out.println("Cloumn Name - " + columnsRresultSet.getString(4) + "-----Type-" + columnsRresultSet.getString(5)  );
//				}
//			}  
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
