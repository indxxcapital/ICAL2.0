package com.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Bean.ClientBean;
import com.DataService.ConfigUtil;
import com.DataService.DefaultDao;

public class ClientService 
{
	public List<ClientBean> getAllClients()
	{
		List<ClientBean> clientsList = new ArrayList<>();
		
		DefaultDao dDao = new DefaultDao();
		try
		{
			String strQuery = " SELECT DISTINCT clientName FROM " + ConfigUtil.propertiesMap.get("dbName") + ".clientdetails order by clientName asc ";
					
			ResultSet rs = dDao. ExecuteQuery(strQuery) ;
			clientsList =convertListToClientBeanList(rs);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientsList;  
	}
	
	public List<ClientBean> convertListToClientBeanList(ResultSet rs) throws SQLException, ClassNotFoundException
	{
		List<ClientBean> clientsList = new ArrayList<>();
		while (rs.next())
		{
			ClientBean cBean = new ClientBean(); 
			getClientBeanFromResultSet(rs,cBean);
			clientsList.add(cBean);
	    }		
		return clientsList;
	}
	
	private void getClientBeanFromResultSet(ResultSet rs,ClientBean cBean) throws SQLException
	{
		if(rs.getString("clientName") != null)
			cBean.setClientName(rs.getString("clientName"));	
	}
	

}
