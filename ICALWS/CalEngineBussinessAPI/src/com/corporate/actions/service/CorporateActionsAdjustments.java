package com.corporate.actions.service;

public class CorporateActionsAdjustments
{

//	public Double divisorAdjustmentFactor = (double) 0;
	public Double shareAdjustmentFactor = (double) 1;
	public Double sharePriceAdjustmentFactor = (double) 1;
//	public Double baseShares = (double) 1;
	public Double basePrice = (double) 1;
	
	public CorporateActionsAdjustments()
	{
	}
	
	public Double getPriceFactor()
	{
		
		return sharePriceAdjustmentFactor;
	}
	
	public Double getShareFactor()
	{
		return shareAdjustmentFactor;
	}
	
//	public Double getDivisorFactor()
//	{
//		
//		return divisorAdjustmentFactor;
//	}
}
