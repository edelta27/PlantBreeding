package com.plantbreeding.service;

import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
import com.plantbreeding.dto.request.TaskDto;
import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    List<TaskDto> findAll();
    List<TaskDto> findTasksByDate(LocalDate taskDate);
    void createTask(CreateTaskRequestDto request);
    List<TaskDto> findTasksByPlantId(Long plantId);
    void updateTasksStatus(Long id, TaskStatus taskStatus);
    void markOverdueTasks();
}
