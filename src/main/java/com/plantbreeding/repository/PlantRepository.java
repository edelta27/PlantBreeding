package com.plantbreeding.repository;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enums.PlantType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>, JpaSpecificationExecutor<Plant> {
    @Query("SELECT p FROM Plant p WHERE (:isAnnual IS NULL OR p.isAnnual = :isAnnual) AND (:type IS NULL OR p.type = :type)")
    Page<Plant> findFilteredPlants(@Param("isAnnual") Boolean isAnnual,
                                   @Param("type") PlantType type,
                                   Pageable pageable);

}
