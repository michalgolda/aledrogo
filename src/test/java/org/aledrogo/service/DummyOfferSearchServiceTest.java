package org.aledrogo.service;

import org.aledrogo.entity.Offer;
import org.aledrogo.entity.Seller;
import org.aledrogo.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DummyOfferSearchServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private DummyOfferOfferSearchService offerSearchService;

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
    void matchByName() {
        Seller seller = newSeller();

        Offer matching = new Offer("apple-phone", "some-description", 1.0, 1, seller);
        Offer nonMatching = new Offer("banana-phone", "other-description", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(matching);
        offers.add(nonMatching);

        when(offerRepository.getAll()).thenReturn(offers);

        ArrayList<Offer> results = offerSearchService.match("apple-phone");

        assertEquals(1, results.size());
        assertSame(matching, results.get(0));
    }

    @Test
    void matchByDescription() {
        Seller seller = newSeller();

        Offer matching = new Offer("some-name", "fresh-apple-juice", 1.0, 1, seller);
        Offer nonMatching = new Offer("other-name", "fresh-banana-juice", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(matching);
        offers.add(nonMatching);

        when(offerRepository.getAll()).thenReturn(offers);

        ArrayList<Offer> results = offerSearchService.match("fresh-apple-juice");

        assertEquals(1, results.size());
        assertSame(matching, results.get(0));
    }

    @Test
    void matchByNameAndDescription() {
        Seller seller = newSeller();

        Offer nameAndDescriptionMatch = new Offer("apple", "apple-description", 1.0, 1, seller);
        Offer onlyNameMatch = new Offer("apple-phone", "totally-different", 1.0, 1, seller);
        Offer noMatch = new Offer("banana", "banana-description", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(onlyNameMatch);
        offers.add(nameAndDescriptionMatch);
        offers.add(noMatch);

        when(offerRepository.getAll()).thenReturn(offers);

        ArrayList<Offer> results = offerSearchService.match("apple apple-description");

        assertEquals(2, results.size());
        assertSame(nameAndDescriptionMatch, results.get(0));
        assertSame(onlyNameMatch, results.get(1));
    }

    @Test
    void resultsAreSortedByScoreDescending() {
        Seller seller = newSeller();

        Offer highScore = new Offer("apple banana", "cherry", 1.0, 1, seller);
        Offer mediumScore = new Offer("apple", "banana", 1.0, 1, seller);
        Offer lowScore = new Offer("apple", "totally-different", 1.0, 1, seller);

        ArrayList<Offer> offers = new ArrayList<>();
        offers.add(lowScore);
        offers.add(highScore);
        offers.add(mediumScore);

        when(offerRepository.getAll()).thenReturn(offers);

        ArrayList<Offer> results = offerSearchService.match("apple banana cherry");

        assertEquals(3, results.size());
        assertSame(highScore, results.get(0));
        assertSame(mediumScore, results.get(1));
        assertSame(lowScore, results.get(2));
    }
}