package com.Bean;

import java.util.Date;

public class IndexBean {

//	 `id` int(11) NOT NULL AUTO_INCREMENT,
//	  `indexTicker` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `indexName` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `zoneType` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `indexType` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `indexLiveDate` date DEFAULT NULL,
//	  `specialCashDivAdj` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `normalCashDivAdj` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `clientName` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `currency` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `disseminationSource` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `status` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `OutputFileFormat` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `indexWeightType` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
//	  `IndexMarketValue` float DEFAULT NULL,
//	  `CloseIndexValue` float DEFAULT NULL,
//	  `RunIndexDate` date DEFAULT NULL,
//	  `vf` date DEFAULT NULL,
//	  `vt` date DEFAULT NULL,
//	  `flag` float NOT NULL,
	
	private Integer id;
 	private String indexTicker;
 	private String indexName;
    private String zoneType;
    private String indexType;
    private String indexLiveDate;
    private String indexLiveDateStr;
    
    private String specialCashDivAdj;
    private String normalCashDivAdj;
    private String clientName;    
    private String currency;
    
    private String disseminationSource;
//    private String outputFilesFormat;
    private String status;
    
    private String indexWeightType;
    private Float indexMarketValue = (float) 0.0;
    private Float closeIndexValue = (float) 0.0;;
    
    private float divisor = (float) 0.0;
    private float sumOfWeights = (float) 0.0;;
    
    private String indexRunDate;
    
    private Integer flag;
    private Date vf;
    private Date vt;
    
    public String getIndexWeightType() {
        return indexWeightType;
    }
    public void setIndexWeightType(String aIndexWeightType) {
        this.indexWeightType = aIndexWeightType;
    }
    
    public Float getIndexMarketValue() {
        return indexMarketValue;
    }
    public void setIndexMarketValue(Float aIndexMarketValue) {
        this.indexMarketValue = aIndexMarketValue;
    }
    
    public Float getCloseIndexValue() {
        return closeIndexValue;
    }
    public void setCloseIndexValue(Float aCloseIndexValue) {
        this.closeIndexValue = aCloseIndexValue;
    }
    
    public Float getIndexDivisor() {
        return divisor;
    }
    public void setIndexDivisor(Float aDivisor) {
        this.divisor = aDivisor;
    }
    
    public Float getSumOfWeights() {
        return sumOfWeights;
    }
    public void setSumOfWeights(float aSumOfWeights) {
        this.sumOfWeights = aSumOfWeights;
    }
    
    public String getIndexRunDate() {
        return indexRunDate;
    }
    public void setIndexRunDate(String aIndexRunDate) {
        this.indexRunDate= aIndexRunDate;
    }
    
    public IndexBean() {
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(int aId) {
        id = aId;
    }
    
    public String getIndexTicker() {
        return indexTicker;
    }
    public void setIndexTicker(String aIndexTicker) {
        this.indexTicker = aIndexTicker;
    }
    
    public String getIndexName() {
        return indexName;
    }
    public void setIndexlName(String aIndexName) {
        this.indexName = aIndexName;
    }
    
    public String getZoneType() {
        return zoneType;
    }
    public void setZoneType(String aZoneType) {
        this.zoneType = aZoneType;
    }
    
    public String getIndexType() {
        return indexType;
    }
    public void setIndexType(String aIndexType) {
        this.indexType = aIndexType;
    }
    
    public String getIndexLiveDate() {
        return indexLiveDate;
    }
    
    public String getIndexLiveDateStr() {
        return indexLiveDateStr;
    }
    
    public void setIndexLiveDate(String aIndexLiveDate) {
        this.indexLiveDate= aIndexLiveDate;
    }
    
    public void setIndexLiveDateStr(String aIndexLiveDate) {
        this.indexLiveDateStr= aIndexLiveDate;
    }
    
    public String getSpecialCashDivAdj() {
        return specialCashDivAdj;
    }
    public void setSpecialCashDivAdj(String aSpecialCashDivAdj) {
        this.specialCashDivAdj = aSpecialCashDivAdj;
    }
    
    public String getNormalCashDivAdj() {
        return normalCashDivAdj;
    }
    public void setNormalCashDivAdj(String aNormalCashDivAdj) {
        this.normalCashDivAdj = aNormalCashDivAdj;
    }
    
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String aClientName) {
        this.clientName = aClientName;
    }
    
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String aCurrency) {
        this.currency = aCurrency;
    }
    
    public String getDisseminationSource() {
        return disseminationSource;
    }
    public void setDisseminationSource(String aDisseminationSource) {
        this.disseminationSource = aDisseminationSource;
    }
    
//    public String getOutputFilesFormat() {
//        return outputFilesFormat;
//    }
//    public void setOutputFilesFormat(String aOutputFilesFormat) {
//        this.outputFilesFormat = aOutputFilesFormat;
//    }
    //
    public String getStatus() {
        return status;
    }
    public void setStatus(String aStatus) {
        this.status = aStatus;
    }
    
    public Integer getFlag() {
        return flag;
    }
    
    public void setFlag(int aFlag) {
        flag = aFlag;
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
}
