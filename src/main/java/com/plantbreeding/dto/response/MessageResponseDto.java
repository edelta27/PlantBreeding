package com.plantbreeding.dto.response;

import org.springframework.http.HttpStatus;

public record MessageResponseDto(String message, HttpStatus status) {
}
