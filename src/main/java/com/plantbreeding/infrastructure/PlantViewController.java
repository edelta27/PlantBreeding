package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.service.PlantRetreiver;
import com.plantbreeding.infrastructure.dto.GetAllPlantsResponseDto;
import com.plantbreeding.infrastructure.dto.request.CreatePlantRequestDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantViewController {
    private final PlantRetreiver plantService;
    private final PlantMapper plantMapper;

    public PlantViewController(PlantRetreiver plantService, PlantMapper plantMapper) {
        this.plantService = plantService;
        this.plantMapper = plantMapper;
    }

    @GetMapping("/plants")
    public String showPlants(Model model) {
        List<Plant> plants = plantService.findAll();
        List<CreatePlantRequestDto> plantDtos = plants.stream()
                .map(plantMapper::toDto)
                .toList();
        model.addAttribute("plants", plantDtos);
        return "plants"; // nazwa pliku HTML w folderze `resources/templates`
    }
}
