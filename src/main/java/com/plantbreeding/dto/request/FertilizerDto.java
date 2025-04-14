package com.plantbreeding.dto.request;

import com.plantbreeding.domain.enums.ApplicationMethod;
import com.plantbreeding.domain.enums.FertilizerType;
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
