package com.plantbreeding.domain.entity;

import com.plantbreeding.domain.enumeration.TaskStatus;
import com.plantbreeding.domain.enumeration.TaskType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private String notes;

    private LocalDate taskDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Integer version;



    public Task(TaskType taskType, String notes, LocalDate taskDate, TaskStatus status, Plant plant) {
        this.taskType = taskType;
        this.notes = notes;
        this.taskDate = taskDate;
        this.status = status;
        this.plant = plant;
    }

    public Task(Long id, TaskType taskType, String notes, LocalDate taskDate, TaskStatus status, Long plantId) {
        this.id = id;
        this.taskType = taskType;
        this.notes = notes;
        this.taskDate = taskDate;
        this.status = status;
    }

    public Task() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public Plant getPlant() { return plant; }

    public void setPlant(Plant plant) { this.plant = plant; }

}

