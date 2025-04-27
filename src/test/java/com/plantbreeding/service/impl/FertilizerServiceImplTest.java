package com.plantbreeding.service.impl;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.plantbreeding.repository.FertilizerRepository;
import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.domain.enums.ApplicationMethod;
import com.plantbreeding.domain.enums.FertilizerType;
import com.plantbreeding.dto.request.FertilizerDto;
import com.plantbreeding.dto.response.GetAllFertilizerResponseDto;
import com.plantbreeding.mapper.FertilizerMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FertilizerServiceImplTest {

    @Mock
    private FertilizerRepository fertilizerRepository;

    @Mock
    private FertilizerMapper fertilizerMapper;

    @InjectMocks
    private FertilizerServiceImpl fertilizerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllFertilizers() {
        // given
        Fertilizer fertilizer = new Fertilizer();
        List<Fertilizer> fertilizers = List.of(fertilizer);

        FertilizerDto fertilizerDto = new FertilizerDto(1L, "BioFertilizer", FertilizerType.ORGANIC, ApplicationMethod.GRANULATED, "Natural fertilizer");
        List<FertilizerDto> fertilizerDtos = List.of(fertilizerDto);

        given(fertilizerRepository.findAll()).willReturn(fertilizers);
        given(fertilizerMapper.toDtoList(fertilizers)).willReturn(fertilizerDtos);

        // when
        GetAllFertilizerResponseDto response = fertilizerService.getAllFertilizers();

        // then
        assertThat(response).isNotNull();
        assertThat(response.fertilizersDto()).hasSize(1);
        assertThat(response.fertilizersDto().get(0).name()).isEqualTo("BioFertilizer");
    }

    @Test
    void shouldAddFertilizer() {
        // given
        FertilizerDto fertilizerDto = new FertilizerDto(1L, "OrganicGrow", FertilizerType.ORGANIC, ApplicationMethod.GRANULATED, "Best fertilizer");
        Fertilizer fertilizer = new Fertilizer();

        given(fertilizerMapper.toEntity(fertilizerDto)).willReturn(fertilizer);

        // when
        fertilizerService.addFertilizer(fertilizerDto);

        // then
        verify(fertilizerMapper).toEntity(fertilizerDto);
        verify(fertilizerRepository).save(fertilizer);
    }
}