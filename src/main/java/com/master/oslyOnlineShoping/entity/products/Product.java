package com.master.oslyOnlineShoping.entity.products;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String barcode;
    private String imageUrl;
    private String description;

    private Double boughtPrice;
    private Double sellPrice;
    private Integer quantity;
    public Product(String name, Double price, String description, String storeName, String categoryName, String imageUrl) {
        this.name = name;
        this.sellPrice = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.store = new Store(storeName);  // You may want to manually instantiate or use a service to fetch Store by name
        this.category = new Category(categoryName);  // Likewise, fetch category by name
    }
    public Product(){

    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Column(columnDefinition = "LONGTEXT")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(Double boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
