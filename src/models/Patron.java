package models;

import java.util.UUID;

public class Patron {
    final private String patronId;
    private String patronName;
    private String patronContact;
    private String patronAddress;

    public Patron(String patronName, String patronContact, String patronAddress) {
        this.patronId = UUID.randomUUID().toString();
        this.patronName = patronName;
        this.patronContact = patronContact;
        this.patronAddress = patronAddress;
    }

    public Patron(Patron patron) {
        this.patronId = patron.getPatronId();
        this.patronName = patron.getPatronName();
        this.patronContact = patron.getPatronContact();
        this.patronAddress = patron.getPatronAddress();
    }

    public String getPatronId() {
        return patronId;
    }

    public String getPatronName() {
        return patronName;
    }

    public String getPatronContact() {
        return patronContact;
    }

    public String getPatronAddress() {
        return patronAddress;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public void setPatronContact(String patronContact) {
        this.patronContact = patronContact;
    }

    public void setPatronAddress(String patronAddress) {
        this.patronAddress = patronAddress;
    }
}
