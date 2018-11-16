package com.Bean;

import java.util.Date;

public class SecurityBean {

//	  `id` int(25) NOT NULL AUTO_INCREMENT,
//	  `securityid` int(11) NOT NULL,
//	  `ISIN` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `BBGTicker` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `fullName` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `SEDOL` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `CUSIP` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `Country` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `Sector` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `Industry` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `Subindustry` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `flag` float DEFAULT NULL,
//	  `vf` date DEFAULT NULL,
//	  `vt` date DEFAULT NULL,
//	  `vd` date DEFAULT NULL,
	  
	private Integer id;	 	
    private String isin;
    private String bbgTicker;
    private String fullName;
    private String sedol;
    private String cusip;
    private String country;
    private String sector;
    private String industry;
    private String subindustry;	
    private Integer flag;
    private Date vf;
    private Date vt;
    private Date vd;
    
    private String indexCode;
    private float weight = 0;
    private Float shares = (float) 0; 
    
    private String currency;
    private float price;
    private float currencyFactor;

    private String dividend;   
    
    public SecurityBean() {
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(int aId) {
        id = aId;
    }
//    
    public Integer getFlag() {
        return flag;
    }
    
    public void setFlag(int aFlag) {
        flag = aFlag;
    }
    	    
    public String getIndexCode() {
        return indexCode;
    }
    public void setIndexCode(String aIndexCode) {
        this.indexCode = aIndexCode;
    }
    
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String aFullName) {
        this.fullName = aFullName;
    }
    
    public String getISIN() {
        return isin;
    }
    public void setISIN(String aISIN) {
        this.isin = aISIN;
    }
    
    public String getBBGTicker() {
        return bbgTicker;
    }
    public void setBBGTicker(String aBBGTicker) {
        this.bbgTicker = aBBGTicker;
    }
    
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String aCurrency) {
        this.currency= aCurrency;
    }
    
    public String getDividend() {
        return dividend;
    }
    public void setDividend(String aDividend) {
        this.dividend = aDividend;
    }
    
    public String getSEDOL() {
        return sedol;
    }
    public void setSEDOL(String aSEDOL) {
        this.sedol = aSEDOL;
    }
    
    public String getCUSIP() {
        return cusip;
    }
    public void setCUSIP(String aCUSIP) {
        this.cusip = aCUSIP;
    }
    
    public String getCountry() {
        return country;
    }
    public void setCountry(String aCountry) {
        this.country = aCountry;
    }
    
    public String getSector() {
        return sector;
    }
    public void setSector(String aSector) {
        this.sector = aSector;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String aIndustry) {
        this.industry = aIndustry;
    }
    public String getSubIndustry() {
        return subindustry;
    }
    public void setSubIndustry(String aSubindustry) {
        this.subindustry = aSubindustry;
    }
    
    public float getWeight() {
        return weight;
    }
    public void setWeight(float aWeight) {
        this.weight = aWeight;
    }
    
    public Float getShares() {
        return shares;
    }
    public void setShares(Float aShares) {
        this.shares= aShares;
    }
    
    public Date getVF() {
        return vf;
    }
    public void setVF(Date aVf) {
        this.vf = aVf;
    }
    public Date getVT() {
        return vt;
    }
    public void setVT(Date aVT) {
       this.vt = aVT;
    }  
    
    public Date getVD() {
        return vd;
    }
    public void setVD(Date aVD) {
        this.vd = aVD;
    }
    
    public float getPrice() {
        return price;
    }
    public void setPrice(float aPrice) {
        this.price = aPrice;
    }

    public float getCurrencyFactor() {
        return currencyFactor;
    }
    public void setCurrencyFactor(float aCurrencyFactor) {
        this.currencyFactor = aCurrencyFactor;
    }
}
