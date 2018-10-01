package com.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Bean.ClientBean;
import com.DataService.DefaultDao;

public class ClientService 
{
	public List<ClientBean> getAllClients()
	{
		List<ClientBean> clientsList = new ArrayList<>();
		
		DefaultDao dDao = new DefaultDao();
		try
		{
			ResultSet rs = dDao. getAllData("currency","") ;
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
		if(rs.getString("crryname") != null)
			cBean.setClientName(rs.getString("crryname"));	
	}
	

}
