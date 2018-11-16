package com.Bean;

public class CACMBean 
{
	private Integer id; 	
	private Integer securityId;
	
 	private String ISIN;
 	private String BBGTicker;
 	private String Name;
 	
 	private Double grossAmountEDI = (double) 0;
 	private String currencyEDI;
 	private String eventCodeEDI;
 	private Double ratioEDI = (double) 0;
 	private String oldValueEDI;
 	private String newValueEDI;
 	
 	private Double grossAmountFDS = (double) 0;
 	private String currencyFDS;
 	private String eventCodeFDS;
 	private Double ratioFDS = (double) 0;
 	private String oldValueFDS;
 	private String newValueFDS;
 	
 	private String check;
 	
 	private String uniqueISIN;
 	
// 	private Double differenceInAmount = (double) 0;
// 	private Double differenceInRatio = (double) 0;
 	
 	public Integer getId(){
 		return id;
 	}
 	public void setId(Integer aId){
 		this.id = aId;
 	}
 	
 	public Integer getSecurityId(){
 		return securityId;
 	}
 	public void setSecurityId(Integer aSecurityId){
 		this.securityId = aSecurityId;
 	}
 	
 	public String getISIN(){
        return ISIN;
    }
    public void setISIN(String aISIN){
        this.ISIN = aISIN;
    } 
    
    public String getBBGTicker(){
        return BBGTicker;
    }
    public void setBBGTicker(String aBBGTicker){
        this.BBGTicker = aBBGTicker;
    } 
    
    public String getName(){
        return Name;
    }
    public void setName(String aName){
        this.Name = aName;
    } 
             
    public Double getGrossAmountEDI(){
        return grossAmountEDI;
    }
    public void setGrossAmountEDI(Double aGrossAmountEDI){
        this.grossAmountEDI = aGrossAmountEDI;
    } 
    
    public String getCurrencyEDI(){
        return currencyEDI;
    }
    public void setCurrencyEDI(String acurrencyEDI){
        this.currencyEDI = acurrencyEDI;
    }
    
    public String getEventCodeEDI(){
    	return eventCodeEDI;
    }
    public void setEventCodeEDI(String aEventCodeEDI){
    	this.eventCodeEDI = aEventCodeEDI;
    }
     
    public Double getGrossAmountFDS(){
        return grossAmountFDS;
    }
    public void setGrossAmountFDS(Double aGrossAmountFDS){
        this.grossAmountFDS = aGrossAmountFDS;
    }
    
    public String getCurrencyFDS(){
    	return currencyFDS;
    }
    public void setCurrencyFDS(String acurrencyFDS){
    	this.currencyFDS = acurrencyFDS;
    }
     
    public String getOldValueEDI(){
    	return oldValueEDI;
    }
    public void setOldValueEDI(String aOldValueEDI){
    	this.oldValueEDI = aOldValueEDI;
    }
    
    public String getNewValueEDI(){
    	return newValueEDI;
    }
    public void setNewValueEDI(String aNewValueEDI){
    	this.newValueEDI = aNewValueEDI;
    }
    
    
    public String getOldValueFDS(){
    	return oldValueFDS;
    }
    public void setOldValueFDS(String aOldValueFDS){
    	this.oldValueFDS = aOldValueFDS;
    }
    
    public String getNewValueFDS(){
    	return newValueFDS;
    }
    public void setNewValueFDS(String aNewValueFDS){
    	this.newValueFDS = aNewValueFDS;
    }
    
    public String getEventCodeFDS(){
    	return eventCodeFDS;
    }
    public void setEventCodeFDS(String aEventCodeFDS){
    	this.eventCodeFDS = aEventCodeFDS;
    }
    
    public String getCheck(){
        return check;
    }
    public void setCheck(String aCheck){
        this.check = aCheck;
    } 
    
    public Double getDifferenceInAmount(){
        return grossAmountEDI-grossAmountFDS;
    }
//    public void setDifferenceInAmount(Double aDifferenceInAmount){
//        this.differenceInAmount = aDifferenceInAmount;
//    } 
    
    public String getUniqueISIN(){
        return uniqueISIN;
    }
    public void setUniqueISIN(String aUniqueISIN){
        this.uniqueISIN = aUniqueISIN;
    } 
    
    public Double getRatioEDI(){
        return ratioEDI;
    }
    public void setRatioEDI(Double aRatioEDI){
        this.ratioEDI = aRatioEDI;
    } 
    
    public Double getRatioFDS(){
        return ratioFDS;
    }
    public void setRatioFDS(Double aRatioFDS){
        this.ratioFDS = aRatioFDS;
    } 
    
    public Double getDifferenceInRatio(){
        return ratioEDI-ratioFDS;
    }   
}