package com.plantbreeding.domain.service;

import com.plantbreeding.dao.PlantRepository;
import com.plantbreeding.dao.TaskRepository;
import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enumeration.Recurrence;
import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.enumeration.TaskType;
import com.plantbreeding.infrastructure.dto.request.CreateTaskRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
//class TaskServiceTest {
//
//    @Mock
//    private TaskRepository taskRepository;
//    @Mock private PlantRepository plantRepository;
//    @InjectMocks
//    private TaskService taskService;
//
//    @Test
//    void shouldCreateTasksForGivenRecurrence() {
//        // GIVEN
//        CreateTaskRequestDto request = new CreateTaskRequestDto(1L, TaskType.WATERING, "Regular watering",
//                LocalDate.of(2025, 2, 16), LocalDate.of(2025, 3, 16), Recurrence.WEEKLY);
//
//        Plant plant = new Plant();
//        when(plantRepository.findById(1L)).thenReturn(Optional.of(plant));
//
//        // WHEN
//        taskService.createTask(request);
//
//        // THEN
//        verify(taskRepository, times(5)).saveAll(anyList());  // 5 tygodni -> 5 zadań
//    }
//
//    @Test
//    void shouldUpdateTaskStatus() {
//        // GIVEN
//        Task task = new Task(TaskType.WATERING, "Test", LocalDate.now(), TaskStatus.SCHEDULED, new Plant());
//        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
//
//        // WHEN
//        taskService.updateTasksStatus(1L, TaskStatus.DONE);
//
//        // THEN
//        assertEquals(TaskStatus.DONE, task.getStatus());
//        verify(taskRepository).save(task);
//    }
//
//    @Test
//    void shouldMarkOverdueTasks() {
//        // GIVEN
//        List<Task> tasks = List.of(
//                new Task(TaskType.WATERING, "Late", LocalDate.now().minusDays(2), TaskStatus.SCHEDULED, new Plant())
//        );
//        when(taskRepository.findByStatusAndTaskDateBefore(TaskStatus.SCHEDULED, LocalDate.now()))
//                .thenReturn(tasks);
//
//        // WHEN
//        taskService.markOverdueTasks();
//
//        // THEN
//        assertEquals(TaskStatus.OVERDUE, tasks.get(0).getStatus());
//        verify(taskRepository).saveAll(tasks);
//    }
//}
