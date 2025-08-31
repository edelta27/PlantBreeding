package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import com.plantbreeding.service.PlantService;
import com.plantbreeding.dto.request.PlantDto;
import com.plantbreeding.dto.response.MessageResponseDto;
import com.plantbreeding.dto.response.PlantWithTasksDto;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
     * @param pageable optional filter by plant
     * @param isAnnual optional filter by plant is annual
     * @param type optional filter by plant type (e.g., VEGETABLE, FRUIT)
     * @return list of {@link PlantDto} matching the filters or all plants if no filters are provided
     */
    @GetMapping()
    public ResponseEntity<Page<PlantDto>> getAllPlants(@RequestParam(required = false) Boolean isAnnual,
                                                       @RequestParam(required = false) PlantType type,
                                                       @PageableDefault(size = 10, page = 0) Pageable pageable){

        Page<PlantDto> filteredPlants = plantService.findFilteredPlants(isAnnual, type, pageable);
        return ResponseEntity.ok(filteredPlants);
    }

    /**
     * Retrieves a plant by its unique identifier.
     *
     * @param id the ID of the plant to retrieve
     * @return the {@link PlantDto} for the specified ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<PlantDto> getPlantByID(@PathVariable Long id, @RequestHeader(required = false) String requestId){
        log.info("Request received: requestId={}, plantId={}", requestId, id);
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
     * @return the created plant
     */
    @PostMapping()
    public ResponseEntity<PlantDto> postPlant(@RequestBody @Valid PlantDto plantDto){
        PlantDto createdPlant = plantService.addPlant(plantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlant);
    }

    /**
     * Updates an existing plant.
     *
     * @param id           the ID of the plant to update
     * @param healthStatus the updated plant health status
     * @param height       the updated plant height
     * @return the updated Plant
     */
    @PatchMapping ("/{id}")
    public ResponseEntity<PlantDto> updatePlant(@PathVariable Long id,
                                              @RequestParam HealthStatus healthStatus,
                                              @RequestParam Integer height) {
        PlantDto updatePlant = plantService.updatePlantHealthAndHeight(id, healthStatus, height);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatePlant);
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
