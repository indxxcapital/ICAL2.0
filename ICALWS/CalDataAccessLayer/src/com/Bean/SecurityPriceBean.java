package com.Bean;

import java.util.Date;

public class SecurityPriceBean
{
	
//	 `id` int(25) NOT NULL AUTO_INCREMENT, PRIMARY KEY (`id`),
//	  `securityId` int(25) DEFAULT NULL,
//	  `vd` date DEFAULT NULL,
//	  `closePrice` float DEFAULT NULL,
//	  `crrysymbol` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	
	private Integer id;
 	private String securityId;
 	private String vd;
 	private Float closePrice;
    private String crrySymbol;
    
    private String isin;
    private String bbgTicker;
    
    public String getVd() {
        return vd;
    }
    public void setVd(String aVd) {
        this.vd = aVd;
    }
//    public Date getVd() {
//        return vd;
//    }
//    public void setVd(Date aVd) {
//        this.vd = aVd;
//    }
    
    public Float getCurrencyPrice() {
        return closePrice;
    }
    public void setCurrencyPrice(Float aCrrySymbol) {
        this.closePrice = aCrrySymbol;
    }
    
    public String getCurrencySymbol() {
        return crrySymbol;
    }
    public void setCurrencySymbol(String aCrrySymbol) {
        this.crrySymbol = aCrrySymbol;
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
}
