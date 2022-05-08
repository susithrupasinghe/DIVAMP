package com.mad.divamp.citizen.models;

public class Citizen {
    private String email,contactNo,province,district,address1,address2,firstName,lastName,nic,birthday,gender,hashPassword;

    public Citizen() {
    }

    public Citizen(String email, String contactNo, String province, String district, String address1, String address2, String firstName, String lastName, String nic, String birthday, String gender, String hashPassword) {
        this.email = email;
        this.contactNo = contactNo;
        this.province = province;
        this.district = district;
        this.address1 = address1;
        this.address2 = address2;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nic = nic;
        this.birthday = birthday;
        this.gender = gender;
        this.hashPassword = hashPassword;
    }
}
