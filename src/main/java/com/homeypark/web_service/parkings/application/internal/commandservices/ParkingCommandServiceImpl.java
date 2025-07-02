package com.homeypark.web_service.parkings.application.internal.commandservices;

import com.homeypark.web_service.parkings.application.internal.outboundservices.acl.ExternalProfileService;
import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.CreateParkingCommand;
import com.homeypark.web_service.parkings.domain.model.commands.DeleteParkingCommand;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateParkingCommand;
import com.homeypark.web_service.parkings.domain.model.exceptions.ParkingNotFoundException;
import com.homeypark.web_service.parkings.domain.services.ParkingCommandService;
import com.homeypark.web_service.parkings.infrastructure.persistence.jpa.repositories.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingCommandServiceImpl implements ParkingCommandService {
    private final ParkingRepository parkingRepository;
    private final ExternalProfileService externalProfileService;

    public ParkingCommandServiceImpl(ParkingRepository parkingRepository, ExternalProfileService externalProfileService) {
        this.parkingRepository = parkingRepository;
        this.externalProfileService = externalProfileService;
    }

    @Override
    public Optional<Parking> handle(CreateParkingCommand command) {
        if (!externalProfileService.checkProfileExistById(command.profileId())) {
            throw new ParkingNotFoundException();
        }
        var parking = new Parking(command);
        try {
            var response = parkingRepository.save(parking);
            return Optional.of(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Parking> handle(UpdateParkingCommand command) {
        var result = parkingRepository.findById(command.parkingId());

        if (result.isEmpty())
            throw new ParkingNotFoundException();
        try {
            var parking = result.get();

            System.out.println("[DEBUG COMMAND DATA]" + command.location().address() + " " + command.location().numDirection());

            parking.updateParking(command);

            System.out.println("[DEBUG ENTITY]" + parking.getLocation().getAddress() + " " + parking.getLocation().getNumDirection());

            var updatedParking = parkingRepository.save(parking);
            return Optional.of(updatedParking);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void handle(DeleteParkingCommand command) {
        if (!parkingRepository.existsById(command.parkingId())) {
            throw new ParkingNotFoundException();
        }
        parkingRepository.deleteById(command.parkingId());
    }
}
