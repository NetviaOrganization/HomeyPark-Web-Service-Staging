package com.homeypark.web_service.parkings.interfaces.rest;

import com.homeypark.web_service.parkings.domain.model.entities.Schedule;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllScheduleQuery;
import com.homeypark.web_service.parkings.domain.services.ScheduleCommandService;
import com.homeypark.web_service.parkings.domain.services.ScheduleQueryService;
import com.homeypark.web_service.parkings.interfaces.rest.resources.CreateScheduleResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.ScheduleResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.UpdateScheduleResource;
import com.homeypark.web_service.parkings.interfaces.rest.transform.CreateScheduleCommandFromResourceAssembler;
import com.homeypark.web_service.parkings.interfaces.rest.transform.ScheduleResourceFromEntityAssembler;
import com.homeypark.web_service.parkings.interfaces.rest.transform.UpdateScheduleCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/schedule")
@Tag(name="Schedule", description = "Schedule Management Endpoints")
public class ScheduleController {
    private final ScheduleCommandService scheduleCommandService;
    private final ScheduleQueryService scheduleQueryService;

    public ScheduleController(ScheduleCommandService scheduleCommandService, ScheduleQueryService scheduleQueryService) {
        this.scheduleCommandService = scheduleCommandService;
        this.scheduleQueryService = scheduleQueryService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResource> updateSchedule(@PathVariable Long id, @RequestBody UpdateScheduleResource updateScheduleResource){

        var updateScheduleCommand = UpdateScheduleCommandFromResourceAssembler.toCommandFromResource(id, updateScheduleResource);

        var updatedSchedule = scheduleCommandService.handle(updateScheduleCommand).map(ScheduleResourceFromEntityAssembler::toResourceFromEntity);

        return updatedSchedule.map(p -> new ResponseEntity<>(p, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping
    public ResponseEntity<ScheduleResource> createSchedule(@Valid @RequestBody CreateScheduleResource createScheduleResource){
        var createScheduleCommand = CreateScheduleCommandFromResourceAssembler.toCommandFromResource(createScheduleResource);
        var schedule = scheduleCommandService.handle(createScheduleCommand).map(ScheduleResourceFromEntityAssembler::toResourceFromEntity);
        return schedule.map(p->new ResponseEntity<>(p,HttpStatus.CREATED)).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResource>> getAllSchedule(){
        var getAllScheduleQuery = new GetAllScheduleQuery();
        var scheduleList = scheduleQueryService.handle(getAllScheduleQuery);
        var resource = scheduleList.stream().map(ScheduleResourceFromEntityAssembler::toResourceFromEntity).toList();
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
