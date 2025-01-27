package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.service.PlantRetreiver;
import com.plantbreeding.domain.service.TaskService;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import com.plantbreeding.infrastructure.mapper.PlantMapper;
import com.plantbreeding.infrastructure.mapper.TaskMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class PlantViewController {
    private final PlantRetreiver plantService;
    private final PlantMapper plantMapper;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public PlantViewController(PlantRetreiver plantService, PlantMapper plantMapper, TaskService taskService, TaskMapper taskMapper) {
        this.plantService = plantService;
        this.plantMapper = plantMapper;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
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
    @GetMapping("/tasks")
    public String showTasks(Model model) {
        List<Task> tasks = taskService.findAll();
        List<TaskDto> taskDtos = tasks.stream()
                .map(taskMapper::toDto)
                .toList();
        model.addAttribute("tasks", taskDtos);
        return "tasks"; // nazwa pliku HTML w folderze `resources/templates`
    }


}
