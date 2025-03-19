package com.plantbreeding.repository;

import com.plantbreeding.domain.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {

}
