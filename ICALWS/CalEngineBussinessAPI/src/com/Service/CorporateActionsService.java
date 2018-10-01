package com.Service;

public class CorporateActionsService 
{
	
	public void adjustCorporateAction(String eventCode)
	{
		
		CAEnum CA = CAEnum.valueOf(eventCode);
	        
//		CAEnum CA = CAEnum.DELIST;
		
		switch(CA)
		{
			case SPIN_OFF:
			break;
			
			case BANKRUPTCY:
				break;
				
			case DVD_STOCK:
				break;
				
			case RIGHTS_OFFER:
				break;
				
			case STOCK_SPLIT:
				break;
				
			case DIV_1:
				break;
				
			case TICKER_CHANGE:
				break;
				
			case MERGER:
				break;
				
			case NEW_LISTING:
				break;
				
			case PR_EXCH_CHG:
				break;
		
			case DVD_CASH:
				break;
				
			case SEC_RECLASSIFY:
				break;
				
			case ACQUISITION:
				break;		
		}				
			
	}

}
