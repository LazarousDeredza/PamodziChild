package com.pamodzichild.Donations;

import java.util.ArrayList;

public class ModelDonation {
    String donationId;
    String donatedto,donatedby,donationType,productname,productDescription,date,time,
    quantity,image,status;

    String receiver;
    ArrayList<ModelSeeker> seeker;

    private ModelDonor donor;

    public ModelDonation() {
    }

    public ModelDonation(String donatedby, String donationType, String productname, String productDescription, String date, String time, String quantity, String image, String status) {
        this.donatedby = donatedby;
        this.donationType = donationType;
        this.productname = productname;
        this.productDescription = productDescription;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
    }

    public ModelDonation(String donationType, String productname, String productDescription, String date, String time, String quantity, String image, String status) {
        this.donationType = donationType;
        this.productname = productname;
        this.productDescription = productDescription;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
    }

    public ModelDonation(String donatedby, String donationType, String productname, String productDescription, String date, String time, String quantity, String image, String status, String receiver) {
        this.donatedby = donatedby;
        this.donationType = donationType;
        this.productname = productname;
        this.productDescription = productDescription;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
        this.receiver = receiver;
    }

    public ModelDonation(String donationId, String donatedto, String donatedby, String donationType, String productname, String productDescription, String date, String time, String quantity, String image, String status, String receiver) {
        this.donationId = donationId;
        this.donatedto = donatedto;
        this.donatedby = donatedby;
        this.donationType = donationType;
        this.productname = productname;
        this.productDescription = productDescription;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
        this.receiver = receiver;
    }

    public String getDonatedto() {
        return donatedto;
    }

    public void setDonatedto(String donatedto) {
        this.donatedto = donatedto;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDonatedby() {
        return donatedby;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setDonatedby(String donatedby) {
        this.donatedby = donatedby;
    }

    public ModelDonor getDonor() {
        return donor;
    }

    public void setDonor(ModelDonor donor) {
        this.donor = donor;
    }

    public ArrayList<ModelSeeker> getSeeker() {
        return seeker;
    }

    public void setSeeker(ArrayList<ModelSeeker> seeker) {
        this.seeker = seeker;
    }

    @Override
    public String toString() {
        return "ModelDonation{" +
                "donatedby='" + donatedby + '\'' +
                ", donationType='" + donationType + '\'' +
                ", productname='" + productname + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", quantity='" + quantity + '\'' +
                ", status='" + status + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }


}
