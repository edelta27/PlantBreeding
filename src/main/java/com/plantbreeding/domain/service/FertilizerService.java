package com.plantbreeding.domain.service;

import com.plantbreeding.dao.FertilizerRepository;
import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.infrastructure.dto.request.CreateFertilizerRequestDto;
import com.plantbreeding.infrastructure.dto.response.GetAllFertilizerResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class FertilizerService {
    private final FertilizerRepository fertilizerRepository;

    public FertilizerService(FertilizerRepository fertilizerRepository) {
        this.fertilizerRepository = fertilizerRepository;
    }

    public GetAllFertilizerResponseDto getAllFertilizers() {
        List<Fertilizer> fertilizers = fertilizerRepository.findAll();
        return new GetAllFertilizerResponseDto(fertilizers);
    }

    public void addFertilizer(CreateFertilizerRequestDto fertilizerDto) {
        log.info("save fertilizer: ");
        Fertilizer fertilizer = new Fertilizer(
                fertilizerDto.name(),
                fertilizerDto.type(),
                fertilizerDto.applicationMethod(),
                fertilizerDto.usageRecommendations()
        );
        fertilizerRepository.save(fertilizer);
    }
}
