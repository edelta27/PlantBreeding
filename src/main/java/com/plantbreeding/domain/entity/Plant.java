package com.plantbreeding.domain.entity;

import com.plantbreeding.domain.enumeration.HealthStatus;
import com.plantbreeding.domain.enumeration.PlantType;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlantType type;

    private LocalDate plantingDate;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    private Boolean isAnnual;  // true = annual, false = perennial

    private String description;

    private Integer height;

    @OneToMany(mappedBy = "plant")
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(
            name = "plant_fertilizer",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "fertilizer_id"))
    private List<Fertilizer> fertilizers;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Plant() {
    }

    public Plant(String name, PlantType type, LocalDate plantingDate, HealthStatus healthStatus, Boolean isAnnual, String description, Integer height) {
        this.name = name;
        this.type = type;
        this.plantingDate = plantingDate;
        this.healthStatus = healthStatus;
        this.isAnnual = isAnnual;
        this.description = description;
        this.height = height;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Plant(Long id, String name, PlantType type, LocalDate plantingDate, HealthStatus healthStatus, Boolean isAnnual, String description, Integer height,
                 List<Task> tasks, List<Fertilizer> fertilizers, LocalDateTime createdAt, LocalDateTime updatedAt, Integer version) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.plantingDate = plantingDate;
        this.healthStatus = healthStatus;
        this.isAnnual = isAnnual;
        this.description = description;
        this.height = height;
        this.tasks = tasks;
        this.fertilizers = fertilizers;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlantType getType() {
        return type;
    }

    public void setType(PlantType type) {
        this.type = type;
    }

    public LocalDate getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(LocalDate plantingDate) {
        this.plantingDate = plantingDate;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Boolean getAnnual() {
        return isAnnual;
    }

    public void setAnnual(Boolean annual) {
        isAnnual = annual;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

