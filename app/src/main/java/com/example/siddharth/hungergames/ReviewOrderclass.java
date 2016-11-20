package com.example.siddharth.hungergames;

/**
 * Created by siddharth on 11/21/16.
 */
public class ReviewOrderclass {
    String name;
    int quantity;
    int price;
    int total_price;
    ReviewOrderclass(String name,int quantity, int price, int total_price) {
        this.name = name;
        this.quantity=quantity;
        this.price=price;
        this.total_price=total_price;
    }
}

