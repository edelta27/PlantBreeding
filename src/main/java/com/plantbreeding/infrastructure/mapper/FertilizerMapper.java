package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.infrastructure.dto.request.FertilizerDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface FertilizerMapper {
    FertilizerDto toDto(Fertilizer fertilizer);
    Fertilizer toEntity(FertilizerDto fertilizerDto);
}

