package org.aledrogo.service;

import org.aledrogo.entity.Offer;
import org.aledrogo.entity.OfferReport;
import org.aledrogo.entity.User;
import org.aledrogo.repository.OfferReportRepository;
import org.aledrogo.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferReportRepository offerReportRepository;

    @InjectMocks
    private OfferService offerService;

    @Test
    void createOfferReturnsCreatedOffer() {
        Offer offer = mock(Offer.class);
        when(offerRepository.create(offer)).thenReturn(offer);

        Offer result = offerService.createOffer(offer);

        assertSame(offer, result);
        verify(offerRepository).create(offer);
    }

    @Test
    void deleteOfferDeletesExistingOffer() throws Exception {
        Offer offer = mock(Offer.class);
        when(offerRepository.getById(1)).thenReturn(offer);

        offerService.deleteOffer(1);

        verify(offerRepository).delete(offer);
    }

    @Test
    void deleteOfferThrowsWhenOfferDoesNotExist() {
        when(offerRepository.getById(99)).thenReturn(null);

        Exception ex = assertThrows(Exception.class, () -> offerService.deleteOffer(99));

        assertEquals("Oferta o podanym id nie istnieje", ex.getMessage());
        verify(offerRepository, never()).delete(any());
    }

    @Test
    void updateOfferReturnsUpdatedOffer() {
        Offer offer = mock(Offer.class);
        when(offerRepository.update(offer)).thenReturn(offer);

        Offer result = offerService.updateOffer(offer);

        assertSame(offer, result);
        verify(offerRepository).update(offer);
    }


    @Test
    void reportOfferReturnsOfferReportWithCorrectData() {
        Offer offer = mock(Offer.class);
        User user = mock(User.class);

        OfferReport result = offerService.reportOffer(offer, "spam", user);

        assertNotNull(result);
        assertEquals("spam", result.getReason());
        assertSame(offer, result.getOffer());
        assertSame(user, result.getReportedBy());
    }

    @Test
    void reportOfferCallsRepositoryCreate() {
        Offer offer = mock(Offer.class);
        User user = mock(User.class);

        offerService.reportOffer(offer, "spam", user);

        verify(offerReportRepository).create(any(OfferReport.class));
    }
}