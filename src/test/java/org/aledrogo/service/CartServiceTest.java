package org.aledrogo.service;

import org.aledrogo.entity.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService();
    }

    @Test
    void addItemIncreasesCartSize() {
        cartService.addItem(mock(Offer.class));

        assertEquals(1, cartService.getItems().size());
    }

    @Test
    void addItemAppendsOfferToItems() {
        Offer offer = mock(Offer.class);
        cartService.addItem(offer);

        assertSame(offer, cartService.getItems().get(0));
    }

    @Test
    void removeItemDeletesOfferAtGivenIndex() {
        Offer first = mock(Offer.class);
        Offer second = mock(Offer.class);
        cartService.addItem(first);
        cartService.addItem(second);

        cartService.removeItem(0);

        assertEquals(1, cartService.getItems().size());
        assertSame(second, cartService.getItems().get(0));
    }

    @Test
    void getItemsReturnsAllAddedOffers() {
        Offer first = mock(Offer.class);
        Offer second = mock(Offer.class);
        cartService.addItem(first);
        cartService.addItem(second);

        ArrayList<Offer> items = cartService.getItems();

        assertEquals(2, items.size());
        assertSame(first, items.get(0));
        assertSame(second, items.get(1));
    }

    @Test
    void clearRemovesAllItems() {
        cartService.addItem(mock(Offer.class));
        cartService.addItem(mock(Offer.class));

        cartService.clear();

        assertTrue(cartService.getItems().isEmpty());
    }

    @Test
    void clearOnEmptyCartDoesNotThrow() {
        assertDoesNotThrow(() -> cartService.clear());
    }
}