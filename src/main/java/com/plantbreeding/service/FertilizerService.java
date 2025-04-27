package com.plantbreeding.service;

import com.plantbreeding.dto.request.FertilizerDto;
import com.plantbreeding.dto.response.GetAllFertilizerResponseDto;

public interface FertilizerService {
    GetAllFertilizerResponseDto getAllFertilizers();
    void addFertilizer(FertilizerDto fertilizerDto);
}
