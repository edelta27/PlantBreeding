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

@Service
@Log4j2
@RequiredArgsConstructor
public class FertilizerServiceImpl implements FertilizerService {
    private final FertilizerRepository fertilizerRepository;
    private final FertilizerMapper fertilizerMapper;

    @Override
    public List<FertilizerDto> getAllFertilizers() {
        List<Fertilizer> fertilizers = fertilizerRepository.findAll();
        return fertilizerMapper.toDtoList(fertilizers);
    }

    @Override
    @Transactional
    public void addFertilizer(FertilizerDto fertilizerDto) {
        log.info("save fertilizer: ");
        Fertilizer fertilizer = fertilizerMapper.toEntity(fertilizerDto);
        fertilizerRepository.save(fertilizer);
    }
}
