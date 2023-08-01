package com.pamodzichild.Donations;

public class ModelSeeker {

    String firstname;
    String lastname;
    String email;
    String phone;
    String photo;
    String countryCodeName;
    String country;
    String phoneSuffix;
    String phonePrefix;
    String idNumberOrPassport;
    String address;
    String dob;
    String gender;
    String occupation;
    String seekerId;
    String yourDescription;
    String reason;
    String education;

    String date;
    String time;
    String donorId;

    public ModelSeeker() {
    }



    public ModelSeeker(String firstname, String lastname, String email, String phone, String photo, String countryCodeName, String country, String phoneSuffix, String phonePrefix, String idNumberOrPassport, String address, String dob, String gender, String occupation, String seekerId, String yourDescription, String reason, String date, String time) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.countryCodeName = countryCodeName;
        this.country = country;
        this.phoneSuffix = phoneSuffix;
        this.phonePrefix = phonePrefix;
        this.idNumberOrPassport = idNumberOrPassport;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.occupation = occupation;
        this.seekerId = seekerId;
        this.yourDescription = yourDescription;
        this.reason = reason;
        this.date = date;
        this.time = time;
    }

    public ModelSeeker(String firstname, String lastname, String email, String phone, String photo, String countryCodeName, String country, String phoneSuffix, String phonePrefix, String idNumberOrPassport, String address, String dob, String gender, String occupation, String seekerId, String yourDescription, String reason, String education, String date, String time) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.countryCodeName = countryCodeName;
        this.country = country;
        this.phoneSuffix = phoneSuffix;
        this.phonePrefix = phonePrefix;
        this.idNumberOrPassport = idNumberOrPassport;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.occupation = occupation;
        this.seekerId = seekerId;
        this.yourDescription = yourDescription;
        this.reason = reason;
        this.education = education;
        this.date = date;
        this.time = time;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCountryCodeName() {
        return countryCodeName;
    }

    public void setCountryCodeName(String countryCodeName) {
        this.countryCodeName = countryCodeName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneSuffix() {
        return phoneSuffix;
    }

    public void setPhoneSuffix(String phoneSuffix) {
        this.phoneSuffix = phoneSuffix;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getIdNumberOrPassport() {
        return idNumberOrPassport;
    }

    public void setIdNumberOrPassport(String idNumberOrPassport) {
        this.idNumberOrPassport = idNumberOrPassport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(String seekerId) {
        this.seekerId = seekerId;
    }

    public String getYourDescription() {
        return yourDescription;
    }

    public void setYourDescription(String yourDescription) {
        this.yourDescription = yourDescription;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ModelSeeker{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", countryCodeName='" + countryCodeName + '\'' +
                ", country='" + country + '\'' +
                ", phoneSuffix='" + phoneSuffix + '\'' +
                ", phonePrefix='" + phonePrefix + '\'' +
                ", idNumberOrPassport='" + idNumberOrPassport + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", occupation='" + occupation + '\'' +
                ", seekerId='" + seekerId + '\'' +
                ", yourDescription='" + yourDescription + '\'' +
                ", reason='" + reason + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
