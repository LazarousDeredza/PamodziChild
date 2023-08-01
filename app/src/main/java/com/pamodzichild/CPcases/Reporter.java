package com.pamodzichild.CPcases;

public class Reporter {
    String reporterName,reporterPhoneNo,reporterEmail,age,reporterGender,anyserviceProvided,
            reporterLocation,reporterOccupation;

    public Reporter() {
    }

    public Reporter(String reporterName, String reporterPhoneNo, String reporterEmail, String age, String reporterGender, String anyserviceProvided, String reporterLocation, String reporterOccupation) {
        this.reporterName = reporterName;
        this.reporterPhoneNo = reporterPhoneNo;
        this.reporterEmail = reporterEmail;
        this.age = age;
        this.reporterGender = reporterGender;
        this.anyserviceProvided = anyserviceProvided;
        this.reporterLocation = reporterLocation;
        this.reporterOccupation = reporterOccupation;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterPhoneNo() {
        return reporterPhoneNo;
    }

    public void setReporterPhoneNo(String reporterPhoneNo) {
        this.reporterPhoneNo = reporterPhoneNo;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getReporterGender() {
        return reporterGender;
    }

    public void setReporterGender(String reporterGender) {
        this.reporterGender = reporterGender;
    }

    public String getAnyserviceProvided() {
        return anyserviceProvided;
    }

    public void setAnyserviceProvided(String anyserviceProvided) {
        this.anyserviceProvided = anyserviceProvided;
    }

    public String getReporterLocation() {
        return reporterLocation;
    }

    public void setReporterLocation(String reporterLocation) {
        this.reporterLocation = reporterLocation;
    }

    public String getReporterOccupation() {
        return reporterOccupation;
    }

    public void setReporterOccupation(String reporterOccupation) {
        this.reporterOccupation = reporterOccupation;
    }
}
