package com.homeypark.web_service.vehicles.domain.model.aggregates;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.homeypark.web_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.homeypark.web_service.vehicles.domain.model.valueobjects.ProfileId;
import com.homeypark.web_service.vehicles.domain.model.commands.CreateVehicleCommand;
import com.homeypark.web_service.vehicles.domain.model.commands.UpdateVehicleCommand;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicles")

public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle> {
    private String licensePlate;
    private String model;
    private String brand;

    @Embedded
    private ProfileId profileId;

    public Vehicle(String licensePlate, String model, String brand, Long profileId) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
        this.profileId = new ProfileId(profileId);
    }


    public Vehicle(CreateVehicleCommand command) {
        this.licensePlate = command.licensePlate();
        this.model = command.model();
        this.brand = command.brand();
        this.profileId = new ProfileId(command.profileId());
    }

    public Vehicle updatedVehicle(UpdateVehicleCommand command) {
        this.licensePlate = command.licensePlate();
        this.model = command.model();
        this.brand = command.brand();
        return this;
    }
}