package com.plantbreeding.service.impl;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.exception.PlantNotFoundException;
import com.plantbreeding.exception.TaskNotFoundException;
import com.plantbreeding.mapper.TaskMapper;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.repository.TaskRepository;
import com.plantbreeding.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PlantRepository plantRepository;
    private final TaskMapper taskMapper;

    public List<TaskDto> findAll() {
        log.info("retrieving all tasks: ");
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toDtoList(tasks);
    }

    public List<TaskDto> findTasksByDate(LocalDate taskDate) {
        List<Task> tasks = taskRepository.findByTaskDate(taskDate);
        List<TaskDto> taskDtos = taskMapper.toDtoList(tasks);
        return taskDtos;
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
        List<Task> tasks = taskRepository.findByPlantId(plantId);
        List<TaskDto> taskDtos = taskMapper.toDtoList(tasks);
        return taskDtos;
    }

    @Transactional
    public void updateTasksStatus(Long id, @NonNull TaskStatus taskStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        task.setStatus(taskStatus);
        taskRepository.save(task);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void markOverdueTasks() {
        LocalDate today = LocalDate.now();
        List<Task> overdueTasks = taskRepository.findByStatusAndTaskDateBefore(TaskStatus.SCHEDULED, today);

        if (!overdueTasks.isEmpty()) {
            overdueTasks.forEach(task -> task.setStatus(TaskStatus.OVERDUE));
            taskRepository.saveAll(overdueTasks);
            log.info("Updated {} tasks on OVERDUE", overdueTasks.size());
        }
    }
}
