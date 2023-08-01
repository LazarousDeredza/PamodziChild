package com.pamodzichild.Sponsorship;

import com.pamodzichild.Users.UserModel;

import java.io.Serializable;

public class ModelSponsorship implements Serializable {
    String id;
    String sponsorId;
    String sponseeId;
    private UserModel sponsor;
    String date;
    String time;
    String status;
    String amount;
String duration;
String purpose;
    String dateToStart;
    String conditions;
    String currency;


    private ModelSponsee modelSponsee;


    public ModelSponsorship() {
    }

    public ModelSponsorship(String id, String sponsorId, String sponseeId, UserModel sponsor, String date, String time, String status, String amount, String duration, String purpose, String dateToStart, String conditions, String currency, ModelSponsee modelSponsee) {
        this.id = id;
        this.sponsorId = sponsorId;
        this.sponseeId = sponseeId;
        this.sponsor = sponsor;
        this.date = date;
        this.time = time;
        this.status = status;
        this.amount = amount;
        this.duration = duration;
        this.purpose = purpose;
        this.dateToStart = dateToStart;
        this.conditions = conditions;
        this.currency = currency;
        this.modelSponsee = modelSponsee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponseeId() {
        return sponseeId;
    }

    public void setSponseeId(String sponseeId) {
        this.sponseeId = sponseeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }



    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }


    public ModelSponsee getModelSponsee() {
        return modelSponsee;
    }

    public void setModelSponsee(ModelSponsee modelSponsee) {
        this.modelSponsee = modelSponsee;
    }


    public UserModel getSponsor() {
        return sponsor;
    }

    public void setSponsor(UserModel sponsor) {
        this.sponsor = sponsor;
    }

    public String getDateToStart() {
        return dateToStart;
    }

    public void setDateToStart(String dateToStart) {
        this.dateToStart = dateToStart;
    }
}
