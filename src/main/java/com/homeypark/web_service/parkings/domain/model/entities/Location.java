package com.homeypark.web_service.parkings.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateLocationCommand;
import com.homeypark.web_service.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class Location extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    private Parking parking;

    private String address;
    private String numDirection;
    private String street;
    private String district;
    private String city;
    private Double latitude;
    private Double longitude;

    public Location updateLocation(UpdateLocationCommand command) {
        this.address = command.address();
        this.numDirection = command.numDirection();
        this.street = command.street();
        this.district = command.district();
        this.city = command.city();
        this.latitude = command.latitude();
        this.longitude = command.longitude();

        return this;
    }
}