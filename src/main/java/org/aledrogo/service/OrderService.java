package org.aledrogo.service;

import org.aledrogo.entity.*;
import org.aledrogo.repository.OfferRepository;
import org.aledrogo.repository.OrderRepository;
import org.aledrogo.repository.OrderReviewRepository;

import java.util.ArrayList;


public class OrderService {
    public final OrderRepository orderRepository;
    public final OrderReviewRepository orderReviewRepository;
    public final OfferRepository offerRepository;

    public OrderService(OrderRepository orderRepository, OrderReviewRepository orderReviewRepository, OfferRepository offerRepository) {
        this.orderRepository = orderRepository;
        this.orderReviewRepository = orderReviewRepository;
        this.offerRepository = offerRepository;
    }

    public Order createOrder(ArrayList<Offer> offers, Buyer buyer, OrderShippingDetails orderShippingDetails, PaymentMethod paymentMethod) throws Exception {
        for (Offer offer : offers) {
            Offer existingOffer = this.offerRepository.getById(offer.getId());
            if (existingOffer != null) {
                if (existingOffer.getQuantity() == 0) {
                    throw new Exception("Oferta jest niedostępna");
                }
            }
        }

        Order newOrder = new Order(
                offers,
                buyer,
                paymentMethod,
                orderShippingDetails
        );
        this.orderRepository.create(newOrder);

        return newOrder;
    }

    public ArrayList<Order> getOrdersForBuyer(Buyer buyer) {
        ArrayList<Order> result = new ArrayList<>();
        for (Order order : this.orderRepository.getAll()) {
            if (order.getBuyer() == buyer) {
                result.add(order);
            }
        }
        return result;
    }

    public Order completeOrder(int orderId) throws Exception {
        Order order = this.orderRepository.getById(orderId);
        if (order == null) {
            throw new Exception("Nie znaleziono zamówienia");
        }

        order.setStatus(OrderStatus.COMPLETED);
        this.orderRepository.update(order);
        return order;
    }

    public void deleteOrderReview(int orderReviewId) throws Exception {
        OrderReview orderReview = this.orderReviewRepository.getById(orderReviewId);
        if (orderReview == null) {
            throw new Exception("Nie znaleziono opinii");
        }

        this.orderReviewRepository.delete(orderReview);
    }

    public OrderReview updateOrderReview(int orderReviewId, float rating, String description) {
        return null;
    }

    public OrderReview createOrderReview(Order order, Buyer reviewer, float rating, String description) throws Exception {
        if (!order.getStatus().equals(OrderStatus.COMPLETED)) {
            throw new Exception("Zamówienie musi być zrealizowane");
        }

        OrderReview orderReview = new OrderReview(order, reviewer, rating);
        orderReview.setDescription(description);
        this.orderReviewRepository.create(orderReview);
        return orderReview;
    }
}
