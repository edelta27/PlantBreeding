package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.service.PlantService;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.MessageResponseDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing plants.
 * Provides endpoints for retrieving, adding, updating and deleting.
 */
@RestController
@RequestMapping("/plants")
@Log4j2
public class PlantRestController {
    private final PlantService plantService;
    public PlantRestController(PlantService plantService) {
        this.plantService = plantService;
    }

    /**
     * Retrieves a list of all plants with optional filtering parameters.
     *
     * @param limit optional filter by plant
     * @param isAnnual optional filter by plant is annual
     * @param type optional filter by plant type (e.g., VEGETABLE, FRUIT)
     * @return list of {@link PlantDto} matching the filters or all plants if no filters are provided
     */
    @GetMapping()
    public ResponseEntity<List<PlantDto>> getAllPlants(@RequestParam(required = false) Integer limit,
                                                                @RequestParam(required = false) Boolean isAnnual,
                                                                @RequestParam(required = false) PlantType type){
        int actualLimit = (limit != null) ? limit : Integer.MAX_VALUE;
        List<PlantDto> filteredPlants = plantService.findFilteredPlants(isAnnual, type, actualLimit);
        return ResponseEntity.ok(filteredPlants);
    }

    /**
     * Retrieves a plant by its unique identifier.
     *
     * @param id the ID of the plant to retrieve
     * @return the {@link PlantDto} for the specified ID
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlantDto> getPlantByID(@PathVariable Long id, @RequestHeader(required = false) String requestId){
        log.info("Request ID: {}", requestId != null ? requestId : id);
        PlantDto response = plantService.getPlantById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a plant along with its associated tasks.
     *
     * @param id the ID of the plant to retrieve
     * @return the {@link PlantWithTasksDto} containing plant details and its tasks
     */
    @GetMapping("/{id}/tasks")
    public ResponseEntity<PlantWithTasksDto> getPlantWithTasks(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getPlantWithTasks(id));
    }

    /**
     * Creates a new plant.
     *
     * @param plantDto the plant data to create
     * @return the created {@link PlantDto}
     */
    @PostMapping()
    public ResponseEntity<MessageResponseDto> postPlant(@RequestBody @Valid PlantDto plantDto){
        plantService.addPlant(plantDto);
        return ResponseEntity.ok(new MessageResponseDto("Plant added successfully", HttpStatus.OK));
    }

    /**
     * Updates an existing plant.
     *
     * @param id           the ID of the plant to update
     * @param healthStatus the updated plant health status
     * @param height       the updated plant height
     * @return the updated {@link PlantDto}
     */
    @PatchMapping ("/{id}")
    public ResponseEntity<MessageResponseDto> updatePlant(@PathVariable Long id,
                                              @RequestParam HealthStatus healthStatus,
                                              @RequestParam Integer height) {
        plantService.updatePlantHealthAndHeight(id, healthStatus, height);
        return ResponseEntity.ok(new MessageResponseDto("Plant updated successfully with id: " + id, HttpStatus.OK));
    }

    /**
     * Deletes a plant by its unique identifier.
     *
     * @param id the ID of the plant to delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deletePlant(@PathVariable Long id){
        plantService.deletePlant(id);
        return ResponseEntity.ok(new MessageResponseDto("You deleted plant with id: " + id, HttpStatus.OK));
    }

}
