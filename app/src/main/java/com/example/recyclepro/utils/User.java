package com.example.recyclepro.utils;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class User extends FirebaseKey implements Serializable {

    private String firstname;
    private String lastname;
    private String email;
    private String imagePath;
    private String imageUrl;
    private String phone;

    public User(){}

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Exclude
    public String getFullName(){
        return this.firstname + " " + this.lastname;
    }

    public User setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Exclude
    public String getImageUrl() {
        return imageUrl;
    }

    @Exclude
    public boolean isValid(){
        if(this.firstname.isEmpty()) return false;
        if(this.lastname.isEmpty()) return false;
        if(this.email.isEmpty()) return false;
        return true;
    }
}
