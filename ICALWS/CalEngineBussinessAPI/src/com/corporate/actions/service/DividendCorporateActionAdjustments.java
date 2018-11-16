package com.corporate.actions.service;

import com.Service.CurrencyService;

public class DividendCorporateActionAdjustments extends CorporateActionsAdjustments
{
//	String indexType;
	
	String indexCurrency;
	String caCurrency;
	Double amount = (double) 0;
//	Float indexValue = (float) 0;
	Boolean isDivisorAdjustment;
	
	public DividendCorporateActionAdjustments(Double aAmount, Double aBasePrice,boolean aIsDivisorAdjustment)
	{
		this.basePrice = aBasePrice;
//		this.baseShares = aBaseShares;
		this.amount = aAmount;
//		this.indexValue = aIndexValue;
		this.isDivisorAdjustment = aIsDivisorAdjustment;
	}
	
	public Double getPriceFactor()
	{
		sharePriceAdjustmentFactor = 1 - (amount/basePrice);
		return sharePriceAdjustmentFactor;
	}
	
	public Double getShareFactor()
	{
		if(!isDivisorAdjustment )
		{
			shareAdjustmentFactor = 1/(1 - (amount/basePrice));
		}
		return shareAdjustmentFactor;
	}	
	
//	
//	public Double getDivisorFactor()
//	{
//		if(isDivisorAdjustment)
//		{
//			divisorAdjustmentFactor = (amount*baseShares)/indexValue;
//		}
//		return divisorAdjustmentFactor;
//	}
}
