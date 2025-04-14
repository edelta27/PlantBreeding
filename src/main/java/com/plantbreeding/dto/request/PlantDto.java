package com.plantbreeding.dto.request;

import com.plantbreeding.domain.enums.HealthStatus;
import com.plantbreeding.domain.enums.PlantType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PlantDto(Long id,
                                    @NotNull(message = "name must not be null")
                                    @NotEmpty(message = "name must not be empty")
                                    String name,
                                    @NotNull(message = "type must not be null, choose: GREEN_PLANT, VEGETABLE, HERB, LEGUMS, ORNAMENTAL_PLANT")
                                    PlantType plantType,
                                    @NotNull(message = "plantingDate must not be null")
                                    LocalDate plantingDate,
                                    @NotNull(message = "healthStatus must not be null, choose: HEALTHY, SICK, DEAD")
                                    HealthStatus healthStatus,
                                    @NotNull(message = "isAnnual must not be null, choose: true-annual or false-perennial")
                                    Boolean isAnnual,
                                    String description,
                                    Integer height
) {

}
