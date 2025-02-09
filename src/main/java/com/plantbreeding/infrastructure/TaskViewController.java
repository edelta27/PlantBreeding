package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.service.TaskService;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import com.plantbreeding.infrastructure.mapper.TaskMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class TaskViewController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskViewController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
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
