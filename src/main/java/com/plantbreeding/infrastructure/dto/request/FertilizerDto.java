package com.plantbreeding.infrastructure.dto.request;

import com.plantbreeding.domain.enumeration.ApplicationMethod;
import com.plantbreeding.domain.enumeration.FertilizerType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FertilizerDto(Long id,
                            @NotNull(message = "name must not be null")
                            @NotEmpty(message = "name must not be empty")
                            String name,
                            @NotNull(message = "type must not be null, choose: ORGANIC, MINERAL")
                            FertilizerType type,
                            @NotNull(message = "applicationMethod must not be null, choose: WATER_SOLUBLE, GRANULATED")
                            ApplicationMethod applicationMethod,
                            String usageRecommendations
) {
}
