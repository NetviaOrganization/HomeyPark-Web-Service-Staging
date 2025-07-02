package com.homeypark.web_service.parkings.application.acl;

import com.homeypark.web_service.parkings.infrastructure.persistence.jpa.repositories.ParkingRepository;
import com.homeypark.web_service.parkings.interfaces.acl.ParkingContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ParkingContextFacadeImpl implements ParkingContextFacade {
    private final ParkingRepository parkingRepository;

    public ParkingContextFacadeImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }
    @Override
    public boolean checkParkingExistById(Long parkingId) {
        var parking = parkingRepository.findById(parkingId);
        return parking.isPresent();
    }
}
