package com.homeypark.web_service.parkings.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.CreateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;
import com.homeypark.web_service.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    @JsonBackReference
    private Parking parking;

    @Enumerated(EnumType.STRING)
    private WeekDay day;
    private LocalTime startTime;
    private LocalTime endTime;

    public Schedule(CreateScheduleCommand command) {
        this.day = WeekDay.valueOf(command.day());
        this.startTime = command.startTime();
        this.endTime = command.endTime();
    }

    public Schedule updatedSchedule(UpdateScheduleCommand command) {
        this.day = WeekDay.valueOf(command.day());
        this.startTime = command.startTime();
        this.endTime = command.endTime();
        return this;
    }
}