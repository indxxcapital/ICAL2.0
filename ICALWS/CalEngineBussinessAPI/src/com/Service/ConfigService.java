package com.Service;

import java.util.Map;

import com.DataService.DefaultDao;

public class ConfigService 
{
	public Map<String,String> getConfigValues()
	{
		DefaultDao dao = new DefaultDao();
		return dao.getAllConfigValues();
	}

}
