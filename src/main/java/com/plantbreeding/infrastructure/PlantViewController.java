package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.service.PlantService;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import com.plantbreeding.infrastructure.mapper.PlantMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class PlantViewController {
    private final PlantService plantService;
    private final PlantMapper plantMapper;

    public PlantViewController(PlantService plantService, PlantMapper plantMapper) {
        this.plantService = plantService;
        this.plantMapper = plantMapper;
    }

    @GetMapping("/plants")
    public String showPlants(Model model) {
        List<Plant> plants = plantService.findAll();
        List<PlantDto> plantDtos = plants.stream()
                .map(plantMapper::toDto)
                .toList();
        model.addAttribute("plants", plantDtos);
        return "plants"; // nazwa pliku HTML w folderze `resources/templates`
    }
}
