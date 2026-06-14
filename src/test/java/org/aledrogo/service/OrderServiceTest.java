package org.aledrogo.service;

import org.aledrogo.entity.*;
import org.aledrogo.repository.OfferRepository;
import org.aledrogo.repository.OrderRepository;
import org.aledrogo.repository.OrderReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderReviewRepository orderReviewRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrderReturnsNewOrderWhenAllOffersAreAvailable() throws Exception {
        Offer offer = mock(Offer.class);
        when(offer.getId()).thenReturn(1);
        when(offerRepository.getById(1)).thenReturn(offer);
        when(offer.getQuantity()).thenReturn(5);

        Buyer buyer = mock(Buyer.class);
        OrderShippingDetails shippingDetails = mock(OrderShippingDetails.class);
        PaymentMethod paymentMethod = mock(PaymentMethod.class);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(offer);

        Order result = orderService.createOrder(offers, buyer, shippingDetails, paymentMethod);

        assertNotNull(result);
        verify(orderRepository).create(result);
    }

    @Test
    void createOrderThrowsWhenOfferQuantityIsZero() {
        Offer offer = mock(Offer.class);
        when(offer.getId()).thenReturn(1);
        when(offerRepository.getById(1)).thenReturn(offer);
        when(offer.getQuantity()).thenReturn(0);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(offer);

        Exception ex = assertThrows(Exception.class, () ->
                orderService.createOrder(offers, mock(Buyer.class), mock(OrderShippingDetails.class), mock(PaymentMethod.class)));

        assertEquals("Oferta jest niedostępna", ex.getMessage());
        verify(orderRepository, never()).create(any());
    }

    @Test
    void createOrderSkipsQuantityCheckWhenOfferNotFoundInRepository() throws Exception {
        Offer offer = mock(Offer.class);
        when(offer.getId()).thenReturn(99);
        when(offerRepository.getById(99)).thenReturn(null);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(offer);

        Order result = orderService.createOrder(offers, mock(Buyer.class), mock(OrderShippingDetails.class), mock(PaymentMethod.class));

        assertNotNull(result);
        verify(orderRepository).create(result);
    }

    @Test
    void deleteOrderReviewDeletesExistingReview() throws Exception {
        OrderReview review = mock(OrderReview.class);
        when(orderReviewRepository.getById(1)).thenReturn(review);

        orderService.deleteOrderReview(1);

        verify(orderReviewRepository).delete(review);
    }

    @Test
    void deleteOrderReviewThrowsWhenReviewDoesNotExist() {
        when(orderReviewRepository.getById(99)).thenReturn(null);

        Exception ex = assertThrows(Exception.class, () -> orderService.deleteOrderReview(99));

        assertEquals("Nie znaleziono opinii", ex.getMessage());
        verify(orderReviewRepository, never()).delete(any());
    }

    @Test
    void createOrderReviewReturnsReviewForCompletedOrder() throws Exception {
        Order order = mock(Order.class);
        when(order.getStatus()).thenReturn(OrderStatus.COMPLETED);
        Buyer buyer = mock(Buyer.class);

        OrderReview result = orderService.createOrderReview(order, buyer, 5.0f, "Great!");

        assertNotNull(result);
        verify(orderReviewRepository).create(result);
    }

    @Test
    void createOrderReviewThrowsWhenOrderIsNotCompleted() {
        Order order = mock(Order.class);
        when(order.getStatus()).thenReturn(OrderStatus.PENDING);

        Exception ex = assertThrows(Exception.class, () ->
                orderService.createOrderReview(order, mock(Buyer.class), 4.0f, "ok"));

        assertEquals("Zamówienie musi być zrealizowane", ex.getMessage());
        verify(orderReviewRepository, never()).create(any());
    }

    @Test
    void createOrderReviewPersistsTheDescription() throws Exception {
        Order order = mock(Order.class);
        when(order.getStatus()).thenReturn(OrderStatus.COMPLETED);

        OrderReview result = orderService.createOrderReview(order, mock(Buyer.class), 5.0f, "Świetny produkt");

        assertEquals("Świetny produkt", result.getDescription());
    }

    @Test
    void completeOrderSetsStatusAndPersists() throws Exception {
        Order order = mock(Order.class);
        when(orderRepository.getById(1)).thenReturn(order);

        Order result = orderService.completeOrder(1);

        assertSame(order, result);
        verify(order).setStatus(OrderStatus.COMPLETED);
        verify(orderRepository).update(order);
    }

    @Test
    void completeOrderThrowsWhenOrderDoesNotExist() {
        when(orderRepository.getById(99)).thenReturn(null);

        Exception ex = assertThrows(Exception.class, () -> orderService.completeOrder(99));

        assertEquals("Nie znaleziono zamówienia", ex.getMessage());
        verify(orderRepository, never()).update(any());
    }

    @Test
    void getOrdersForBuyerReturnsOnlyThatBuyersOrders() {
        Buyer buyer = mock(Buyer.class);
        Buyer otherBuyer = mock(Buyer.class);

        Order mine = mock(Order.class);
        when(mine.getBuyer()).thenReturn(buyer);
        Order theirs = mock(Order.class);
        when(theirs.getBuyer()).thenReturn(otherBuyer);

        ArrayList<Order> all = new ArrayList<>();
        all.add(mine);
        all.add(theirs);
        when(orderRepository.getAll()).thenReturn(all);

        ArrayList<Order> result = orderService.getOrdersForBuyer(buyer);

        assertEquals(1, result.size());
        assertSame(mine, result.get(0));
    }
}