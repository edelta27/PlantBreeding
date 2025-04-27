package com.plantbreeding.service.impl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enums.Recurrence;
import com.plantbreeding.domain.enums.TaskStatus;
import com.plantbreeding.domain.enums.TaskType;
import com.plantbreeding.exception.TaskNotFoundException;
import com.plantbreeding.dto.request.CreateTaskRequestDto;
import com.plantbreeding.dto.request.TaskDto;
import com.plantbreeding.mapper.TaskMapper;
import com.plantbreeding.repository.PlantRepository;
import com.plantbreeding.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private PlantRepository plantRepository;
    @Mock
    private TaskMapper taskMapper;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void shouldFindAllTasks() {
        // given
        Task task = new Task();
        List<Task> tasks = List.of(task);
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, TaskType.WATERING, "Water daily", LocalDate.now(), TaskStatus.SCHEDULED, 1L));

        given(taskRepository.findAll()).willReturn(tasks);
        given(taskMapper.toDtoList(tasks)).willReturn(taskDtos);

        // when
        List<TaskDto> result = taskService.findAll();

        // then
        assertThat(result).hasSize(1);
        verify(taskRepository).findAll();
    }

    @Test
    void shouldFindTasksByDate() {
        // given
        LocalDate date = LocalDate.now();
        Task task = new Task();
        List<Task> tasks = List.of(task);
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, TaskType.FERTILIZING, "Fertilize", date, TaskStatus.SCHEDULED, 1L));

        given(taskRepository.findByTaskDate(date)).willReturn(tasks);
        given(taskMapper.toDtoList(tasks)).willReturn(taskDtos);

        // when
        List<TaskDto> result = taskService.findTasksByDate(date);

        // then
        assertThat(result).hasSize(1);
        verify(taskRepository).findByTaskDate(date);
    }

    @Test
    void shouldCreateTask() {
        // given
        Long plantId = 1L;
        CreateTaskRequestDto request = new CreateTaskRequestDto(
                plantId,
                TaskType.WATERING,
                "Water plants",
                TaskStatus.SCHEDULED,
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                Recurrence.DAILY
        );
        Plant plant = new Plant();
        given(plantRepository.findById(plantId)).willReturn(Optional.of(plant));

        // when
        taskService.createTask(request);

        // then
        verify(taskRepository).saveAll(anyList());
    }
    @Test
    void shouldFindTasksByPlantId() {
        // given
        Long plantId = 1L;
        Task task = new Task();
        Plant plant = new Plant();
        plant.setId(plantId);
        task.setPlant(plant);
        task.setId(2L);
        task.setTaskType(TaskType.WATERING);
        task.setNotes("Water the plant");
        task.setTaskDate(LocalDate.now());
        task.setStatus(TaskStatus.SCHEDULED);

        given(taskRepository.findByPlantId(plantId)).willReturn(List.of(task));

        // when
        List<TaskDto> result = taskService.findTasksByPlantId(plantId);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).plantId()).isEqualTo(plantId);
    }

    @Test
    void shouldUpdateTaskStatus() {
        // given
        Long taskId = 1L;
        Task task = new Task();
        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        // when
        taskService.updateTasksStatus(taskId, TaskStatus.DONE);

        // then
        assertThat(task.getStatus()).isEqualTo(TaskStatus.DONE);
        verify(taskRepository).save(task);
    }

    @Test
    void shouldThrowWhenTaskNotFoundInUpdate() {
        // given
        Long taskId = 1L;
        given(taskRepository.findById(taskId)).willReturn(Optional.empty());

        // when // then
        org.junit.jupiter.api.Assertions.assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTasksStatus(taskId, TaskStatus.OVERDUE);
        });
    }

    @Test
    void shouldMarkOverdueTasks() {
        // given
        Task task = new Task();
        task.setStatus(TaskStatus.SCHEDULED);
        task.setTaskDate(LocalDate.now().minusDays(1));
        List<Task> overdueTasks = List.of(task);

        given(taskRepository.findByStatusAndTaskDateBefore(eq(TaskStatus.SCHEDULED), any(LocalDate.class)))
                .willReturn(overdueTasks);

        // when
        taskService.markOverdueTasks();

        // then
        assertThat(task.getStatus()).isEqualTo(TaskStatus.OVERDUE);
        verify(taskRepository).saveAll(overdueTasks);
    }
}
