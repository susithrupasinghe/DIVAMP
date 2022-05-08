package com.mad.divamp.admin.models;

public class registrationModel {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCenterName() {
        return CenterName;
    }

    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInchargeNIC() {
        return inchargeNIC;
    }

    public void setInchargeNIC(String inchargeNIC) {
        this.inchargeNIC = inchargeNIC;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getInchargeEmail() {
        return inchargeEmail;
    }

    public void setInchargeEmail(String inchargeEmail) {
        this.inchargeEmail = inchargeEmail;
    }

    private String email;
    private String CenterName;
    private String province;
    private String district;
    private String password;
    private String inchargeNIC;
    private  String contactNo;
    private  String inchargeEmail;

}
