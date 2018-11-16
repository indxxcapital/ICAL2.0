package com.corporate.actions.service;

public class StockDividendCorporateActionsAdjustments extends CorporateActionsAdjustments
{
	Double ratio = (double) 0;
	
	public StockDividendCorporateActionsAdjustments(Double aRatio)
	{
		this.ratio = aRatio;
	}
	
	public Double getPriceFactor()
	{
		sharePriceAdjustmentFactor = sharePriceAdjustmentFactor/(1+ratio);
		return sharePriceAdjustmentFactor;
	}
	
	public Double getShareFactor()
	{
		shareAdjustmentFactor = shareAdjustmentFactor*(1+ratio);
		return shareAdjustmentFactor;
	}
}