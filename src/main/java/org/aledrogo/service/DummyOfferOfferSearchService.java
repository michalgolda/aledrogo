package org.aledrogo.service;

import org.aledrogo.entity.Offer;
import org.aledrogo.repository.OfferRepository;
import org.aledrogo.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DummyOfferOfferSearchService implements OfferSearchService {
    public final OfferRepository offerRepository;

    public DummyOfferOfferSearchService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    @Override
    public ArrayList<Offer> match(String query) {
        ArrayList<Offer> offers = this.offerRepository.getAll();

        String words[] = query.trim().split("\\s+");
        Map<Offer, Integer> offerToScore = new HashMap<>();

        for (Offer offer : offers) {
            for (String word : words) {
                if (offer.getName().contains(word) || offer.getDescription().contains(word)) {
                    offerToScore.put(offer, offerToScore.getOrDefault(offer, 0) + 1);
                }
            }
        }

        Offer results[] = offerToScore.entrySet().stream()
                .sorted(Map.Entry.<Offer, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toArray(Offer[]::new);

        return new ArrayList<>(java.util.Arrays.asList(results));
    }
}
