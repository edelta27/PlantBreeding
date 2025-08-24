package com.plantbreeding.service.impl;

import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.dto.request.FertilizerDto;
import com.plantbreeding.mapper.FertilizerMapper;
import com.plantbreeding.repository.FertilizerRepository;
import com.plantbreeding.service.FertilizerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing fertilizers.
 * Handles business logic for CRUD operations on fertilizers.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class FertilizerServiceImpl implements FertilizerService {
    private final FertilizerRepository fertilizerRepository;
    private final FertilizerMapper fertilizerMapper;

    /**
     * Retrieves all fertilizers from the database.
     *
     * @return a list of fertilizers
     */
    @Override
    public List<FertilizerDto> getAllFertilizers() {
        List<Fertilizer> fertilizers = fertilizerRepository.findAll();
        return fertilizerMapper.toDtoList(fertilizers);
    }
    /**
     * Adds a new fertilizer to the database.
     *
     * @param fertilizerDto the fertilizer entity to add
     * @return the saved fertilizer entity
     */
    @Override
    @Transactional
    public void addFertilizer(FertilizerDto fertilizerDto) {
        log.info("save fertilizer: ");
        Fertilizer fertilizer = fertilizerMapper.toEntity(fertilizerDto);
        fertilizerRepository.save(fertilizer);
    }
}
