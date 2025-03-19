package com.plantbreeding.mapper;

import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.dto.request.FertilizerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FertilizerMapper {
    FertilizerDto toDto(Fertilizer fertilizer);
    List<FertilizerDto> toDtoList(List<Fertilizer> fertilizers);
    Fertilizer toEntity(FertilizerDto fertilizerDto);
    List<Fertilizer> toEntityList(List<FertilizerDto> fertilizersDto);


}

