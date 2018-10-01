package com.corporate.actions.service;

import com.Service.CAEnum;

public class CorporateActionsAdjustments {

	public void adjustCorporateAction(String eventCode)
	{
		
		CAEnum CA = CAEnum.valueOf(eventCode);
	        
//		CAEnum CA = CAEnum.DELIST;
		
		switch(CA)
		{
			case SPIN_OFF:
				adjustSpinOff();
			break;
			
			case BANKRUPTCY:
				adjustBankruptcy();
				break;
				
			case DVD_STOCK:
				adjustDVDStock();
				break;
				
			case RIGHTS_OFFER:
				adjustRightsOffer();
				break;
				
			case STOCK_SPLIT:
				adjustStockSplit();
				break;
				
			case DIV_1:
				adjustDIV1();
				break;
				
			case TICKER_CHANGE:
				adjustTickerChange();
				break;
				
			case MERGER:
				adjustMerger();
				break;
				
			case NEW_LISTING:
				adjustNewListing();
				break;
				
			case PR_EXCH_CHG:
				adjustPrimaryExchangeChange();
				break;
		
			case DVD_CASH:
				adjustDIVCash();
				break;
				
			case SEC_RECLASSIFY:
				adjustSecurityReclassification();
				break;
				
			case ACQUISITION:
				adjustAcquisition();
				break;		
		}				
			
	}

	protected void adjustBankruptcy() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustDVDStock() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustRightsOffer() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustStockSplit() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustDIV1() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustTickerChange() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustMerger() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustNewListing() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustPrimaryExchangeChange() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustDIVCash() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustSecurityReclassification() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustAcquisition() {
		// TODO Auto-generated method stub
		
	}

	protected void adjustSpinOff() {
		// TODO Auto-generated method stub
		
	}	
}
