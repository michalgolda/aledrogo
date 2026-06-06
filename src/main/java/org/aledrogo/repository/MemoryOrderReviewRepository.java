package org.aledrogo.repository;

import org.aledrogo.entity.OrderReview;

import java.util.ArrayList;

public class MemoryOrderReviewRepository extends OrderReviewRepository {
    ArrayList<OrderReview> reviews = new ArrayList<>();

    @Override
    public OrderReview getById(int id) {
        for (OrderReview review : reviews) {
            if (review.getId() == id) {
                return review;
            }
        }
        return null;
    }

    @Override
    public ArrayList<OrderReview> getAll() {
        return reviews;
    }

    @Override
    public OrderReview create(OrderReview entity) {
        reviews.add(entity);
        return entity;
    }

    @Override
    public OrderReview update(OrderReview entity) {
        ArrayList<OrderReview> reviewsForReplace = new ArrayList<>();
        for (OrderReview review : reviews) {
            if (review.getId() != entity.getId()) {
                reviewsForReplace.add(entity);
            }
        }
        reviewsForReplace.add(entity);
        return entity;
    }

    @Override
    public void delete(OrderReview entity) {
        ArrayList<OrderReview> reviewsForReplace = new ArrayList<>();
        for (OrderReview review : reviews) {
            if (review.getId() != entity.getId()) {
                reviewsForReplace.add(entity);
            }
        }
        reviews = reviewsForReplace;
    }
}
