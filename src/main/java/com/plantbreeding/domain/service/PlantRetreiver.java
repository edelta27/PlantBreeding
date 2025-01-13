package com.plantbreeding.domain.service;

import com.plantbreeding.dao.PlantRepository;
import com.plantbreeding.domain.entity.Plant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class PlantRetreiver {
    private final PlantRepository plantRepository;

    @Autowired
    PlantRetreiver(PlantRepository plantRepository){
        this.plantRepository = plantRepository;
    }
    public List<Plant> findAll() {
        log.info("retrieving all songs: ");
        return plantRepository.findAll();
    }
}
