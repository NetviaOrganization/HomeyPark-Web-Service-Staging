package com.homeypark.web_service.reviews.domain.model.aggregates;

import com.homeypark.web_service.reviews.domain.model.commands.CreateReviewCommand;
import com.homeypark.web_service.reviews.domain.model.commands.UpdateReviewCommand;
import com.homeypark.web_service.reviews.domain.model.valueobjects.ParkingId;
import com.homeypark.web_service.reviews.domain.model.valueobjects.UserId;
import com.homeypark.web_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "reviews")
public class Review extends AuditableAbstractAggregateRoot<Review> {

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 1000)
    private String comment;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "parkingId", column = @Column(name = "parking_id"))
    })
    private ParkingId parkingId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "userId", column = @Column(name = "user_id"))
    })
    private UserId userId;

    public Review(CreateReviewCommand command) {
        this.rating = command.rating();
        this.comment = command.comment();
        this.parkingId = new ParkingId(command.parkingId());
        this.userId = new UserId(command.userId());
    }

    public Review updateReview(UpdateReviewCommand command) {
        this.rating = command.rating();
        this.comment = command.comment();
        return this;
    }

    public Long getParkingIdValue() {
        return this.parkingId.parkingId();
    }

    public Long getUserIdValue() {
        return this.userId.userId();
    }
}
