package org.aledrogo.repository;

import org.aledrogo.entity.Offer;
import org.aledrogo.entity.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryOfferRepositoryTest {

    private MemoryOfferRepository repository;
    private Seller seller;

    @BeforeEach
    void setUp() {
        repository = new MemoryOfferRepository();
        seller = new Seller("seller@example.com", "password", "Jan", "Kowalski", "123456789");
    }

    private Offer newOffer(String name) {
        return new Offer(name, "desc", 10.0, 5, seller);
    }

    @Test
    void createStoresOfferAndGetByIdReturnsIt() {
        Offer offer = repository.create(newOffer("Laptop"));

        assertSame(offer, repository.getById(offer.getId()));
        assertEquals(1, repository.getAll().size());
    }

    @Test
    void getByIdReturnsNullWhenMissing() {
        assertNull(repository.getById(-1));
    }

    @Test
    void updateReplacesMatchingOfferAndKeepsOthers() {
        Offer a = repository.create(newOffer("A"));
        Offer b = repository.create(newOffer("B"));

        a.setName("A-updated");
        repository.update(a);

        assertEquals(2, repository.getAll().size(), "update must not change the number of offers");
        assertEquals("A-updated", repository.getById(a.getId()).getName());
        assertSame(b, repository.getById(b.getId()), "the other offer must be left intact");
    }

    @Test
    void deleteRemovesOnlyTheTargetOffer() {
        Offer a = repository.create(newOffer("A"));
        Offer b = repository.create(newOffer("B"));

        repository.delete(a);

        assertNull(repository.getById(a.getId()), "deleted offer must be gone");
        assertSame(b, repository.getById(b.getId()), "the other offer must remain");
        assertEquals(1, repository.getAll().size());
    }
}
