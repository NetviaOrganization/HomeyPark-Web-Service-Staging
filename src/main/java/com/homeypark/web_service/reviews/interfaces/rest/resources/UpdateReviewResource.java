package com.homeypark.web_service.reviews.interfaces.rest.resources;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateReviewResource(
        @NotNull
        @Min(1)
        @Max(5)
        Integer rating,
        
        String comment
) {
}
