package com.homeypark.web_service.parkings.application.internal.queryservices;

import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllParkingQuery;
import com.homeypark.web_service.parkings.domain.model.queries.GetParkingByIdQuery;
import com.homeypark.web_service.parkings.domain.model.queries.GetParkingListByProfileId;
import com.homeypark.web_service.parkings.domain.model.queries.GetParkingsByNearLatLngQuery;
import com.homeypark.web_service.parkings.domain.services.ParkingQueryService;
import com.homeypark.web_service.parkings.infrastructure.persistence.jpa.repositories.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingQueryServiceImpl implements ParkingQueryService {

    private final ParkingRepository parkingRepository;

    public ParkingQueryServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<Parking> handle(GetAllParkingQuery query) {
        return parkingRepository.findAll();
    }

    @Override
    public Optional<Parking> handle(GetParkingByIdQuery query) {
        return parkingRepository.findById(query.parkingId());
    }

    @Override
    public List<Parking> handle(GetParkingListByProfileId query) {
        return parkingRepository.findByProfileId(query.profileId());
    }

    @Override
    public List<Parking> handle(GetParkingsByNearLatLngQuery query) {

        List<Parking> parkingList = parkingRepository.findAll();

        parkingList.removeIf(parking -> {
            double earthRadius = 6371; // Radio de la Tierra en kilómetros
            double dLat = Math.toRadians(parking.getLocation().getLatitude() - query.latitude());
            double dLng = Math.toRadians(parking.getLocation().getLongitude() - query.longitude());
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(Math.toRadians(query.latitude())) * Math.cos(Math.toRadians(parking.getLocation().getLatitude())) *
                            Math.sin(dLng / 2) * Math.sin(dLng / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadius * c;
            return distance > 1;
        });

        return parkingList;
    }
}
