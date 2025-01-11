package com.plantbreeding.model;

import com.plantbreeding.config.TaskStatus;
import com.plantbreeding.config.TaskType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

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
    @JoinColumn(name = "plant_id")
    private Plant plant;  // Relacja M:1

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Integer version;
}

