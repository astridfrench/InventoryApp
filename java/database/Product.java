/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory.database;

public class Product {
    private int id;
    private String productName;
    private String description;
    private int quantity;
    private double sellPrice;
    private double buyPrice;

    public Product() {
        // Initialize default values if needed
        this(-1, "", "", 0, 0.0, 0.0);
    }


    public Product(int id, String productName, String description, int quantity, double sellPrice, double buyPrice) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }


    public Product(String productName, String description, int quantity, double sellPrice, double buyPrice) {
        this(-1, productName, description, quantity, sellPrice, buyPrice);
    }

    // Getters and Setters for product
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }
}
