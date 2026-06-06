package org.aledrogo.entity;

public class OrderReview extends Entity {
    private final Order order;
    private final Buyer reviewer;
    private float rating;
    private String description;

    public OrderReview(Order order, Buyer reviewer, float rating) {
        this.order = order;
        this.reviewer = reviewer;
        this.rating = rating;
        this.description = null;
    }

    public Order getOffer() {
        return order;
    }

    public Buyer getReviewer() {
        return reviewer;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
