package com.plantbreeding.infrastructure.mapper;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.infrastructure.dto.request.PlantDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface PlantMapper {

    PlantDto toDto(Plant plant);

    Plant toEntity(PlantDto plantDto);

}

