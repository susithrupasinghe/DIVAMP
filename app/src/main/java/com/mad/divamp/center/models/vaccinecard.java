package com.mad.divamp.center.models;

public class vaccinecard {


    public String getCenter() {
        return Center;
    }

    public void setCenter(String center) {
        Center = center;
    }

    public String getBatchId() {
        return BatchId;
    }

    public void setBatchId(String batchId) {
        BatchId = batchId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    private  String documentId;
    private String BatchId;
    private String logoUrl;
    private String Date;
    private String Center;

}
