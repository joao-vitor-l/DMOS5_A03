package com.example.dmos5_a03.model;

import java.util.ArrayList;

public class User{
    private String username;
    private String password;
    private ArrayList<Contact> contacts;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.contacts = new ArrayList<Contact>();
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public String getUsername() { return username; }
    public String getPassword(){
        return password;
    }
    public ArrayList<Contact> getContacts(){
        return contacts;
    }
}
