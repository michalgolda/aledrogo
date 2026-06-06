package org.aledrogo.service;

import org.aledrogo.entity.Offer;

import java.util.ArrayList;

public interface OfferSearchService {
    public ArrayList<Offer> match(String query);
}
