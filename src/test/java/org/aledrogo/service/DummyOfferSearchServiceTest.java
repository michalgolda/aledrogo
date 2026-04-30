package org.aledrogo.service;

import org.aledrogo.entity.Offer;
import org.aledrogo.entity.Seller;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DummyOfferSearchServiceTest {
    private final OfferSearchService offerSearchService = new DummyOfferOfferSearchService();

    private Seller newSeller() {
        return new Seller(
                "test@example.com",
                "qwerty",
                "test-firstName",
                "test-lastName",
                "1234567890"
        );
    }

    @Test
    void shouldMatchByName() {
        Seller seller = newSeller();

        Offer matching = new Offer("apple-phone", "some-description", 1.0, 1, seller);
        Offer nonMatching = new Offer("banana-phone", "other-description", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(matching);
        offers.add(nonMatching);

        ArrayList<Offer> results = this.offerSearchService.match("apple-phone", offers);

        assertEquals(1, results.size());
        assertSame(matching, results.get(0));
    }

    @Test
    void shouldMatchByDescription() {
        Seller seller = newSeller();

        Offer matching = new Offer("some-name", "fresh-apple-juice", 1.0, 1, seller);
        Offer nonMatching = new Offer("other-name", "fresh-banana-juice", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(matching);
        offers.add(nonMatching);

        ArrayList<Offer> results = this.offerSearchService.match("fresh-apple-juice", offers);

        assertEquals(1, results.size());
        assertSame(matching, results.get(0));
    }

    @Test
    void shouldMatchByNameAndDescription() {
        Seller seller = newSeller();

        Offer nameAndDescriptionMatch = new Offer("apple", "apple-description", 1.0, 1, seller);
        Offer onlyNameMatch = new Offer("apple-phone", "totally-different", 1.0, 1, seller);
        Offer noMatch = new Offer("banana", "banana-description", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(onlyNameMatch);
        offers.add(nameAndDescriptionMatch);
        offers.add(noMatch);

        ArrayList<Offer> results = this.offerSearchService.match("apple apple-description", offers);

        assertEquals(2, results.size());
        assertSame(nameAndDescriptionMatch, results.get(0));
        assertSame(onlyNameMatch, results.get(1));
    }

    @Test
    void shouldReturnResultsSortedByScoreDescending() {
        Seller seller = newSeller();

        Offer highScore = new Offer("apple banana", "cherry", 1.0, 1, seller);
        Offer mediumScore = new Offer("apple", "banana", 1.0, 1, seller);
        Offer lowScore = new Offer("apple", "totally-different", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();

        offers.add(lowScore);
        offers.add(highScore);
        offers.add(mediumScore);

        ArrayList<Offer> results = this.offerSearchService.match("apple banana cherry", offers);

        assertEquals(3, results.size());
        assertSame(highScore, results.get(0));
        assertSame(mediumScore, results.get(1));
        assertSame(lowScore, results.get(2));
    }
}
