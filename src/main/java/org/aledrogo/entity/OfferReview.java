package org.aledrogo.entity;

public class OfferReview {
    private final Offer offer;
    private final Buyer reviewer;
    private float rating;
    private String description;

    public OfferReview(Offer offer, Buyer reviewer, float rating) {
        this.offer = offer;
        this.reviewer = reviewer;
        this.rating = rating;
        this.description = null;
    }

    public Offer getOffer() {
        return offer;
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
