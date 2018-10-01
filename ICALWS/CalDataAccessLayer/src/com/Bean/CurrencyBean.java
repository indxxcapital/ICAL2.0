package com.Bean;

public class CurrencyBean 
{
	private Integer id;
 	private String crrysymbol;
 	private String crryname;
 	
 	 public Integer getCurrencyId() {
         return id;
     }
     public void setCurrencyId(Integer aId) {
         this.id = aId;
     }
     
     public String getCurrencySymbol() {
         return crrysymbol;
     }
     public void setCurrencySymbol(String aCurrencySymbol) {
         this.crrysymbol = aCurrencySymbol;
     }
     
     public String getCurrencyName() {
         return crryname;
     }
     public void setCurrencyName(String aCurrencyName) {
         this.crryname = aCurrencyName;
     }     
}
