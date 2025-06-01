package com.plantbreeding.service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.plantbreeding.repository.FertilizerRepository;
import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.domain.enums.ApplicationMethod;
import com.plantbreeding.domain.enums.FertilizerType;
import com.plantbreeding.dto.request.FertilizerDto;
import com.plantbreeding.dto.response.GetAllFertilizerResponseDto;
import com.plantbreeding.mapper.FertilizerMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FertilizerServiceImplTest {

    @Mock
    private FertilizerRepository fertilizerRepository;

    @Mock
    private FertilizerMapper fertilizerMapper;

    @InjectMocks
    private FertilizerServiceImpl fertilizerService;

    @Test
    void shouldReturnAllFertilizers() {
        // given
        Fertilizer fertilizer = new Fertilizer(1L, "BioGrow", FertilizerType.ORGANIC, ApplicationMethod.GRANULATED, "Use weekly");
        List<Fertilizer> fertilizerList = List.of(fertilizer);

        FertilizerDto dto = new FertilizerDto(1L, "BioGrow", FertilizerType.ORGANIC, ApplicationMethod.GRANULATED, "Use weekly");
        List<FertilizerDto> dtoList = List.of(dto);

        when(fertilizerRepository.findAll()).thenReturn(fertilizerList);
        when(fertilizerMapper.toDtoList(fertilizerList)).thenReturn(dtoList);

        // when
        GetAllFertilizerResponseDto response = fertilizerService.getAllFertilizers();

        // then
        assertNotNull(response);
        assertEquals(1, response.fertilizersDto().size());
        assertEquals(dto, response.fertilizersDto().get(0));

        verify(fertilizerRepository, times(1)).findAll();
        verify(fertilizerMapper, times(1)).toDtoList(fertilizerList);
    }

    @Test
    void shouldAddFertilizer() {
        // given
        FertilizerDto dto = new FertilizerDto(null, "GreenPlus", FertilizerType.MINERAL, ApplicationMethod.WATER_SOLUBLE, "Mix with water");
        Fertilizer entity = new Fertilizer("GreenPlus", FertilizerType.MINERAL, ApplicationMethod.WATER_SOLUBLE, "Mix with water");

        when(fertilizerMapper.toEntity(dto)).thenReturn(entity);

        // when
        fertilizerService.addFertilizer(dto);

        // then
        verify(fertilizerMapper, times(1)).toEntity(dto);
        verify(fertilizerRepository, times(1)).save(entity);
    }
}