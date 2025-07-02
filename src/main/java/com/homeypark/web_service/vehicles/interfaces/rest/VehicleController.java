package com.homeypark.web_service.vehicles.interfaces.rest;

import com.homeypark.web_service.vehicles.domain.model.commands.DeleteVehicleCommand;
import com.homeypark.web_service.vehicles.domain.model.queries.GetAllVehiclesQuery;
import com.homeypark.web_service.vehicles.domain.model.queries.GetVehicleByIdQuery;
import com.homeypark.web_service.vehicles.domain.model.queries.GetVehiclesByProfileIdQuery;
import com.homeypark.web_service.vehicles.domain.services.VehicleCommandService;
import com.homeypark.web_service.vehicles.domain.services.VehicleQueryService;
import com.homeypark.web_service.vehicles.interfaces.rest.resources.CreateVehicleResource;
import com.homeypark.web_service.vehicles.interfaces.rest.resources.UpdateVehicleResource;
import com.homeypark.web_service.vehicles.interfaces.rest.resources.VehicleResource;
import com.homeypark.web_service.vehicles.interfaces.rest.transform.CreateVehicleCommandFromResourceAssembler;
import com.homeypark.web_service.vehicles.interfaces.rest.transform.UpdateVehicleCommandFromResource;
import com.homeypark.web_service.vehicles.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/vehicles")
public class VehicleController {

    private final VehicleQueryService vehicleQueryService;
    private final VehicleCommandService vehicleCommandService;

    public VehicleController(VehicleQueryService vehicleQueryService, VehicleCommandService vehicleCommandService) {
        this.vehicleQueryService = vehicleQueryService;
        this.vehicleCommandService = vehicleCommandService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long id) {
        var getVehicleByIdQuery = new GetVehicleByIdQuery(id);

        var vehicle = vehicleQueryService.handle(getVehicleByIdQuery).map(VehicleResourceFromEntityAssembler::toResourceFromEntity);

        return vehicle.map(u -> new ResponseEntity<>(u, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<List<VehicleResource>> getVehiclesByUserId(@PathVariable Long id) {
        var getVehiclesByUserIdQuery = new GetVehiclesByProfileIdQuery(id);

        var vehicleList = vehicleQueryService.handle(getVehiclesByUserIdQuery).stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList();

        return new ResponseEntity<>(vehicleList, HttpStatus.OK);
    }



    @PostMapping("/create")
    public ResponseEntity<VehicleResource> createVehicle(@Valid @RequestBody CreateVehicleResource resource) {
        return vehicleCommandService.handle(CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource))
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .map(vehicle -> ResponseEntity.status(HttpStatus.CREATED).body(vehicle))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAllVehicles(){
        var getAllVehiclesQuery = new GetAllVehiclesQuery();
        var vehicleList = vehicleQueryService.handle(getAllVehiclesQuery).stream().map(VehicleResourceFromEntityAssembler::toResourceFromEntity).toList();
        return new ResponseEntity<>(vehicleList,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VehicleResource> updateVehicle(@PathVariable Long id, @Valid @RequestBody UpdateVehicleResource updateVehicleResource) {
        var updateVehicleCommand = UpdateVehicleCommandFromResource.toCommandFromResource(id, updateVehicleResource);
        var updatedVehicle = vehicleCommandService.handle(updateVehicleCommand).map(VehicleResourceFromEntityAssembler::toResourceFromEntity);
        return updatedVehicle.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id){
        var deleteVehicleCommand = new DeleteVehicleCommand(id);
        vehicleCommandService.handle(deleteVehicleCommand);
        return ResponseEntity.noContent().build();
    }

}