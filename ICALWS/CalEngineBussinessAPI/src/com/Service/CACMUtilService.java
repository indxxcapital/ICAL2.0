package com.Service;

import java.util.ArrayList;
import java.util.List;
import com.Bean.CACMBean;

public class CACMUtilService {

	public static List<String> generateDividendCACMColumns()
	{
		List<String> cloumnsList = new ArrayList<>();
		cloumnsList.add("S.No.");
		cloumnsList.add("ISIN");
		cloumnsList.add("TICKER");
		cloumnsList.add("Security Name");
		cloumnsList.add("EDI_DIV");
		cloumnsList.add("EDI_CUR");
		cloumnsList.add("EDI_TYPE");
		cloumnsList.add("FDS_DIV");
		cloumnsList.add("FDS_CUR");
		cloumnsList.add("FDS_TYPE");
		cloumnsList.add("Check");
		cloumnsList.add("Difference");
		cloumnsList.add("Unique ISIN");
		return cloumnsList;
	}
	
	public static List<String> setDivCloumnsValue(CACMBean sBean,Integer currentDivRow)
	{
		List<String> cloumnsValue = new ArrayList<>();
		cloumnsValue.add(currentDivRow.toString());
		cloumnsValue.add(sBean.getISIN());
		cloumnsValue.add(sBean.getBBGTicker());
		cloumnsValue.add(sBean.getName());
		cloumnsValue.add(sBean.getGrossAmountEDI().toString());
		cloumnsValue.add(sBean.getCurrencyEDI());
		cloumnsValue.add(sBean.getEventCodeEDI());
		cloumnsValue.add(sBean.getGrossAmountFDS().toString());
		cloumnsValue.add(sBean.getCurrencyFDS());
		cloumnsValue.add(sBean.getEventCodeFDS());
		cloumnsValue.add(sBean.getCheck());
		cloumnsValue.add(sBean.getDifferenceInAmount().toString());
		cloumnsValue.add(sBean.getUniqueISIN());
		return cloumnsValue;
	}
	
	public static List<String> generateSplitCACMColumns()
	{
		List<String> cloumnsList = new ArrayList<>();
		cloumnsList.add("S.No.");
		cloumnsList.add("ISIN");
		cloumnsList.add("TICKER");
		cloumnsList.add("Security Name");
		cloumnsList.add("EDI_Split_Factor");
		cloumnsList.add("FDS_Split_Factor");
		cloumnsList.add("Check");
		cloumnsList.add("Unique ISIN");
		return cloumnsList;
	}
	
	public static List<String> setSplitCloumnsValue(CACMBean sBean,Integer currentDivRow)
	{
		List<String> cloumnsValue = new ArrayList<>();
		cloumnsValue.add(currentDivRow.toString());
		cloumnsValue.add(sBean.getISIN());
		cloumnsValue.add(sBean.getBBGTicker());
		cloumnsValue.add(sBean.getName());
		cloumnsValue.add(sBean.getRatioEDI().toString());
		cloumnsValue.add(sBean.getRatioFDS().toString());
		cloumnsValue.add(sBean.getCheck());
//		cloumnsValue.add(sBean.getDifferenceInRatio().toString());
		cloumnsValue.add(sBean.getUniqueISIN());
		return cloumnsValue;
	}
	
	public static List<String> generateIdChangeCACMColumns()
	{
		List<String> cloumnsList = new ArrayList<>();
		cloumnsList.add("S.No.");
		cloumnsList.add("ISIN");
		cloumnsList.add("TICKER");
		cloumnsList.add("Security Name");
		cloumnsList.add("EDI_OLD_VALUE");
		cloumnsList.add("EDI_NEW_VALUE");
		cloumnsList.add("EDI_TYPE");
		cloumnsList.add("FDS_OLD_VALUE");
		cloumnsList.add("FDS_NEW_VALUE");
		cloumnsList.add("FDS_TYPE");
		cloumnsList.add("Check");
		cloumnsList.add("Unique ISIN");
		return cloumnsList;
	}
	
	public static List<String> setIdChangeCloumnsValue(CACMBean sBean,Integer currentDivRow)
	{
		List<String> cloumnsValue = new ArrayList<>();
		cloumnsValue.add(currentDivRow.toString());
		cloumnsValue.add(sBean.getISIN());
		cloumnsValue.add(sBean.getBBGTicker());
		cloumnsValue.add(sBean.getName());
		cloumnsValue.add(sBean.getOldValueEDI());
		cloumnsValue.add(sBean.getNewValueEDI());
		cloumnsValue.add(sBean.getEventCodeEDI());
		cloumnsValue.add(sBean.getOldValueFDS());
		cloumnsValue.add(sBean.getNewValueFDS());
		cloumnsValue.add(sBean.getEventCodeFDS());
		cloumnsValue.add(sBean.getCheck());
		cloumnsValue.add(sBean.getUniqueISIN());
		return cloumnsValue;
	}	
}