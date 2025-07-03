package com.homeypark.web_service.reviews.infrastructure.persistence.repositories.jpa;

import com.homeypark.web_service.reviews.domain.model.aggregates.Review;
import com.homeypark.web_service.reviews.domain.model.valueobjects.ParkingId;
import com.homeypark.web_service.reviews.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByParkingId(ParkingId parkingId);
    List<Review> findByUserId(UserId userId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.parkingId = :parkingId")
    Double findAverageRatingByParkingId(@Param("parkingId") ParkingId parkingId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.parkingId = :parkingId")
    Long countReviewsByParkingId(@Param("parkingId") ParkingId parkingId);
}
