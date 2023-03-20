package com.example.cemenuta;

public class ClientsSearchModel {
    Integer clientID;
    String clientName;
    String clientEmail;
    String clientPhoneNumber;

    public ClientsSearchModel(Integer clientID, String clientName, String clientEmail, String clientPhoneNumber) {
        this.clientID = clientID;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }
}
