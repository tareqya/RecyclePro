package com.example.recyclepro.utils;

import java.util.ArrayList;

public class Category extends FirebaseKey{
    private String name;
    private ArrayList<String> products;

    public Category(){}

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<String>  getProducts() {
        return products;
    }

    public Category setProducts(ArrayList<String>  products) {
        this.products = products;
        return this;
    }

    public String toString(){
        return this.name;
    }
}
