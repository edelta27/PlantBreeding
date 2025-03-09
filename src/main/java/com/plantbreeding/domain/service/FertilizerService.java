package com.plantbreeding.domain.service;

import com.plantbreeding.dao.FertilizerRepository;
import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.infrastructure.dto.request.FertilizerDto;
import com.plantbreeding.infrastructure.dto.response.GetAllFertilizerResponseDto;
import com.plantbreeding.infrastructure.mapper.FertilizerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class FertilizerService {
    private final FertilizerRepository fertilizerRepository;
    private final FertilizerMapper fertilizerMapper;

    public GetAllFertilizerResponseDto getAllFertilizers() {
        List<Fertilizer> fertilizers = fertilizerRepository.findAll();
        List<FertilizerDto> fertilizersDtos = fertilizerMapper.toDtoList(fertilizers);
        return new GetAllFertilizerResponseDto(fertilizersDtos);
    }
    @Transactional
    public void addFertilizer(FertilizerDto fertilizerDto) {
        log.info("save fertilizer: ");
        Fertilizer fertilizer = fertilizerMapper.toEntity(fertilizerDto);
        fertilizerRepository.save(fertilizer);
    }
}
