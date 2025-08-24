package com.plantbreeding.controller.rest;

import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.service.TaskService;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.dto.response.MessageResponseDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing tasks.
 * Provides endpoints for retrieving, adding and updating.
 */
@RestController
@Log4j2
@RequestMapping("/tasks")
public class TaskRestController {
    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Retrieves the list of tasks scheduled for today.
     *
     * <p>This endpoint is intended to help users quickly access
     * their daily planned activities related to plants.</p>
     *
     * @return a {@link ResponseEntity} containing a list of {@link TaskDto} objects
     *         scheduled for the current date
     */
    @GetMapping("/daily")
    public ResponseEntity<List<TaskDto>> getTasksForDate(@RequestParam("date") LocalDate taskDate) {
        List<TaskDto> tasksDtos = taskService.findTasksByDate(taskDate);
        return ResponseEntity.ok(tasksDtos);
    }

    /**
     * Adds a new task to the system.
     *
     * <p>The task is associated with a plant and contains
     * information such as description, date, and status.</p>
     *
     * @param request the data of the new task, provided as a {@link TaskDto}
     * @return a {@link ResponseEntity} containing the saved {@link TaskDto}
     */
    @PostMapping()
    public ResponseEntity<MessageResponseDto> addTask(@RequestBody @Valid CreateTaskRequestDto request) {
        taskService.createTask(request);
        return ResponseEntity.ok(new MessageResponseDto("Task(s) added successfully", HttpStatus.OK));
    }

    /**
     * Updates an existing task.
     *
     * <p>The method allows partial updates (PATCH), such as
     * changing the status or description of the task.</p>
     *
     * @param id         the unique identifier of the task to be updated
     * @param taskStatus the updated task status
     * @return a {@link ResponseEntity} containing the updated {@link TaskDto}
     */
    @PatchMapping ("/{id}")
    public ResponseEntity<MessageResponseDto> updateTask(@PathVariable Long id,
                                              @RequestParam TaskStatus taskStatus) {
        taskService.updateTasksStatus(id, taskStatus);
        return ResponseEntity.ok(new MessageResponseDto("Task updated successfully", HttpStatus.OK));
    }

}
