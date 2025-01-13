package com.plantbreeding.dao;

import com.plantbreeding.domain.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Plant, Long> {
}
