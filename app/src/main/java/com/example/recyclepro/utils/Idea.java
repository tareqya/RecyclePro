package com.example.recyclepro.utils;

import com.google.firebase.firestore.Exclude;

public class Idea extends FirebaseKey{
    private String title;
    private String description;
    private String uid;
    private String imagePath;
    private String imageUrl;
    private String category;
    private String product;

    public Idea() {}

    public String getTitle() {
        return title;
    }

    public Idea setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Idea setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Idea setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Idea setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
    @Exclude
    public String getImageUrl() {
        return imageUrl;
    }

    public Idea setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Idea setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public Idea setProduct(String product) {
        this.product = product;
        return this;
    }
}
