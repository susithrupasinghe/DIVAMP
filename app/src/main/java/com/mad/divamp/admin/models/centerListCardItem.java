package com.mad.divamp.admin.models;

public class centerListCardItem {
    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getInchargeFullName() {
        return inchargeFullName;
    }

    public void setInchargeFullName(String inchargeFullName) {
        this.inchargeFullName = inchargeFullName;
    }

    public String getInchargeEmail() {
        return inchargeEmail;
    }

    public void setInchargeEmail(String inchargeEmail) {
        this.inchargeEmail = inchargeEmail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    private String centerName;
    private String inchargeFullName;
    private String inchargeEmail;
    private String contact;
    private String documentId;
}
