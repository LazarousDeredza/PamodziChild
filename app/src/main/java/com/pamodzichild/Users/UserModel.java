package com.pamodzichild.Users;

import androidx.annotation.NonNull;

public class UserModel {

    String fname,  lname,  user_email,  user_password,  mobile,  localaddress,  role,phonePrefix,phoneSuffix,country,countyCodeName;


    String photo;
    String dateRegistered;
    String id;
    public UserModel() {
    }

    public UserModel(String fname, String lname, String user_email, String user_password, String mobile, String localaddress, String role, String phonePrefix, String phoneSuffix, String country, String countyCodeName) {
        this.fname = fname;
        this.lname = lname;
        this.user_email = user_email;
        this.user_password = user_password;
        this.mobile = mobile;
        this.localaddress = localaddress;
        this.role = role;
        this.phonePrefix = phonePrefix;
        this.phoneSuffix = phoneSuffix;
        this.country = country;
        this.countyCodeName = countyCodeName;
    }

    public UserModel(String fname, String lname, String user_email, String user_password, String mobile, String localaddress, String role) {
        this.fname = fname;
        this.lname = lname;
        this.user_email = user_email;
        this.user_password = user_password;
        this.mobile = mobile;
        this.localaddress = localaddress;
        this.role = role;
    }

    public UserModel(String fname, String lname, String user_email, String user_password, String mobile, String localaddress, String role, String phonePrefix, String phoneSuffix) {
        this.fname = fname;
        this.lname = lname;
        this.user_email = user_email;
        this.user_password = user_password;
        this.mobile = mobile;
        this.localaddress = localaddress;
        this.role = role;
        this.phonePrefix = phonePrefix;
        this.phoneSuffix = phoneSuffix;
    }

    public UserModel(String fname, String lname, String user_email, String user_password, String mobile, String localaddress, String role, String phonePrefix, String phoneSuffix, String country, String countyCodeName, String photo) {
        this.fname = fname;
        this.lname = lname;
        this.user_email = user_email;
        this.user_password = user_password;
        this.mobile = mobile;
        this.localaddress = localaddress;
        this.role = role;
        this.phonePrefix = phonePrefix;
        this.phoneSuffix = phoneSuffix;
        this.country = country;
        this.countyCodeName = countyCodeName;
        this.photo = photo;
    }

    public UserModel(String fname, String lname, String user_email, String user_password, String mobile, String localaddress, String role, String phonePrefix, String phoneSuffix, String country, String countyCodeName, String photo, String dateRegistered, String id) {
        this.fname = fname;
        this.lname = lname;
        this.user_email = user_email;
        this.user_password = user_password;
        this.mobile = mobile;
        this.localaddress = localaddress;
        this.role = role;
        this.phonePrefix = phonePrefix;
        this.phoneSuffix = phoneSuffix;
        this.country = country;
        this.countyCodeName = countyCodeName;
        this.photo = photo;
        this.dateRegistered = dateRegistered;
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCountyCodeName() {
        return countyCodeName;
    }

    public void setCountyCodeName(String countyCodeName) {
        this.countyCodeName = countyCodeName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocaladdress() {
        return localaddress;
    }

    public void setLocaladdress(String localaddress) {
        this.localaddress = localaddress;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getPhoneSuffix() {
        return phoneSuffix;
    }

    public void setPhoneSuffix(String phoneSuffix) {
        this.phoneSuffix = phoneSuffix;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_password='" + user_password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", localaddress='" + localaddress + '\'' +
                ", role='" + role + '\'' +
                ", phonePrefix='" + phonePrefix + '\'' +
                ", phoneSuffix='" + phoneSuffix + '\'' +
                ", country='" + country + '\'' +
                ", countyCodeName='" + countyCodeName + '\'' +
                ", photo='" + photo + '\'' +
                ", dateRegistered='" + dateRegistered + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
