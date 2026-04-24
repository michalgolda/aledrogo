package org.aledrogo.entity;


import java.math.BigDecimal;

public class Offer {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private final Seller seller;

    public Offer(String name, String description, BigDecimal price, int quantity, Seller seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Seller getSeller() {
        return seller;
    }
}
