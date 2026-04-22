package org.aledrogo.entity;

public class Order {
    public int quantity;
    public final Offer offer;
    public final Buyer buyer;
    public final PaymentMethod paymentMethod;
    public final OrderShippingDetails shippingDetails;
    public OrderStatus status = OrderStatus.PENDING;

    public Order(int quantity, Offer offer, Buyer buyer, PaymentMethod paymentMethod, OrderShippingDetails shippingDetails) {
        this.offer = offer;
        this.quantity = quantity;
        this.buyer = buyer;
        this.paymentMethod = paymentMethod;
        this.shippingDetails = shippingDetails;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Offer getOffer() {
        return offer;
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
