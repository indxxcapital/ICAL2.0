package com.corporate.actions.service;

public class SplitCorporateActionAdjustment extends CorporateActionsAdjustments
{
	Double ratio = (double) 0;
	
	public SplitCorporateActionAdjustment(Double aRatio)
	{
		this.ratio = aRatio;
	}
	
	public Double getPriceFactor()
	{
		sharePriceAdjustmentFactor = sharePriceAdjustmentFactor/ratio;
		return sharePriceAdjustmentFactor;
	}
	
	public Double getShareFactor()
	{
		shareAdjustmentFactor = shareAdjustmentFactor*ratio;
		return shareAdjustmentFactor;
	}
}