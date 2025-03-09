package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.service.TaskService;
import com.plantbreeding.infrastructure.dto.request.TaskDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class TaskViewController {
    private final TaskService taskService;
    public TaskViewController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String showTasks(Model model) {
        List<TaskDto> tasks = taskService.findAll();
        List<TaskDto> taskDtos = tasks.stream()
                .toList();
        model.addAttribute("tasks", taskDtos);
        return "tasks"; // nazwa pliku HTML w folderze `resources/templates`
    }


}
