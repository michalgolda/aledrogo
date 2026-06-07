package org.aledrogo.entity;

import java.util.ArrayList;

public class Order extends Entity {
    private final ArrayList<Offer> offers;
    private final Buyer buyer;
    private final PaymentMethod paymentMethod;
    private final OrderShippingDetails shippingDetails;
    private OrderStatus status = OrderStatus.PENDING;

    public Order(ArrayList<Offer> offers, Buyer buyer, PaymentMethod paymentMethod, OrderShippingDetails shippingDetails) {
        this.offers = offers;
        this.buyer = buyer;
        this.paymentMethod = paymentMethod;
        this.shippingDetails = shippingDetails;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OrderShippingDetails getShippingDetails() {
        return shippingDetails;
    }
}
