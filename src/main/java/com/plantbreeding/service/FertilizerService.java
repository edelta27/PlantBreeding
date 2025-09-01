package com.plantbreeding.service;

import com.plantbreeding.dto.request.FertilizerDto;

import java.util.List;

public interface FertilizerService {
    List<FertilizerDto> getAllFertilizers();
    FertilizerDto addFertilizer(FertilizerDto fertilizerDto);

    void deleteFertilizer(Long id);
}
