package com.plantbreeding.domain.service;

import com.plantbreeding.dao.PlantRepository;
import com.plantbreeding.dao.TaskRepository;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.errors.PlantNotFoundException;
import com.plantbreeding.infrastructure.dto.request.CreateTaskRequestDto;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.plantbreeding.domain.enumeration.Recurrence.*;

@Service
@Log4j2
public class TaskService {
    private final TaskRepository taskRepository;
    private final PlantRepository plantRepository;

    public TaskService(TaskRepository taskRepository, PlantRepository plantRepository) {
        this.taskRepository = taskRepository;
        this.plantRepository = plantRepository;
    }

    public List<Task> findAll() {
        log.info("retrieving all tasks: ");
        return taskRepository.findAll();
    }

    public List<Task> findTasksByDate(LocalDate taskDate) {
        return taskRepository.findByTaskDate(taskDate);
    }

    @Transactional
    public void createTask(CreateTaskRequestDto request) {
        Plant plant = plantRepository.findById(request.plantId())
                .orElseThrow(() -> new PlantNotFoundException("Plant with id " + request.plantId() + " not found"));

        LocalDate currentDate = request.startDate();
        List<Task> tasks = new ArrayList<>();

        while (!currentDate.isAfter(request.endDate())) {
            Task task = new Task();
            task.setPlant(plant);
            task.setTaskType(request.taskType());
            task.setTaskDate(currentDate);
            task.setStatus(TaskStatus.SCHEDULED);

            String note = "Recurring task scheduled on " + LocalDate.now();
            task.setNotes(request.notes() != null ? request.notes() + " | " + note : note);
            tasks.add(task);

            switch (request.recurrence()) {
                case DAILY -> currentDate = currentDate.plusDays(1);
                case WEEKLY -> currentDate = currentDate.plusWeeks(1);
                case MONTHLY -> currentDate = currentDate.plusMonths(1);
            }
        }

        taskRepository.saveAll(tasks);
    }

    public List<TaskDto> findTasksByPlantId(Long plantId) {
        return taskRepository.findByPlantId(plantId)
                .stream()
                .map(task -> new TaskDto(
                        task.getId(),
                        task.getTaskType(),
                        task.getNotes(),
                        task.getTaskDate(),
                        task.getStatus(),
                        task.getPlant().getId()
                ))
                .toList();
    }
}
