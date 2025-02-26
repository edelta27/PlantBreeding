package com.plantbreeding.domain.entity;

import com.plantbreeding.domain.enumeration.ApplicationMethod;
import com.plantbreeding.domain.enumeration.FertilizerType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FertilizerType type;

    @Enumerated(EnumType.STRING)
    private ApplicationMethod applicationMethod;

    private String usageRecommendations;

    @ManyToMany(mappedBy = "fertilizers")
    private List<Plant> plants;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Integer version;

    public Fertilizer() {
    }

    public Fertilizer(String name, FertilizerType type, ApplicationMethod applicationMethod, String usageRecommendations) {
        this.name = name;
        this.type = type;
        this.applicationMethod = applicationMethod;
        this.usageRecommendations = usageRecommendations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FertilizerType getType() {
        return type;
    }

    public void setType(FertilizerType type) {
        this.type = type;
    }

    public ApplicationMethod getApplicationMethod() {
        return applicationMethod;
    }

    public void setApplicationMethod(ApplicationMethod applicationMethod) {
        this.applicationMethod = applicationMethod;
    }

    public String getUsageRecommendations() {
        return usageRecommendations;
    }

    public void setUsageRecommendations(String usageRecommendations) {
        this.usageRecommendations = usageRecommendations;
    }

    public Fertilizer(Long id, String name, FertilizerType type, ApplicationMethod applicationMethod, String usageRecommendations) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.applicationMethod = applicationMethod;
        this.usageRecommendations = usageRecommendations;
    }
}

