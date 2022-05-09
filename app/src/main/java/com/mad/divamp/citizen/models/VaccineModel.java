package com.mad.divamp.citizen.models;

public class VaccineModel {

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

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

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private String NIC;
    private String Center;
    private String BatchId;
    private String vaccineName;
    private long timestamp;

}
