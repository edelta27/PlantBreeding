package com.plantbreeding.service.impl;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.exception.ResourceNotFoundException;
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

/**
 * Service implementation for managing tasks.
 * Handles business logic for CRUD operations on tasks.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PlantRepository plantRepository;
    private final TaskMapper taskMapper;

    /**
     * Retrieves all tasks from the database.
     *
     * @return a list of tasks
     */
    @Override
    public List<TaskDto> findAll() {
        log.info("retrieving all tasks: ");
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toDtoList(tasks);
    }

    /**
     * Retrieves a list of tasks based on provided filtering criteria.
     *
     * @param taskDate   optional task in date
     * @return a list of tasks matching the specified filters
     */
    @Override
    public List<TaskDto> findTasksByDate(LocalDate taskDate) {
        List<Task> tasks = taskRepository.findByTaskDate(taskDate);
        List<TaskDto> taskDtos = taskMapper.toDtoList(tasks);
        return taskDtos;
    }

    /**
     * Creates a new task associated with a specific plant.
     *
     * @param taskRequestDto the task data to be saved
     * @return the saved tasks DTO
     */
    @Override
    @Transactional
    public List<TaskDto> createTask(CreateTaskRequestDto taskRequestDto) {
        Plant plant = findPlantById(taskRequestDto.plantId());
        List<Task> tasks = generateRecurringTasks(taskRequestDto, plant);
        List<Task> newTasks = taskRepository.saveAll(tasks);
        return taskMapper.toDtoList(newTasks);
    }

    /**
     * Retrieves all tasks for a given plant ID.
     *
     * @param plantId the ID of the plant
     * @return a list of tasks associated with the plant
     */
    @Override
    public List<TaskDto> findTasksByPlantId(Long plantId) {
        plantRepository.findById(plantId)
                .orElseThrow(() -> new ResourceNotFoundException("Plant with id " + plantId + " not found"));

        List<Task> tasks = taskRepository.findByPlantId(plantId);
        List<TaskDto> taskDtos = taskMapper.toDtoList(tasks);
        return taskDtos;
    }

    /**
     * Updates the status of an existing task.
     *
     * @param id the ID of the task to update
     * @param taskStatus the new task status
     * @return the updated task DTO
     */
    @Override
    @Transactional
    public TaskDto updateTasksStatus(Long id, @NonNull TaskStatus taskStatus) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        task.setStatus(taskStatus);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    /**
     * Marks all scheduled tasks as overdue if their planned date is before today.
     *
     * This method is executed automatically every day at midnight (00:00)
     * according to the cron expression ( 0 0 0 * * ? ).
     * It searches for tasks with  TaskStatus#SCHEDULED status
     * and a task date earlier than the current date,
     * then updates their status to TaskStatus#OVERDUE.
     *
     * The operation is transactional – either all affected tasks are updated
     * and saved in the database, or none are in case of failure.
     *
     * Additionally, the method logs the number of tasks that were updated.
     *
     *  TaskRepository#findByStatusAndTaskDateBefore(TaskStatus, LocalDate)
     */
    @Override
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

    private Plant findPlantById(Long plantId) {
        return plantRepository.findById(plantId)
                .orElseThrow(() -> new ResourceNotFoundException("Plant with id " + plantId + " not found"));
    }

    private List<Task> generateRecurringTasks(CreateTaskRequestDto request, Plant plant) {
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

        return tasks;
    }


}
