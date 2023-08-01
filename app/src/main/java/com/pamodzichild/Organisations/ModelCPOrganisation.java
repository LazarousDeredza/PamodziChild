package com.pamodzichild.Organisations;

public class ModelCPOrganisation {
    String id;
    String name, website, logo, phone, whatsapp, email;
    String country;

    public ModelCPOrganisation() {
    }

    public ModelCPOrganisation(String name, String website, String logo, String phone, String whatsapp, String email) {
        this.name = name;
        this.website = website;
        this.logo = logo;
        this.phone = phone;
        this.whatsapp = whatsapp;
        this.email = email;
    }


    public ModelCPOrganisation(String id, String name, String website, String logo, String phone, String whatsapp, String email, String country) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.logo = logo;
        this.phone = phone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ModelCPOrganisation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", logo='" + logo + '\'' +
                ", phone='" + phone + '\'' +
                ", whatsapp='" + whatsapp + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
