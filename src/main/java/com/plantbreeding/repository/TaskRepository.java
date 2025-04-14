package com.plantbreeding.repository;

import com.plantbreeding.domain.entity.Task;
import com.plantbreeding.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTaskDate(LocalDate taskDate);
    List<Task> findByPlantId(Long plantId);
    List<Task> findByStatusAndTaskDateBefore(TaskStatus status, LocalDate date);
    void deleteByPlantId(Long plantId);
}
