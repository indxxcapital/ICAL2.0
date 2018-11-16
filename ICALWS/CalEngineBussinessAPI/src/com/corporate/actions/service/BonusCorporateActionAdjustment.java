package com.corporate.actions.service;

public class BonusCorporateActionAdjustment extends CorporateActionsAdjustments
{

	Double value = (double) 0;
	
	public BonusCorporateActionAdjustment(Double aValue)
	{
		this.value = aValue;
	}
	
	public Double getPriceFactor()
	{
		sharePriceAdjustmentFactor = 1 /((100+value)/100);
		return sharePriceAdjustmentFactor;
	}
	
	public Double getShareFactor()
	{
		shareAdjustmentFactor = (100+value)/100;
		return shareAdjustmentFactor;
	}
}
