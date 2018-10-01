package com.Bean;

public class IndexSecurityCompBean 
{
	private String indexTicker;
 	private Integer securityId;
	private String bbgTicker;
	private String fullName;
	private String isin;
	private String sedol;
	private String cusip;
	private String country;
	private Integer shares;
	private float weight;
	private float securityPrice;
	private float currencyFactor;
    private String securityCurrency;
    private float securityIndexValue;
    
    public String getIndexCode() {
        return indexTicker;
    }
    public void setIndexCode(String aIndexCode) {
        this.indexTicker = aIndexCode;
    }
    
    public Integer getSecurityId() {
        return securityId;
    }
    public void setSecurityId(Integer aSecurityId) {
        this.securityId = aSecurityId;
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
    
    public float getWeight() {
        return weight;
    }
    public void setWeight(float aWeight) {
        this.weight = aWeight;
    }
    
    public Integer getShares() {
        return shares;
    }
    public void setShares(Integer aShares) {
        this.shares= aShares;
    }
}
