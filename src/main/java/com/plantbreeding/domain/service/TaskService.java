package com.plantbreeding.domain.service;

import com.plantbreeding.dao.TaskRepository;
import com.plantbreeding.domain.entity.Task;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.util.List;
@Service
@Log4j2
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        log.info("retrieving all tasks: ");
        return taskRepository.findAll();
    }

    public List<Task> findTasksByDate(LocalDate taskDate) {
        return taskRepository.findByTaskDate(taskDate);
    }
}
