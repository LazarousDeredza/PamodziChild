package com.pamodzichild.Bank;

public class ModelBank {
    String id;
    String bankName, accountNumber, accountOwner;

String status;
    public ModelBank() {
    }


    public ModelBank(String id, String bankName, String accountNumber, String accountOwner) {
        this.id = id;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
    }

    public ModelBank(String id, String bankName, String accountNumber, String accountOwner, String status) {
        this.id = id;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ModelBank{" +
                "id='" + id + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountOwner='" + accountOwner + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
