package com.plantbreeding.infrastructure;

import com.plantbreeding.dao.PlantRepository;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enumeration.PlantType;
import com.plantbreeding.domain.errors.PlantNotFoundException;
import com.plantbreeding.domain.service.PlantService;
import com.plantbreeding.infrastructure.dto.request.CreatePlantRequestDto;
import com.plantbreeding.infrastructure.dto.response.*;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class PlantRestController {
    private final PlantService plantService;
    private final PlantRepository plantRepository;


    public PlantRestController(PlantService plantService, PlantRepository plantRepository) {
        this.plantService = plantService;
        this.plantRepository = plantRepository;
    }

    @GetMapping("/plants")
    public ResponseEntity<GetAllPlantsResponseDto> getAllPlants(@RequestParam(required = false) Integer limit,
                                                                @RequestParam(required = false) Boolean isAnnual,
                                                                @RequestParam(required = false) PlantType type){
        List<Plant> plants = plantService.findAll();
        List<Plant> filteredPlants = plants.stream()
                .filter(plant -> isAnnual == null || plant.getAnnual().equals(isAnnual))
                .filter(plant -> type == null || plant.getType().equals(type))
                .limit(limit != null ? limit : plants.size())
                .collect(Collectors.toList());

        GetAllPlantsResponseDto response = new GetAllPlantsResponseDto(filteredPlants);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plants/{id}")
    public ResponseEntity<GetPlantResponseDto> getPlantByID(@PathVariable Long id, @RequestHeader(required = false) String requestId){
        log.info(requestId);
         Plant plant = plantRepository.findById(id)
            .orElseThrow(() -> new PlantNotFoundException("Plant with id " + id + " not found"));
        GetPlantResponseDto response = new GetPlantResponseDto(plant);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/plants")
    public ResponseEntity<String> postPlant(@RequestBody @Valid CreatePlantRequestDto plant){
        plantService.addPlant(plant);
        return ResponseEntity.status(HttpStatus.CREATED).body("Plant added successfully");
    }

}
