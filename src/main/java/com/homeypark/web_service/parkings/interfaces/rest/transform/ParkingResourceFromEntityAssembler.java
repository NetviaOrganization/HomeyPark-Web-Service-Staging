package com.homeypark.web_service.parkings.interfaces.rest.transform;

import com.homeypark.web_service.parkings.application.internal.outboundservices.acl.ExternalReviewService;
import com.homeypark.web_service.parkings.application.internal.outboundservices.acl.ExternalUserService;
import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.interfaces.rest.resources.ParkingResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ParkingResourceFromEntityAssembler {
    private final ExternalUserService externalUserService;
    private final ExternalReviewService externalReviewService;

    public ParkingResourceFromEntityAssembler(@Qualifier("parkingExternalUserService") ExternalUserService externalUserService,
                                            @Qualifier("parkingExternalReviewService") ExternalReviewService externalReviewService) {
        this.externalUserService = externalUserService;
        this.externalReviewService = externalReviewService;
    }

    public ParkingResource toResourceFromEntity(Parking entity){
        var userInfo = externalUserService.getUserInfoByProfileId(entity.getProfileId().profileIdAsPrimitive());
        var averageRating = externalReviewService.getAverageRatingByParkingId(entity.getId());
        var reviewCount = externalReviewService.getReviewCountByParkingId(entity.getId());
        
        return new ParkingResource(
                entity.getId(),
                entity.getProfileId().profileIdAsPrimitive(),
                entity.getWidth(),
                entity.getLength(),
                entity.getHeight(),
                entity.getPrice(),
                entity.getPhone(),
                entity.getSpace(),
                entity.getDescription(),
                LocationResourceFromEntityAssembler.toResourceFromEntity(entity.getLocation()),
                entity.getSchedules().stream().map(ScheduleResourceFromEntityAssembler::toResourceFromEntity).toList(),
                userInfo,
                averageRating,
                reviewCount,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
