package com.plantbreeding.model;

import com.plantbreeding.config.HealthStatus;
import com.plantbreeding.config.PlantType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlantType type;

    private LocalDate plantingDate;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;  // Enum: HEALTHY, SICK, DEAD

    private Boolean isAnnual;  // true = annual, false = perennial

    private String description;

    private Integer height;

    @OneToMany(mappedBy = "plant")
    private List<Task> tasks;  // Relacja 1:N

    @ManyToMany
    @JoinTable(
            name = "plant_fertilizer",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "fertilizer_id"))
    private List<Fertilizer> fertilizers;  // Relacja M:N

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Integer version;
}

