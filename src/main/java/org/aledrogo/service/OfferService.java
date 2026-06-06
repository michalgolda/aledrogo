package org.aledrogo.service;

import org.aledrogo.entity.Offer;
import org.aledrogo.entity.OfferReport;
import org.aledrogo.entity.User;
import org.aledrogo.repository.OfferReportRepository;
import org.aledrogo.repository.OfferRepository;

public class OfferService {
    public final OfferRepository offerRepository;
    public final OfferReportRepository offerReportRepository;

    public OfferService(OfferRepository offerRepository, OfferReportRepository offerReportRepository) {
        this.offerRepository = offerRepository;
        this.offerReportRepository = offerReportRepository;
    }

    public Offer createOffer(Offer offer) {
        Offer createdOffer = offerRepository.create(offer);
        return createdOffer;
    }

    public void deleteOffer(int offerId) throws Exception {
        Offer existingOffer = offerRepository.getById(offerId);
        if (existingOffer == null) {
            throw new Exception("Oferta o podanym id nie istnieje");
        }

        offerRepository.delete(existingOffer);
    }

    public Offer updateOffer(Offer offer) {
        Offer updatedOffer = offerRepository.update(offer);
        return updatedOffer;
    }

    public OfferReport reportOffer(Offer offer, String reason, User reportedBy)
    {
        OfferReport offerReport = new OfferReport(reason, offer, reportedBy);
        offerReportRepository.create(offerReport);
        return offerReport;
    }
}
