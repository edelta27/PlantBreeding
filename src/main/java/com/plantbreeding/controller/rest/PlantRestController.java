package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.service.PlantService;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.GetAllPlantsResponseDto;
import com.plantbreeding.dto.response.MessageResponseDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plants")
@Log4j2
public class PlantRestController {
    private final PlantService plantService;
    public PlantRestController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping()
    public ResponseEntity<GetAllPlantsResponseDto> getAllPlants(@RequestParam(required = false) Integer limit,
                                                                @RequestParam(required = false) Boolean isAnnual,
                                                                @RequestParam(required = false) PlantType type){
        int actualLimit = (limit != null) ? limit : Integer.MAX_VALUE;
        List<PlantDto> filteredPlants = plantService.findFilteredPlants(isAnnual, type, actualLimit);
        GetAllPlantsResponseDto response = new GetAllPlantsResponseDto(filteredPlants);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlantDto> getPlantByID(@PathVariable Long id, @RequestHeader(required = false) String requestId){
        log.info("Request ID: {}", requestId != null ? requestId : id);
        PlantDto response = plantService.getPlantById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<PlantWithTasksDto> getPlantWithTasks(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getPlantWithTasks(id));
    }

    @PostMapping()
    public ResponseEntity<MessageResponseDto> postPlant(@RequestBody @Valid PlantDto plantDto){
        plantService.addPlant(plantDto);
        return ResponseEntity.ok(new MessageResponseDto("Plant added successfully", HttpStatus.OK));
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<MessageResponseDto> updatePlant(@PathVariable Long id,
                                              @RequestParam HealthStatus healthStatus,
                                              @RequestParam Integer height) {
        plantService.updatePlantHealthAndHeight(id, healthStatus, height);
        return ResponseEntity.ok(new MessageResponseDto("Plant updated successfully with id: " + id, HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deletePlant(@PathVariable Long id){
        plantService.deletePlant(id);
        return ResponseEntity.ok(new MessageResponseDto("You deleted plant with id: " + id, HttpStatus.OK));
    }

}
