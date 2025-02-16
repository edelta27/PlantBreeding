package com.plantbreeding.infrastructure.dto.response;

import org.springframework.http.HttpStatus;

public record DeletePlantResponseDto(String message, HttpStatus status) {
}
