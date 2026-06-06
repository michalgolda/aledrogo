package org.aledrogo.repository;

import org.aledrogo.entity.Offer;

import java.util.ArrayList;

public class MemoryOfferRepository extends OfferRepository {
    public ArrayList<Offer> offers = new ArrayList<>();


    @Override
    public Offer getById(int id) {
        for (Offer offer : offers) {
            if (offer.getId() == id) {
                return offer;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Offer> getAll() {
        return offers;
    }

    @Override
    public Offer create(Offer entity) {
        offers.add(entity);
        return entity;
    }

    @Override
    public Offer update(Offer entity) {
        ArrayList<Offer> offersForReplace = new ArrayList<>();
        for (Offer offer : offers) {
            if (offer.getId() != entity.getId()) {
                offersForReplace.add(entity);
            }
        }
        offersForReplace.add(entity);
        offers = offersForReplace;
        return entity;
    }

    @Override
    public void delete(Offer entity) {
        ArrayList<Offer> offersForReplace = new ArrayList<>();
        for (Offer offer : offers) {
            if (offer.getId() != entity.getId()) {
                offersForReplace.add(entity);
            }
        }
        offers = offersForReplace;
    }
}
