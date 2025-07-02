package com.homeypark.web_service.reservations.application.internal.outboundservices.acl;

import com.homeypark.web_service.parkings.interfaces.acl.ParkingContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalParkingService {
    private final ParkingContextFacade parkingContextFacade;

    public ExternalParkingService(ParkingContextFacade parkingContextFacade) {
        this.parkingContextFacade = parkingContextFacade;
    }

    public boolean checkParkingExistById(Long parkingId) {
        return parkingContextFacade.checkParkingExistById(parkingId);
    }
}
