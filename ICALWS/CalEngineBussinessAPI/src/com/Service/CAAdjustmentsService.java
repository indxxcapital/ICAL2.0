package com.Service;

import java.util.HashMap;
import java.util.Map;
import com.Bean.CorporateActionsFinal;
import com.Bean.IndexBean;
import com.Bean.SecurityBean;
import com.CalCommon.ICalCommonUtill;
import com.corporate.actions.service.CorporateActionsAdjustments;
import com.corporate.actions.service.DividendCorporateActionAdjustments;
import com.corporate.actions.service.SplitCorporateActionAdjustment;
import com.corporate.actions.service.StockDividendCorporateActionsAdjustments;

public class CAAdjustmentsService 
{
	Double oldPrice ;
	Double oldShares ;
	
	public Map<String,Double> adjustCorporateActions(Double shares, Double securityPrice,String securityId,String effectiveDate,IndexBean iBean)
	{
		
		Map<String,CorporateActionsFinal> caMap =  getAllCAForSecurity(securityId,effectiveDate,ICalCommonUtill.PRIMARY_DATABASE_CA);
		Map<String,Double> factorMap =  new HashMap<String,Double>();
		
		oldPrice = securityPrice;
		oldShares = shares;
		
		CorporateActionsAdjustments adjustCA =  new CorporateActionsAdjustments ();
		Double divisorAdjustmentFactor = (double) 0;
		Double shareAdjustmentFactor = (double) 1;
		Double sharePriceAdjustmentFactor = (double) 1;
		
		Double amount = (double) 0;
		boolean isDivisorAdjustment = false;
		for (Map.Entry<String,CorporateActionsFinal> caEntry : caMap.entrySet()) 
		{
			CAEnum CA = CAEnum.valueOf(caEntry.getValue().getEventCode());
			switch(CA)
			{
				case ORD_DIV:
					if(!iBean.getIndexType().trim().equalsIgnoreCase("PR"))
					{
						if(iBean.getNormalCashDivAdj().trim().equalsIgnoreCase("DA"))
							isDivisorAdjustment = true;
						else
							isDivisorAdjustment = false;
						amount = getAmount(caEntry.getValue(),iBean);
						adjustCA  = new  DividendCorporateActionAdjustments(amount,oldPrice,isDivisorAdjustment);
					}
					break;
				
				case SPL_DIV:
					if(iBean.getSpecialCashDivAdj().trim().equalsIgnoreCase("DA"))
						isDivisorAdjustment = true;
					else
						isDivisorAdjustment = false;
					amount = getAmount(caEntry.getValue(),iBean);
					adjustCA  = new  DividendCorporateActionAdjustments(amount,oldPrice,isDivisorAdjustment);
					break;	
//					
				case STOCK_SPLT:
								
					adjustCA = new SplitCorporateActionAdjustment(caEntry.getValue().getRatio());
					break;	
					
				case DVD_STOCK:		
					adjustCA = new StockDividendCorporateActionsAdjustments(caEntry.getValue().getRatio());
					break;
				default:
					break;
			}
			sharePriceAdjustmentFactor *= adjustCA.getPriceFactor();
			shareAdjustmentFactor *= adjustCA.getShareFactor();
//			divisorAdjustmentFactor += adjustCA.getDivisorFactor();
		}
		if(caMap.get(CAEnum.CHG_ID.toString())!= null || caMap.get(CAEnum.CHG_NAME.toString())!= null ||caMap.get(CAEnum.CHG_TKR.toString())!= null)
			adjustIdChange(caMap,securityId);
		Double newPrice = oldPrice*sharePriceAdjustmentFactor;
		Double newShares = oldShares*shareAdjustmentFactor;
		divisorAdjustmentFactor =(oldPrice*oldShares - newPrice*newShares)/ iBean.getCloseIndexValue(); 
		
		factorMap.put("PriceFactor", sharePriceAdjustmentFactor);
		factorMap.put("SharesFactor", shareAdjustmentFactor);
		factorMap.put("DivisorFactor", divisorAdjustmentFactor);
		return factorMap;
	}
		
	private void adjustIdChange(Map<String, CorporateActionsFinal> caMap, String securityId)
	{
		try
		{
			String strISIN = "" ;
			String strName = "";
			String strTicker = "" ;
			
			if(caMap.get(CAEnum.CHG_ID.toString())!= null)
				strISIN = caMap.get(CAEnum.CHG_ID.toString()).getNewValue();
			if(caMap.get(CAEnum.CHG_NAME.toString())!= null)
				strName = caMap.get(CAEnum.CHG_ID.toString()).getNewValue();
			if(caMap.get(CAEnum.CHG_TKR.toString())!= null)
				strTicker = caMap.get(CAEnum.CHG_ID.toString()).getNewValue();
			
			if(strISIN == null && strName == null && strTicker == null)
				return;
			
			SecurityService ss = new SecurityService();
			SecurityBean sBean = new SecurityBean();
			sBean.setId(Integer.parseInt(securityId));
			
			if(strISIN != null && !strISIN.trim().equalsIgnoreCase(""))
				sBean.setISIN(strISIN);
			if(strName != null && !strName.trim().equalsIgnoreCase(""))
				sBean.setFullName(strName);
			if(strTicker != null && !strTicker.trim().equalsIgnoreCase(""))
				sBean.setBBGTicker(strTicker);
			
			ss.deleteSecurity(securityId);
			ss.insertNewSecurityForIdChange(sBean);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Double getAmount(CorporateActionsFinal caFinalBean, IndexBean iBean) 
	{
		Double taxPrecent = getTaxPercent();
		Double currencyMultiplier = currencyMultiplier(caFinalBean.getCurrency(),iBean.getCurrency());
		Double amount = (double) 0;
		
		if(iBean.getIndexType().trim().equalsIgnoreCase("NTR"))
		{
			amount = caFinalBean.getGrossAmount()*(1-taxPrecent/100);
		}
		if(iBean.getIndexType().trim().equalsIgnoreCase("GTR") || iBean.getIndexType().trim().equalsIgnoreCase("PR"))
		{
			amount = caFinalBean.getGrossAmount();
		}
		return amount*currencyMultiplier;
	}

	private Double currencyMultiplier(String fromCurrency,String toCurrency)
	{
		CurrencyService currecnyService = new CurrencyService();
		Double currencyMultiplier = currecnyService.currencyMultiplier(fromCurrency,toCurrency);
		return currencyMultiplier;
	}

	private Double getTaxPercent()
	{
		return (double) 10;
	}
	
	private Map<String,CorporateActionsFinal> getAllCAForSecurity(String securityId,String effectiveDate,String source)
	{		
		Map<String,CorporateActionsFinal> caMap =  new HashMap<String,CorporateActionsFinal>();
		CorporateActionsService caService = new CorporateActionsService();
		
		try 
		{
			caMap = caService.getAllCAByDateAndSecurityMap(securityId,effectiveDate.replaceAll("-", ""),source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return caMap;
	}
}