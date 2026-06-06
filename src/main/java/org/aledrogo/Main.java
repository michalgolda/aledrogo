package org.aledrogo;

import org.aledrogo.GreetingService;
import org.aledrogo.repository.*;
import org.aledrogo.service.DummyOfferOfferSearchService;
import org.aledrogo.service.OfferSearchService;
import org.aledrogo.service.OfferService;

public class Main {
//    Repositories
    public final UserRepository userRepository = new MemoryUserRepository();
    public final OfferRepository offerRepository = new MemoryOfferRepository();
    public final OfferReportRepository offerReportRepository = new MemoryOfferReportRepository();
    public final OrderReviewRepository orderReviewRepository = new MemoryOrderReviewRepository();

//    Services
    public final OfferSearchService offerSearchService = new DummyOfferOfferSearchService(offerRepository);
    public final OfferService offerService = new OfferService(offerRepository, offerReportRepository);


    public static void main(String[] args) {
        GreetingService greetingService = new GreetingService();
        String message = greetingService.execute("World");
        System.out.println(message);
    }
}