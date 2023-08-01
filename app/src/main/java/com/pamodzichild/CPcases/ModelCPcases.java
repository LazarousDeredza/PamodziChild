package com.pamodzichild.CPcases;


import java.io.Serializable;

public class ModelCPcases  implements Serializable {
    String id;
    String dateOfReporting,
            timeOfReporting,
            nameOfChild,
            childLocation,
            childAge,
            genderOfChild,
            anyFormOfDisability,
            parentOrGuardianName,
            typeOfAbuse,
            detailsOfPerpetrator,
            currentStateOfChild;
    String casesStatus,howitwashandled;
    private Reporter reporter;


    public ModelCPcases() {
    }

    public ModelCPcases(String id, String dateOfReporting, String timeOfReporting, String nameOfChild, String childLocation, String childAge, String genderOfChild, String anyFormOfDisability, String parentOrGuardianName, String typeOfAbuse, String detailsOfPerpetrator, String currentStateOfChild, Reporter reporter) {
        this.id = id;
        this.dateOfReporting = dateOfReporting;
        this.timeOfReporting = timeOfReporting;
        this.nameOfChild = nameOfChild;
        this.childLocation = childLocation;
        this.childAge = childAge;
        this.genderOfChild = genderOfChild;
        this.anyFormOfDisability = anyFormOfDisability;
        this.parentOrGuardianName = parentOrGuardianName;
        this.typeOfAbuse = typeOfAbuse;
        this.detailsOfPerpetrator = detailsOfPerpetrator;
        this.currentStateOfChild = currentStateOfChild;
        this.reporter = reporter;
    }

    public ModelCPcases(String dateOfReporting, String timeOfReporting, String nameOfChild, String childLocation, String childAge, String genderOfChild, String anyFormOfDisability, String parentOrGuardianName, String typeOfAbuse, String detailsOfPerpetrator, String currentStateOfChild) {
        this.dateOfReporting = dateOfReporting;
        this.timeOfReporting = timeOfReporting;
        this.nameOfChild = nameOfChild;
        this.childLocation = childLocation;
        this.childAge = childAge;
        this.genderOfChild = genderOfChild;
        this.anyFormOfDisability = anyFormOfDisability;
        this.parentOrGuardianName = parentOrGuardianName;
        this.typeOfAbuse = typeOfAbuse;
        this.detailsOfPerpetrator = detailsOfPerpetrator;
        this.currentStateOfChild = currentStateOfChild;
    }

    public ModelCPcases(String dateOfReporting, String timeOfReporting, String nameOfChild, String childLocation, String childAge, String genderOfChild, String anyFormOfDisability, String parentOrGuardianName, String typeOfAbuse, String detailsOfPerpetrator, String currentStateOfChild, Reporter reporter) {
        this.dateOfReporting = dateOfReporting;
        this.timeOfReporting = timeOfReporting;
        this.nameOfChild = nameOfChild;
        this.childLocation = childLocation;
        this.childAge = childAge;
        this.genderOfChild = genderOfChild;
        this.anyFormOfDisability = anyFormOfDisability;
        this.parentOrGuardianName = parentOrGuardianName;
        this.typeOfAbuse = typeOfAbuse;
        this.detailsOfPerpetrator = detailsOfPerpetrator;
        this.currentStateOfChild = currentStateOfChild;
        this.reporter = reporter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateOfReporting() {
        return dateOfReporting;
    }

    public void setDateOfReporting(String dateOfReporting) {
        this.dateOfReporting = dateOfReporting;
    }

    public String getTimeOfReporting() {
        return timeOfReporting;
    }

    public void setTimeOfReporting(String timeOfReporting) {
        this.timeOfReporting = timeOfReporting;
    }

    public String getNameOfChild() {
        return nameOfChild;
    }

    public void setNameOfChild(String nameOfChild) {
        this.nameOfChild = nameOfChild;
    }

    public String getChildLocation() {
        return childLocation;
    }

    public void setChildLocation(String childLocation) {
        this.childLocation = childLocation;
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }

    public String getGenderOfChild() {
        return genderOfChild;
    }

    public void setGenderOfChild(String genderOfChild) {
        this.genderOfChild = genderOfChild;
    }

    public String getAnyFormOfDisability() {
        return anyFormOfDisability;
    }

    public void setAnyFormOfDisability(String anyFormOfDisability) {
        this.anyFormOfDisability = anyFormOfDisability;
    }

    public String getParentOrGuardianName() {
        return parentOrGuardianName;
    }

    public void setParentOrGuardianName(String parentOrGuardianName) {
        this.parentOrGuardianName = parentOrGuardianName;
    }

    public String getTypeOfAbuse() {
        return typeOfAbuse;
    }

    public void setTypeOfAbuse(String typeOfAbuse) {
        this.typeOfAbuse = typeOfAbuse;
    }

    public String getDetailsOfPerpetrator() {
        return detailsOfPerpetrator;
    }

    public void setDetailsOfPerpetrator(String detailsOfPerpetrator) {
        this.detailsOfPerpetrator = detailsOfPerpetrator;
    }

    public String getCurrentStateOfChild() {
        return currentStateOfChild;
    }

    public void setCurrentStateOfChild(String currentStateOfChild) {
        this.currentStateOfChild = currentStateOfChild;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public String getCasesStatus() {
        return casesStatus;
    }

    public void setCasesStatus(String casesStatus) {
        this.casesStatus = casesStatus;
    }

    public String getHowitwashandled() {
        return howitwashandled;
    }

    public void setHowitwashandled(String howitwashandled) {
        this.howitwashandled = howitwashandled;
    }

    @Override
    public String toString() {
        return "ModelCPcases{" +
                "id='" + id + '\'' +
                ", dateOfReporting='" + dateOfReporting + '\'' +
                ", timeOfReporting='" + timeOfReporting + '\'' +
                ", nameOfChild='" + nameOfChild + '\'' +
                ", childLocation='" + childLocation + '\'' +
                ", childAge='" + childAge + '\'' +
                ", genderOfChild='" + genderOfChild + '\'' +
                ", anyFormOfDisability='" + anyFormOfDisability + '\'' +
                ", parentOrGuardianName='" + parentOrGuardianName + '\'' +
                ", typeOfAbuse='" + typeOfAbuse + '\'' +
                ", detailsOfPerpetrator='" + detailsOfPerpetrator + '\'' +
                ", currentStateOfChild='" + currentStateOfChild + '\'' +
                ", casesStatus='" + casesStatus + '\'' +
                ", howitwashandled='" + howitwashandled + '\'' +
                ", reporter=" + reporter +
                '}';
    }
}
