package org.aledrogo.service;

import org.aledrogo.entity.*;
import org.aledrogo.repository.OrderRepository;
import org.aledrogo.repository.OrderReviewRepository;

import java.util.ArrayList;


public class OrderService {
    public final OrderRepository orderRepository;
    public final OrderReviewRepository orderReviewRepository;

    public OrderService(OrderRepository orderRepository, OrderReviewRepository orderReviewRepository) {
        this.orderRepository = orderRepository;
        this.orderReviewRepository = orderReviewRepository;
    }

    public Order createOrder(ArrayList<Offer> offers, Buyer buyer, OrderShippingDetails orderShippingDetails, PaymentMethod paymentMethod) {
        return null;
    }

    public void deleteOrderReview(int orderReviewId) {

    }

    public OrderReview updateOrderReview(int orderReviewId, float rating, String description) {
        return null;
    }

    public OrderReview createOrderReview(Order order, Buyer reviewer, float rating, String description) throws Exception {
        if (!order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new Exception("Zamówienie musi być zrealizowane");
        }

        OrderReview orderReview = new OrderReview(order, reviewer, rating);
        this.orderReviewRepository.create(orderReview);
        return orderReview;
    }
}
