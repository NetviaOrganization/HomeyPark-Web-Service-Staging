package com.homeypark.web_service.parkings.application.internal.outboundservices.acl;

import com.homeypark.web_service.reviews.interfaces.acl.ReviewContextFacade;
import org.springframework.stereotype.Service;

@Service("parkingExternalReviewService")
public class ExternalReviewService {
    private final ReviewContextFacade reviewContextFacade;

    public ExternalReviewService(ReviewContextFacade reviewContextFacade) {
        this.reviewContextFacade = reviewContextFacade;
    }

    public Double getAverageRatingByParkingId(Long parkingId) {
        return reviewContextFacade.getAverageRatingByParkingId(parkingId);
    }

    public Long getReviewCountByParkingId(Long parkingId) {
        return reviewContextFacade.getReviewCountByParkingId(parkingId);
    }
}
