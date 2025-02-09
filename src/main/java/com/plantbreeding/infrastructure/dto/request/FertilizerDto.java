package com.plantbreeding.infrastructure.dto.request;

import com.plantbreeding.domain.enumeration.ApplicationMethod;
import com.plantbreeding.domain.enumeration.FertilizerType;

public record FertilizerDto(Long id,
                            String name,
                            FertilizerType type,
                            ApplicationMethod applicationMethod,
                            String usageRecommendations
) {
}
