package com.master.oslyOnlineShoping.dto;

public class ProductDTO {
    private String name;
    private Double price;
    private String description;
    private String image;
    private NameWrapper category; // instead of String
    private NameWrapper store;

    private int quantity;

    private double sellPrice;

    private String barcode;

    private double boughtPrice;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public NameWrapper getCategory() { return category; }
    public void setCategory(NameWrapper category) { this.category = category; }

    public NameWrapper getStore() { return store; }
    public void setStore(NameWrapper store) { this.store = store; }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(double boughtPrice) {
        this.boughtPrice = boughtPrice;
    }
}
