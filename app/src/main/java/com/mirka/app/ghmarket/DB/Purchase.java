package com.mirka.app.ghmarket.DB;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Purchase {
    String product_id;
    String size;
    String color;

    public Purchase() {
    }

    public Purchase(Product product, String size, String color) {
        this.product_id = product.getObjectId();
        this.size = size;
        this.color = color;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }


    public static List<Purchase> fromJsonArray(String s) {
        String z = s.trim();
            return new Gson().fromJson(z,new  TypeToken<List<Purchase>>(){}.getType());
    }

    public static Purchase fromJson(String string) {
        return new Gson().fromJson(string, Purchase.class);
    }
}