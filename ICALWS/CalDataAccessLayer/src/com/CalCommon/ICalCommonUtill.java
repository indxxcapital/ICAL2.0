package com.CalCommon;

import java.util.HashMap;
import java.util.Map;

public class ICalCommonUtill {

	public static Map<String,String> getIndexStatusNameCodeMap()
	{
		Map<String,String> keyValueMap = new HashMap<String,String>();
		keyValueMap.put("New", "NI");
		keyValueMap.put("Upcoming", "UI");
		keyValueMap.put("Live", "LI");
		keyValueMap.put("Run", "RI");
		keyValueMap.put("Approve", "AI");
		return keyValueMap;
	}
	
	public static Map<String,String> getIndexStatusCodeNameMap()
	{
		Map<String,String> keyValueMap = new HashMap<String,String>();
		keyValueMap.put("NI","New");
		keyValueMap.put("UI","Upcoming");
		keyValueMap.put("LI","Live");
		keyValueMap.put("RI","Run");
		keyValueMap.put("AI","Approve");
		return keyValueMap;
	}
	
//	public static Map<String,String> getIndexWightTypeCodeMap()
//	{
//		Map<String,String> keyValueMap = new HashMap<String,String>();
//		keyValueMap.put("Proprietary Weighted Indices","PWI");
//		keyValueMap.put("Market Cap Weighted Indices","MWI");
//		return keyValueMap;
//	}
	
	public static Map<String,String> getIndexWeightCodeTypeMap()
	{
		Map<String,String> keyValueMap = new HashMap<String,String>();
		keyValueMap.put("PWI","Proprietary Weighted Indices");
		keyValueMap.put("MWI","Market Cap Weighted Indices");
		return keyValueMap;
	}
	
	
	public static Map<String,String> getIndexZoneMap()
	{
		Map<String,String> keyValueMap = new HashMap<String,String>();
		keyValueMap.put("Americas Region","AR");
		keyValueMap.put("European Region","ER");
		keyValueMap.put("Asia/Pacific Region","APR");
		return keyValueMap;
	}
	
	public static Map<String,String> getIndexDividentAdjustmentMap()
	{
		Map<String,String> keyValueMap = new HashMap<String,String>();
		keyValueMap.put("Divisor Adjustment","DA");
		keyValueMap.put("Stock Adjustment","SA");
		return keyValueMap;
	}
}
