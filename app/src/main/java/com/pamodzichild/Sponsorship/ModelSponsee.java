package com.pamodzichild.Sponsorship;

import java.io.Serializable;
import java.util.List;

public class ModelSponsee implements Serializable {
    String id;
    String firstname, lastname, email, phone, photo, countryCodeName, country;
    String phoneSuffix, phonePrefix, idNumber, address, dob, gender, occupation;
    String disability, bio, reason, education, status;
    String parentsAlive;

    String guardianfirstName, guardianlastName, guardianEmail, guardianPhone, guardianOccupation,
            guardianDOB, guardianGender, guardianDisability;
    String relationship;
    String dateApplied, timeApplied;


    String moreGuardianOrParentInfo;


    String currentFamilysituation,
            sourceOfIncome,
            averageMonthlyIncome,
            typeOfHouseYouLiveIn,
            numberOfPeopleInYourHouse,
            numberOfPeopleInYourHouseUnder18,
            typeOfLightYouUse,
            typeOfCookingEnergyYouUse,
            typeOfWaterYouUse,
            howFarIsTheWaterSourceFromYourHouse,
            typeOfToiletYouUse,
            typeOfFloorYouHave,
            numberOfSiblingsYouhave,
            numberOfSiblingsYouHaveInSchool,
            financialChallengesYouFace,
            circumstancesThatAffectedYourAbilityToGoToSchool,
            haveYouEverMissedSchoolDueToLackOfResources,
            howManyMealsYouEatPerDay,
            anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool,
            yourAcademicGoals,
            haveYourEverRecievedAnySponsorshipBefore,
            nameOfSponsor,
            ifYesWhatTypeOfSponsorship,
            ifYesHowLongDidYouRecieveTheSponsorship,
            ifYesWhyDidItStop,
            ifYesHowDidItHelpYou,
            ifYesHowDidYouFeelWhenItStopped,
            communityContributionIfYouRecieveSponsorship;

             List<String> evidenceOfNeed;
             String locationOfHome,
            letterFromLocalLeaderOrChief,
            anyOtherComments,
            howDidYouKnowAboutPamodziKidsFoundation;
            List<String> myImages;


    public ModelSponsee() {
    }


    public ModelSponsee(String id, String firstname, String lastname, String email, String phone, String photo, String countryCodeName, String country, String phoneSuffix, String phonePrefix, String idNumber, String address, String dob, String gender, String occupation, String disability, String bio, String reason, String education, String status, String parentsAlive, String guardianfirstName, String guardianlastName, String guardianEmail, String guardianPhone, String guardianOccupation, String guardianDOB, String guardianGender, String guardianDisability, String relationship, String dateApplied, String timeApplied, List<String> myImages) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.countryCodeName = countryCodeName;
        this.country = country;
        this.phoneSuffix = phoneSuffix;
        this.phonePrefix = phonePrefix;
        this.idNumber = idNumber;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.occupation = occupation;
        this.disability = disability;
        this.bio = bio;
        this.reason = reason;
        this.education = education;
        this.status = status;
        this.parentsAlive = parentsAlive;
        this.guardianfirstName = guardianfirstName;
        this.guardianlastName = guardianlastName;
        this.guardianEmail = guardianEmail;
        this.guardianPhone = guardianPhone;
        this.guardianOccupation = guardianOccupation;
        this.guardianDOB = guardianDOB;
        this.guardianGender = guardianGender;
        this.guardianDisability = guardianDisability;
        this.relationship = relationship;
        this.dateApplied = dateApplied;
        this.timeApplied = timeApplied;
        this.myImages = myImages;
    }

    public ModelSponsee(String id, String firstname, String lastname, String email, String phone, String photo, String countryCodeName, String country, String phoneSuffix, String phonePrefix, String idNumber, String address, String dob, String gender, String occupation, String disability, String bio, String reason, String education, String status, String parentsAlive, String guardianfirstName, String guardianlastName, String guardianEmail, String guardianPhone, String guardianOccupation, String guardianDOB, String guardianGender, String guardianDisability, String relationship, String dateApplied, String timeApplied, String moreGuardianOrParentInfo, String currentFamilysituation, String sourceOfIncome, String averageMonthlyIncome, String typeOfHouseYouLiveIn, String numberOfPeopleInYourHouse, String numberOfPeopleInYourHouseUnder18, String typeOfLightYouUse, String typeOfCookingEnergyYouUse, String typeOfWaterYouUse, String howFarIsTheWaterSourceFromYourHouse, String typeOfToiletYouUse, String typeOfFloorYouHave, String numberOfSiblingsYouhave, String numberOfSiblingsYouHaveInSchool, String financialChallengesYouFace, String circumstancesThatAffectedYourAbilityToGoToSchool, String haveYouEverMissedSchoolDueToLackOfResources, String howManyMealsYouEatPerDay, String anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool, String yourAcademicGoals, String haveYourEverRecievedAnySponsorshipBefore, String nameOfSponsor, String ifYesWhatTypeOfSponsorship, String ifYesHowLongDidYouRecieveTheSponsorship, String ifYesWhyDidItStop, String ifYesHowDidItHelpYou, String ifYesHowDidYouFeelWhenItStopped, String communityContributionIfYouRecieveSponsorship, List<String> evidenceOfNeed, String locationOfHome, String letterFromLocalLeaderOrChief, String anyOtherComments, String howDidYouKnowAboutPamodziKidsFoundation, List<String> myImages) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.countryCodeName = countryCodeName;
        this.country = country;
        this.phoneSuffix = phoneSuffix;
        this.phonePrefix = phonePrefix;
        this.idNumber = idNumber;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.occupation = occupation;
        this.disability = disability;
        this.bio = bio;
        this.reason = reason;
        this.education = education;
        this.status = status;
        this.parentsAlive = parentsAlive;
        this.guardianfirstName = guardianfirstName;
        this.guardianlastName = guardianlastName;
        this.guardianEmail = guardianEmail;
        this.guardianPhone = guardianPhone;
        this.guardianOccupation = guardianOccupation;
        this.guardianDOB = guardianDOB;
        this.guardianGender = guardianGender;
        this.guardianDisability = guardianDisability;
        this.relationship = relationship;
        this.dateApplied = dateApplied;
        this.timeApplied = timeApplied;
        this.moreGuardianOrParentInfo = moreGuardianOrParentInfo;
        this.currentFamilysituation = currentFamilysituation;
        this.sourceOfIncome = sourceOfIncome;
        this.averageMonthlyIncome = averageMonthlyIncome;
        this.typeOfHouseYouLiveIn = typeOfHouseYouLiveIn;
        this.numberOfPeopleInYourHouse = numberOfPeopleInYourHouse;
        this.numberOfPeopleInYourHouseUnder18 = numberOfPeopleInYourHouseUnder18;
        this.typeOfLightYouUse = typeOfLightYouUse;
        this.typeOfCookingEnergyYouUse = typeOfCookingEnergyYouUse;
        this.typeOfWaterYouUse = typeOfWaterYouUse;
        this.howFarIsTheWaterSourceFromYourHouse = howFarIsTheWaterSourceFromYourHouse;
        this.typeOfToiletYouUse = typeOfToiletYouUse;
        this.typeOfFloorYouHave = typeOfFloorYouHave;
        this.numberOfSiblingsYouhave = numberOfSiblingsYouhave;
        this.numberOfSiblingsYouHaveInSchool = numberOfSiblingsYouHaveInSchool;
        this.financialChallengesYouFace = financialChallengesYouFace;
        this.circumstancesThatAffectedYourAbilityToGoToSchool = circumstancesThatAffectedYourAbilityToGoToSchool;
        this.haveYouEverMissedSchoolDueToLackOfResources = haveYouEverMissedSchoolDueToLackOfResources;
        this.howManyMealsYouEatPerDay = howManyMealsYouEatPerDay;
        this.anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool = anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool;
        this.yourAcademicGoals = yourAcademicGoals;
        this.haveYourEverRecievedAnySponsorshipBefore = haveYourEverRecievedAnySponsorshipBefore;
        this.nameOfSponsor = nameOfSponsor;
        this.ifYesWhatTypeOfSponsorship = ifYesWhatTypeOfSponsorship;
        this.ifYesHowLongDidYouRecieveTheSponsorship = ifYesHowLongDidYouRecieveTheSponsorship;
        this.ifYesWhyDidItStop = ifYesWhyDidItStop;
        this.ifYesHowDidItHelpYou = ifYesHowDidItHelpYou;
        this.ifYesHowDidYouFeelWhenItStopped = ifYesHowDidYouFeelWhenItStopped;
        this.communityContributionIfYouRecieveSponsorship = communityContributionIfYouRecieveSponsorship;
        this.evidenceOfNeed = evidenceOfNeed;
        this.locationOfHome = locationOfHome;
        this.letterFromLocalLeaderOrChief = letterFromLocalLeaderOrChief;
        this.anyOtherComments = anyOtherComments;
        this.howDidYouKnowAboutPamodziKidsFoundation = howDidYouKnowAboutPamodziKidsFoundation;
        this.myImages = myImages;
    }

    public String getNameOfSponsor() {
        return nameOfSponsor;
    }

    public void setNameOfSponsor(String nameOfSponsor) {
        this.nameOfSponsor = nameOfSponsor;
    }

    public String getAverageMonthlyIncome() {
        return averageMonthlyIncome;
    }

    public void setAverageMonthlyIncome(String averageMonthlyIncome) {
        this.averageMonthlyIncome = averageMonthlyIncome;
    }

    public String getGuardianDisability() {
        return guardianDisability;
    }

    public void setGuardianDisability(String guardianDisability) {
        this.guardianDisability = guardianDisability;
    }

    public String getDateApplied() {
        return dateApplied;
    }

    public String getParentsAlive() {
        return parentsAlive;
    }

    public void setParentsAlive(String parentsAlive) {
        this.parentsAlive = parentsAlive;
    }

    public void setDateApplied(String dateApplied) {
        this.dateApplied = dateApplied;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGuardianfirstName() {
        return guardianfirstName;
    }

    public void setGuardianfirstName(String guardianfirstName) {
        this.guardianfirstName = guardianfirstName;
    }

    public String getGuardianlastName() {
        return guardianlastName;
    }

    public void setGuardianlastName(String guardianlastName) {
        this.guardianlastName = guardianlastName;
    }

    public String getGuardianDOB() {
        return guardianDOB;
    }

    public void setGuardianDOB(String guardianDOB) {
        this.guardianDOB = guardianDOB;
    }

    public String getGuardianGender() {
        return guardianGender;
    }

    public void setGuardianGender(String guardianGender) {
        this.guardianGender = guardianGender;
    }

    public String getTimeApplied() {
        return timeApplied;
    }

    public void setTimeApplied(String timeApplied) {
        this.timeApplied = timeApplied;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public String getGuardianOccupation() {
        return guardianOccupation;
    }

    public void setGuardianOccupation(String guardianOccupation) {
        this.guardianOccupation = guardianOccupation;
    }


    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getMoreGuardianOrParentInfo() {
        return moreGuardianOrParentInfo;
    }

    public void setMoreGuardianOrParentInfo(String moreGuardianOrParentInfo) {
        this.moreGuardianOrParentInfo = moreGuardianOrParentInfo;
    }

    public String getCurrentFamilysituation() {
        return currentFamilysituation;
    }

    public void setCurrentFamilysituation(String currentFamilysituation) {
        this.currentFamilysituation = currentFamilysituation;
    }

    public String getSourceOfIncome() {
        return sourceOfIncome;
    }

    public void setSourceOfIncome(String sourceOfIncome) {
        this.sourceOfIncome = sourceOfIncome;
    }

    public String getTypeOfHouseYouLiveIn() {
        return typeOfHouseYouLiveIn;
    }

    public void setTypeOfHouseYouLiveIn(String typeOfHouseYouLiveIn) {
        this.typeOfHouseYouLiveIn = typeOfHouseYouLiveIn;
    }

    public String getNumberOfPeopleInYourHouse() {
        return numberOfPeopleInYourHouse;
    }

    public void setNumberOfPeopleInYourHouse(String numberOfPeopleInYourHouse) {
        this.numberOfPeopleInYourHouse = numberOfPeopleInYourHouse;
    }

    public String getNumberOfPeopleInYourHouseUnder18() {
        return numberOfPeopleInYourHouseUnder18;
    }

    public void setNumberOfPeopleInYourHouseUnder18(String numberOfPeopleInYourHouseUnder18) {
        this.numberOfPeopleInYourHouseUnder18 = numberOfPeopleInYourHouseUnder18;
    }

    public String getTypeOfLightYouUse() {
        return typeOfLightYouUse;
    }

    public void setTypeOfLightYouUse(String typeOfLightYouUse) {
        this.typeOfLightYouUse = typeOfLightYouUse;
    }

    public String getTypeOfCookingEnergyYouUse() {
        return typeOfCookingEnergyYouUse;
    }

    public void setTypeOfCookingEnergyYouUse(String typeOfCookingEnergyYouUse) {
        this.typeOfCookingEnergyYouUse = typeOfCookingEnergyYouUse;
    }

    public String getTypeOfWaterYouUse() {
        return typeOfWaterYouUse;
    }

    public void setTypeOfWaterYouUse(String typeOfWaterYouUse) {
        this.typeOfWaterYouUse = typeOfWaterYouUse;
    }

    public String getHowFarIsTheWaterSourceFromYourHouse() {
        return howFarIsTheWaterSourceFromYourHouse;
    }

    public void setHowFarIsTheWaterSourceFromYourHouse(String howFarIsTheWaterSourceFromYourHouse) {
        this.howFarIsTheWaterSourceFromYourHouse = howFarIsTheWaterSourceFromYourHouse;
    }

    public String getTypeOfToiletYouUse() {
        return typeOfToiletYouUse;
    }

    public void setTypeOfToiletYouUse(String typeOfToiletYouUse) {
        this.typeOfToiletYouUse = typeOfToiletYouUse;
    }

    public String getTypeOfFloorYouHave() {
        return typeOfFloorYouHave;
    }

    public void setTypeOfFloorYouHave(String typeOfFloorYouHave) {
        this.typeOfFloorYouHave = typeOfFloorYouHave;
    }

    public String getNumberOfSiblingsYouhave() {
        return numberOfSiblingsYouhave;
    }

    public void setNumberOfSiblingsYouhave(String numberOfSiblingsYouhave) {
        this.numberOfSiblingsYouhave = numberOfSiblingsYouhave;
    }

    public String getNumberOfSiblingsYouHaveInSchool() {
        return numberOfSiblingsYouHaveInSchool;
    }

    public void setNumberOfSiblingsYouHaveInSchool(String numberOfSiblingsYouHaveInSchool) {
        this.numberOfSiblingsYouHaveInSchool = numberOfSiblingsYouHaveInSchool;
    }

    public String getFinancialChallengesYouFace() {
        return financialChallengesYouFace;
    }

    public void setFinancialChallengesYouFace(String financialChallengesYouFace) {
        this.financialChallengesYouFace = financialChallengesYouFace;
    }

    public String getCircumstancesThatAffectedYourAbilityToGoToSchool() {
        return circumstancesThatAffectedYourAbilityToGoToSchool;
    }

    public void setCircumstancesThatAffectedYourAbilityToGoToSchool(String circumstancesThatAffectedYourAbilityToGoToSchool) {
        this.circumstancesThatAffectedYourAbilityToGoToSchool = circumstancesThatAffectedYourAbilityToGoToSchool;
    }

    public String getHaveYouEverMissedSchoolDueToLackOfResources() {
        return haveYouEverMissedSchoolDueToLackOfResources;
    }

    public void setHaveYouEverMissedSchoolDueToLackOfResources(String haveYouEverMissedSchoolDueToLackOfResources) {
        this.haveYouEverMissedSchoolDueToLackOfResources = haveYouEverMissedSchoolDueToLackOfResources;
    }

    public String getHowManyMealsYouEatPerDay() {
        return howManyMealsYouEatPerDay;
    }

    public void setHowManyMealsYouEatPerDay(String howManyMealsYouEatPerDay) {
        this.howManyMealsYouEatPerDay = howManyMealsYouEatPerDay;
    }

    public String getAnyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool() {
        return anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool;
    }

    public void setAnyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool(String anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool) {
        this.anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool = anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool;
    }

    public String getYourAcademicGoals() {
        return yourAcademicGoals;
    }

    public void setYourAcademicGoals(String yourAcademicGoals) {
        this.yourAcademicGoals = yourAcademicGoals;
    }

    public String getHaveYourEverRecievedAnySponsorshipBefore() {
        return haveYourEverRecievedAnySponsorshipBefore;
    }

    public void setHaveYourEverRecievedAnySponsorshipBefore(String haveYourEverRecievedAnySponsorshipBefore) {
        this.haveYourEverRecievedAnySponsorshipBefore = haveYourEverRecievedAnySponsorshipBefore;
    }

    public String getIfYesWhatTypeOfSponsorship() {
        return ifYesWhatTypeOfSponsorship;
    }

    public void setIfYesWhatTypeOfSponsorship(String ifYesWhatTypeOfSponsorship) {
        this.ifYesWhatTypeOfSponsorship = ifYesWhatTypeOfSponsorship;
    }

    public String getIfYesHowLongDidYouRecieveTheSponsorship() {
        return ifYesHowLongDidYouRecieveTheSponsorship;
    }

    public void setIfYesHowLongDidYouRecieveTheSponsorship(String ifYesHowLongDidYouRecieveTheSponsorship) {
        this.ifYesHowLongDidYouRecieveTheSponsorship = ifYesHowLongDidYouRecieveTheSponsorship;
    }

    public String getIfYesWhyDidItStop() {
        return ifYesWhyDidItStop;
    }

    public void setIfYesWhyDidItStop(String ifYesWhyDidItStop) {
        this.ifYesWhyDidItStop = ifYesWhyDidItStop;
    }

    public String getIfYesHowDidItHelpYou() {
        return ifYesHowDidItHelpYou;
    }

    public void setIfYesHowDidItHelpYou(String ifYesHowDidItHelpYou) {
        this.ifYesHowDidItHelpYou = ifYesHowDidItHelpYou;
    }

    public String getIfYesHowDidYouFeelWhenItStopped() {
        return ifYesHowDidYouFeelWhenItStopped;
    }

    public void setIfYesHowDidYouFeelWhenItStopped(String ifYesHowDidYouFeelWhenItStopped) {
        this.ifYesHowDidYouFeelWhenItStopped = ifYesHowDidYouFeelWhenItStopped;
    }

    public String getCommunityContributionIfYouRecieveSponsorship() {
        return communityContributionIfYouRecieveSponsorship;
    }

    public void setCommunityContributionIfYouRecieveSponsorship(String communityContributionIfYouRecieveSponsorship) {
        this.communityContributionIfYouRecieveSponsorship = communityContributionIfYouRecieveSponsorship;
    }

    public List<String> getEvidenceOfNeed() {
        return evidenceOfNeed;
    }

    public void setEvidenceOfNeed(List<String> evidenceOfNeed) {
        this.evidenceOfNeed = evidenceOfNeed;
    }

    public String getLocationOfHome() {
        return locationOfHome;
    }

    public void setLocationOfHome(String locationOfHome) {
        this.locationOfHome = locationOfHome;
    }

    public String getLetterFromLocalLeaderOrChief() {
        return letterFromLocalLeaderOrChief;
    }

    public void setLetterFromLocalLeaderOrChief(String letterFromLocalLeaderOrChief) {
        this.letterFromLocalLeaderOrChief = letterFromLocalLeaderOrChief;
    }

    public String getAnyOtherComments() {
        return anyOtherComments;
    }

    public void setAnyOtherComments(String anyOtherComments) {
        this.anyOtherComments = anyOtherComments;
    }

    public String getHowDidYouKnowAboutPamodziKidsFoundation() {
        return howDidYouKnowAboutPamodziKidsFoundation;
    }

    public void setHowDidYouKnowAboutPamodziKidsFoundation(String howDidYouKnowAboutPamodziKidsFoundation) {
        this.howDidYouKnowAboutPamodziKidsFoundation = howDidYouKnowAboutPamodziKidsFoundation;
    }

    public List<String> getMyImages() {
        return myImages;
    }

    public void setMyImages(List<String> myImages) {
        this.myImages = myImages;
    }

    @Override
    public String toString() {
        return "" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", photo='" + photo + '\'' +
                ", countryCodeName='" + countryCodeName + '\'' +
                ", country='" + country + '\'' +
                ", phoneSuffix='" + phoneSuffix + '\'' +
                ", phonePrefix='" + phonePrefix + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", occupation='" + occupation + '\'' +
                ", disability='" + disability + '\'' +
                ", bio='" + bio + '\'' +
                ", reason='" + reason + '\'' +
                ", education='" + education + '\'' +
                ", status='" + status + '\'' +
                ", parentsAlive='" + parentsAlive + '\'' +
                ", guardianfirstName='" + guardianfirstName + '\'' +
                ", guardianlastName='" + guardianlastName + '\'' +
                ", guardianEmail='" + guardianEmail + '\'' +
                ", guardianPhone='" + guardianPhone + '\'' +
                ", guardianOccupation='" + guardianOccupation + '\'' +
                ", guardianDOB='" + guardianDOB + '\'' +
                ", guardianGender='" + guardianGender + '\'' +
                ", guardianDisability='" + guardianDisability + '\'' +
                ", relationship='" + relationship + '\'' +
                ", dateApplied='" + dateApplied + '\'' +
                ", timeApplied='" + timeApplied + '\'' +
                ", moreGuardianOrParentInfo='" + moreGuardianOrParentInfo + '\'' +
                ", currentFamilysituation='" + currentFamilysituation + '\'' +
                ", sourceOfIncome='" + sourceOfIncome + '\'' +
                ", averageMonthlyIncome='" + averageMonthlyIncome + '\'' +
                ", typeOfHouseYouLiveIn='" + typeOfHouseYouLiveIn + '\'' +
                ", numberOfPeopleInYourHouse='" + numberOfPeopleInYourHouse + '\'' +
                ", numberOfPeopleInYourHouseUnder18='" + numberOfPeopleInYourHouseUnder18 + '\'' +
                ", typeOfLightYouUse='" + typeOfLightYouUse + '\'' +
                ", typeOfCookingEnergyYouUse='" + typeOfCookingEnergyYouUse + '\'' +
                ", typeOfWaterYouUse='" + typeOfWaterYouUse + '\'' +
                ", howFarIsTheWaterSourceFromYourHouse='" + howFarIsTheWaterSourceFromYourHouse + '\'' +
                ", typeOfToiletYouUse='" + typeOfToiletYouUse + '\'' +
                ", typeOfFloorYouHave='" + typeOfFloorYouHave + '\'' +
                ", numberOfSiblingsYouhave='" + numberOfSiblingsYouhave + '\'' +
                ", numberOfSiblingsYouHaveInSchool='" + numberOfSiblingsYouHaveInSchool + '\'' +
                ", financialChallengesYouFace='" + financialChallengesYouFace + '\'' +
                ", circumstancesThatAffectedYourAbilityToGoToSchool='" + circumstancesThatAffectedYourAbilityToGoToSchool + '\'' +
                ", haveYouEverMissedSchoolDueToLackOfResources='" + haveYouEverMissedSchoolDueToLackOfResources + '\'' +
                ", howManyMealsYouEatPerDay='" + howManyMealsYouEatPerDay + '\'' +
                ", anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool='" + anyResponsibilitiesAtHomeThatAffectYourAbilityToGoToSchool + '\'' +
                ", yourAcademicGoals='" + yourAcademicGoals + '\'' +
                ", haveYourEverRecievedAnySponsorshipBefore='" + haveYourEverRecievedAnySponsorshipBefore + '\'' +
                ", nameOfSponsor='" + nameOfSponsor + '\'' +
                ", ifYesWhatTypeOfSponsorship='" + ifYesWhatTypeOfSponsorship + '\'' +
                ", ifYesHowLongDidYouRecieveTheSponsorship='" + ifYesHowLongDidYouRecieveTheSponsorship + '\'' +
                ", ifYesWhyDidItStop='" + ifYesWhyDidItStop + '\'' +
                ", ifYesHowDidItHelpYou='" + ifYesHowDidItHelpYou + '\'' +
                ", ifYesHowDidYouFeelWhenItStopped='" + ifYesHowDidYouFeelWhenItStopped + '\'' +
                ", communityContributionIfYouRecieveSponsorship='" + communityContributionIfYouRecieveSponsorship + '\'' +
                ", evidenceOfNeed=" + evidenceOfNeed +
                ", locationOfHome='" + locationOfHome + '\'' +
                ", letterFromLocalLeaderOrChief='" + letterFromLocalLeaderOrChief + '\'' +
                ", anyOtherComments='" + anyOtherComments + '\'' +
                ", howDidYouKnowAboutPamodziKidsFoundation='" + howDidYouKnowAboutPamodziKidsFoundation + '\''
                ;
    }
}


