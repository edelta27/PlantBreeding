package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enumeration.HealthStatus;
import com.plantbreeding.domain.enumeration.PlantType;
import com.plantbreeding.domain.errors.PlantNotFoundException;
import com.plantbreeding.domain.service.PlantService;
import com.plantbreeding.infrastructure.dto.request.CreatePlantRequestDto;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import com.plantbreeding.infrastructure.dto.response.*;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
        List<Plant> filteredPlants = plantService.findFilteredPlants(isAnnual, type, actualLimit);
        GetAllPlantsResponseDto response = new GetAllPlantsResponseDto(filteredPlants);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<String> postPlant(@RequestBody @Valid CreatePlantRequestDto plant){
        plantService.addPlant(plant);
        return ResponseEntity.status(HttpStatus.CREATED).body("Plant added successfully");
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<String> updatePlant(@PathVariable Long id,
                                              @RequestParam HealthStatus healthStatus,
                                              @RequestParam Integer height) {
        plantService.updatePlantHealthAndHeight(id, healthStatus, height);
        return ResponseEntity.ok("Plant updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletePlantResponseDto> deletePlant(@PathVariable Long id){
        plantService.deletePlant(id);
        return ResponseEntity.ok(new DeletePlantResponseDto("You deleted plant with id: " + id, HttpStatus.OK));
    }

}
