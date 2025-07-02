package com.homeypark.web_service.parkings.interfaces.rest;

import com.homeypark.web_service.parkings.domain.model.entities.Location;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllLocationsQuery;
import com.homeypark.web_service.parkings.domain.services.LocationCommandService;
import com.homeypark.web_service.parkings.domain.services.LocationQueryService;
import com.homeypark.web_service.parkings.interfaces.rest.resources.LocationResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.UpdateLocationResource;
import com.homeypark.web_service.parkings.interfaces.rest.transform.LocationResourceFromEntityAssembler;
import com.homeypark.web_service.parkings.interfaces.rest.transform.UpdateLocationCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/location")
@Tag(name = "Location", description = "Location Management Endpoints")
public class LocationController {
    private final LocationCommandService locationCommandService;
    private final LocationQueryService locationQueryService;

    public LocationController(LocationCommandService locationCommandService, LocationQueryService locationQueryService) {
        this.locationCommandService = locationCommandService;
        this.locationQueryService = locationQueryService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResource> updateLocation(@PathVariable Long id, @Valid @RequestBody UpdateLocationResource updateLocationResource) {
        var updateLocationCommand = UpdateLocationCommandFromResourceAssembler.toCommandFromResource(id, updateLocationResource);
        var updatedLocation = locationCommandService.handle(updateLocationCommand);
        var resource = LocationResourceFromEntityAssembler.toResourceFromEntity(updatedLocation.orElseThrow(() -> new IllegalArgumentException("Location not found")));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LocationResource>> getAllLocation(){
        var getAllLocationQuery = new GetAllLocationsQuery();
        var locationList = locationQueryService.handle(getAllLocationQuery);

        var resource = locationList.stream().map(LocationResourceFromEntityAssembler::toResourceFromEntity).toList();

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
