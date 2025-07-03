package com.homeypark.web_service.reviews.interfaces.rest.transform;

import com.homeypark.web_service.reviews.domain.model.commands.CreateReviewCommand;
import com.homeypark.web_service.reviews.interfaces.rest.resources.CreateReviewResource;

public class CreateReviewCommandFromResourceAssembler {
    public static CreateReviewCommand toCommandFromResource(CreateReviewResource resource) {
        return new CreateReviewCommand(
                resource.rating(),
                resource.comment(),
                resource.parkingId(),
                resource.userId()
        );
    }
}
