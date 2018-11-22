package com.Bean;

public class CorporateActionsFinal 
{	
	private Integer id;
 	
 	private String source;
 	private String ISIN;
 	private String BBGTicker;
 	private Integer securityId;
 	private String eventCode;
 	private String effectivedate;
 	
 	private Double grossAmount;
 	private Double netAmount;
 	private String currency;
 	
 	private Double ratio;
 	
 	private String oldValue;
 	private String newValue;
 	
 	private String flag;
	private String hasDifference;
	private String isPrimaryOnly;
	private String iconName;
	private boolean allowEdit = false;
	private String Name;
 	public Integer getId(){
 		return id;
 	} 	 
 	public void setId(Integer aId){
 		this.id = aId;
 	}
 	 
 	public String getSource(){
        return source;
    }
    public void setSource(String aSource){
        this.source = aSource;
    }   
    
     public Integer getSecurityId(){
         return securityId;
     }
    
     public void setSecurityId(Integer aSecurityId){
         this.securityId = aSecurityId;
     }
     
     public String getEventCode(){
         return eventCode;
     }
     public void setEventCode(String aEventCode){
         this.eventCode = aEventCode;
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
     
     public String getEffectiveDate(){
         return effectivedate;
     }
     public void setEffectiveDate(String aEffectiveDate){
         this.effectivedate = aEffectiveDate;
     }  
     public String getCurrency(){
         return currency;
     }
     public void setCurrency(String acurrency){
         this.currency = acurrency;
     }
     public Double getRatio(){
         return ratio;
     }
     public void setRatio(Double aRatio){
         this.ratio = aRatio;
     }  
     
     public Double getGrossAmount(){
         return grossAmount;
     }
     public void setGrossAmount(Double aGrossAmount){
         this.grossAmount = aGrossAmount;
     }  
     public Double getNetAmount(){
         return netAmount;
     }
     public void setNetAmount(Double aNetAmount){
         this.netAmount = aNetAmount;
     }
     
     public String getOldValue(){
         return oldValue;
     }
     public void setOldValue(String aOldValue){
         this.oldValue = aOldValue;
     } 
     
     public String getNewValue(){
         return newValue;
     }
     public void setNewValue(String aNewValue){
         this.newValue = aNewValue;
     } 
     
     public String getFlag(){
         return flag;
     }
     public void setFlag(String aFlag){
         this.flag = aFlag;
     }   
     
     //0 for no difference 1 for difference
     public String getHasDifference(){
         return hasDifference;
     }
     public void setHasDifference(String aHasDifference){
         this.hasDifference = aHasDifference;
     } 
     
     // 0 for primary only 1 for both
     public String getIsPrimaryOnly(){
         return isPrimaryOnly;
     }
     public void setIsPrimaryOnly(String aIsPrimaryOnly){
         this.isPrimaryOnly = aIsPrimaryOnly;
     } 
     
     // 1 for allow 0 for not allow
     public boolean getAllowEdit(){
         return allowEdit;
     }
     public void setAllowEdit(boolean aAllowEdit){
         this.allowEdit = aAllowEdit;
     }
     
     public String getIconName(){
         return iconName;
     }
     public void setIconName(String aIconName){
         this.iconName = aIconName;
     } 
     
     public String getName(){
         return Name;
     }
     public void setName(String aName){
         this.Name = aName;
     }       
}