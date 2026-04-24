package org.aledrogo.entity;

public class OfferReport {
    private String reason;
    private final Offer offer;
    private OfferReportStatus status;
    private final User reportedBy;
    private final Moderator reviewedBy;

    public OfferReport(String reason, Offer offer, User reportedBy) {
        this.reason = reason;
        this.status = OfferReportStatus.PENDING;
        this.offer = offer;
        this.reportedBy = reportedBy;
        this.reviewedBy = null;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public OfferReportStatus getStatus() {
        return status;
    }

    public void setStatus(OfferReportStatus status) {
        this.status = status;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public Offer getOffer() {
        return offer;
    }

    public Moderator getReviewedBy() {
        return reviewedBy;
    }
}
