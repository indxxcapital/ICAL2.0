package com.Bean;

public class CorporateActionsFinal 
{	
//	 [id]
//	,[eventId]
//  ,[eventCode]
//  ,[ISIN]
//  ,[BBGTicker]
//  ,[effectivedate]
	
//  ,[ratioOld]
//  ,[ratioNew]
//  ,[grossAmount]
//  ,[netAmount]
//  ,[currency]
	
//  ,[exCountry]
//  ,[exCode]
//  ,[createddate]
//  ,[changeddate]
//  ,[date]
//  ,[version]
	
	private Integer id;
 	private Integer eventId;
 	private String eventCode;
 	private String ISIN;
 	private String BBGTicker;
 	private String effectivedate;
 	
 	private Double ratioOldEDI;
 	private Double ratioNewEDI;
 	private Double grossAmountEDI;
 	private Double netAmountEDI;
 	private String currencyEDI;
 	
 	private Double ratioOldFDS;
 	private Double ratioNewFDS;
 	private Double grossAmountFDS;
 	private Double netAmountFDS;
 	private String currencyFDS;
 	
 	private Double ratioOldMAN;
 	private Double ratioNewMAN;
 	private Double grossAmountMAN;
 	private Double netAmountMAN;
 	private String currencyMAN;
 	
 	
 	 public Integer getId(){
         return id;
     }
     public void setId(Integer aId){
         this.id = aId;
     }
     
     public Integer getEventId(){
         return eventId;
     }
     public void setEventId(Integer aEventId){
         this.eventId = aEventId;
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
     public String getcurrencyEDI(){
         return currencyEDI;
     }
     public void setcurrencyEDI(String acurrencyEDI){
         this.currencyEDI = acurrencyEDI;
     }
     public Double getRatioOldEDI(){
         return ratioOldEDI;
     }
     public void setRatioOldEDI(Double aRatioOldEDI){
         this.ratioOldEDI = aRatioOldEDI;
     }  
     public Double getRatioNewEDI(){
         return ratioNewEDI;
     }
     public void setRatioNewEDI(Double aRatioNewEDI){
         this.ratioNewEDI = aRatioNewEDI;
     }
     
     public Double getGrossAmountEDI(){
         return grossAmountEDI;
     }
     public void setGrossAmountEDI(Double aGrossAmountEDI){
         this.grossAmountEDI = aGrossAmountEDI;
     }  
     public Double getNetAmountEDI(){
         return netAmountEDI;
     }
     public void setNetAmountEDI(Double aNetAmountEDI){
         this.netAmountEDI = aNetAmountEDI;
     }
     
     public String getcurrencyFDS(){
         return currencyFDS;
     }
     public void setcurrencyFDS(String acurrencyFDS){
         this.currencyFDS = acurrencyFDS;
     }
     public Double getRatioOldFDS(){
         return ratioOldFDS;
     }
     public void setRatioOldFDS(Double aRatioOldFDS){
         this.ratioOldFDS = aRatioOldFDS;
     }  
     public Double getRatioNewFDS(){
         return ratioNewFDS;
     }
     public void setRatioNewFDS(Double aRatioNewFDS){
         this.ratioNewFDS = aRatioNewFDS;
     }
     
     public Double getGrossAmountFDS(){
         return grossAmountFDS;
     }
     public void setGrossAmountFDS(Double aGrossAmountFDS){
         this.grossAmountFDS = aGrossAmountFDS;
     }  
     public Double getNetAmountFDS(){
         return netAmountFDS;
     }
     public void setNetAmountFDS(Double aNetAmountFDS){
         this.netAmountFDS = aNetAmountFDS;
     }
     
     public String getcurrencyMAN(){
         return currencyMAN;
     }
     public void setcurrencyMAN(String acurrencyMAN){
         this.currencyMAN = acurrencyMAN;
     }
     
     public Double getRatioOldMAN(){
         return ratioOldMAN;
     }
     public void setRatioOldMAN(Double aRatioOldMAN){
         this.ratioOldMAN = aRatioOldMAN;
     }  
     public Double getRatioNewMAN(){
         return ratioNewMAN;
     }
     public void setRatioNewMAN(Double aRatioNewMAN){
         this.ratioNewMAN = aRatioNewMAN;
     }
     
     public Double getGrossAmountMAN(){
         return grossAmountMAN;
     }
     public void setGrossAmountMAN(Double aGrossAmountMAN){
         this.grossAmountMAN = aGrossAmountMAN;
     }  
     public Double getNetAmountMAN(){
         return netAmountMAN;
     }
     public void setNetAmountMAN(Double aNetAmountMAN){
         this.netAmountMAN = aNetAmountMAN;
     }
}