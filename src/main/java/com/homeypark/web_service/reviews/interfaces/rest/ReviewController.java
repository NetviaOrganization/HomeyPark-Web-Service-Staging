package com.homeypark.web_service.reviews.interfaces.rest;

import com.homeypark.web_service.reviews.domain.model.commands.DeleteReviewCommand;
import com.homeypark.web_service.reviews.domain.model.queries.*;
import com.homeypark.web_service.reviews.domain.services.ReviewCommandService;
import com.homeypark.web_service.reviews.domain.services.ReviewQueryService;
import com.homeypark.web_service.reviews.interfaces.rest.resources.CreateReviewResource;
import com.homeypark.web_service.reviews.interfaces.rest.resources.ReviewResource;
import com.homeypark.web_service.reviews.interfaces.rest.resources.UpdateReviewResource;
import com.homeypark.web_service.reviews.interfaces.rest.transform.CreateReviewCommandFromResourceAssembler;
import com.homeypark.web_service.reviews.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import com.homeypark.web_service.reviews.interfaces.rest.transform.UpdateReviewCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller that exposes the reviews resource.
 * It includes the following operations:
 * - GET /api/v1/reviews: returns all reviews
 * - GET /api/v1/reviews/{reviewId}: returns the review with the given id
 * - GET /api/v1/reviews?parkingId={parkingId}: returns all reviews for a parking
 * - GET /api/v1/reviews?userId={userId}: returns all reviews by a user
 * - POST /api/v1/reviews: creates a new review
 * - PUT /api/v1/reviews/{reviewId}: updates the review with the given id
 * - DELETE /api/v1/reviews/{reviewId}: deletes the review with the given id
 */
@RestController
@RequestMapping(value = "/api/v1/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reviews", description = "Review Management Endpoints")
public class ReviewController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewController(ReviewCommandService reviewCommandService, ReviewQueryService reviewQueryService) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    /**
     * This method returns all reviews or filters by parkingId or userId.
     *
     * @param parkingId optional parking id filter
     * @param userId optional user id filter
     * @return a list of review resources
     */
    @GetMapping
    public ResponseEntity<List<ReviewResource>> getAllReviews(
            @RequestParam(required = false) Long parkingId,
            @RequestParam(required = false) Long userId) {
        
        List<ReviewResource> reviewResources;
        
        if (parkingId != null) {
            var getReviewsByParkingIdQuery = new GetReviewsByParkingIdQuery(parkingId);
            var reviews = reviewQueryService.handle(getReviewsByParkingIdQuery);
            reviewResources = reviews.stream()
                    .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else if (userId != null) {
            var getReviewsByUserIdQuery = new GetReviewsByUserIdQuery(userId);
            var reviews = reviewQueryService.handle(getReviewsByUserIdQuery);
            reviewResources = reviews.stream()
                    .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else {
            var getAllReviewsQuery = new GetAllReviewsQuery();
            var reviews = reviewQueryService.handle(getAllReviewsQuery);
            reviewResources = reviews.stream()
                    .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }
        
        return ResponseEntity.ok(reviewResources);
    }

    /**
     * This method returns the review with the given id.
     *
     * @param reviewId the review id
     * @return the review resource with the given id
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResource> getReviewById(@PathVariable Long reviewId) {
        var getReviewByIdQuery = new GetReviewByIdQuery(reviewId);
        var review = reviewQueryService.handle(getReviewByIdQuery);
        if (review.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return ResponseEntity.ok(reviewResource);
    }

    /**
     * This method creates a new review.
     *
     * @param createReviewResource the create review request body
     * @return the created review resource
     */
    @PostMapping
    public ResponseEntity<ReviewResource> createReview(@Valid @RequestBody CreateReviewResource createReviewResource) {
        var createReviewCommand = CreateReviewCommandFromResourceAssembler.toCommandFromResource(createReviewResource);
        var review = reviewCommandService.handle(createReviewCommand);
        if (review.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return new ResponseEntity<>(reviewResource, HttpStatus.CREATED);
    }

    /**
     * This method updates the review with the given id.
     *
     * @param reviewId the review id
     * @param updateReviewResource the update review request body
     * @return the updated review resource
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResource> updateReview(@PathVariable Long reviewId, 
                                                     @Valid @RequestBody UpdateReviewResource updateReviewResource) {
        var updateReviewCommand = UpdateReviewCommandFromResourceAssembler.toCommandFromResource(reviewId, updateReviewResource);
        var review = reviewCommandService.handle(updateReviewCommand);
        if (review.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(review.get());
        return ResponseEntity.ok(reviewResource);
    }

    /**
     * This method deletes the review with the given id.
     *
     * @param reviewId the review id
     * @return a success message
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        var deleteReviewCommand = new DeleteReviewCommand(reviewId);
        reviewCommandService.handle(deleteReviewCommand);
        return ResponseEntity.ok("Review with given id successfully deleted");
    }
}
