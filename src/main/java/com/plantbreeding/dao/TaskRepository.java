package com.plantbreeding.dao;

import com.plantbreeding.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTaskDate(LocalDate taskDate);

    void deleteByPlantId(Long plantId);
}
