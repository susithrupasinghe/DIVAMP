package com.mad.divamp.location;

public class location {

    private String email,Location_name,NIC,fullName,contactNo,category,Address_1,Address_2,password;

    public location() {
        // empty constructor
        // required for Firebase.
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation_name() {
        return Location_name;
    }

    public void setLocation_name(String location_name) {
        Location_name = location_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress_1() {
        return Address_1;
    }

    public void setAddress_1(String address_1) {
        Address_1 = address_1;
    }

    public String getAddress_2() {
        return Address_2;
    }

    public void setAddress_2(String address_2) {
        Address_2 = address_2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public location(String category, String Address_1, String Address_2, String email, String Location_name, String NIC, String fullName, String contactNo, String password){
        this.email=email;
        this.Location_name = Location_name;
        this.category = category;
        this.Address_1=Address_1;
        this.Address_2 = Address_2;
        this.NIC = NIC;
        this.fullName = fullName;
        this.contactNo = contactNo;
        this.password = password;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
