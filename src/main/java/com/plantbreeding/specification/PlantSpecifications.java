package com.plantbreeding.specification;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.PlantType;
import org.springframework.data.jpa.domain.Specification;

public class PlantSpecifications {
    public static Specification<Plant> hasType(PlantType type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<Plant> hasIsAnnual(Boolean isAnnual) {
        return (root, query, cb) -> isAnnual == null ? null : cb.equal(root.get("isAnnual"), isAnnual);
    }
}
