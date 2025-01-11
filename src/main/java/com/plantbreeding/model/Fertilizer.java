package com.plantbreeding.model;

import com.plantbreeding.config.ApplicationMethod;
import com.plantbreeding.config.FertilizerType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

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
    private List<Plant> plants;  // Relacja M:N

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private Integer version;
}

