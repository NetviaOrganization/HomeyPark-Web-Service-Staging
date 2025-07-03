package com.homeypark.web_service.reviews.interfaces.rest.transform;

import com.homeypark.web_service.reviews.domain.model.commands.UpdateReviewCommand;
import com.homeypark.web_service.reviews.interfaces.rest.resources.UpdateReviewResource;

public class UpdateReviewCommandFromResourceAssembler {
    public static UpdateReviewCommand toCommandFromResource(Long reviewId, UpdateReviewResource resource) {
        return new UpdateReviewCommand(
                reviewId,
                resource.rating(),
                resource.comment()
        );
    }
}
