package com.plantbreeding.domain.errors;

public class FertilizerNotFoundException extends RuntimeException {
    public FertilizerNotFoundException(String message) {
        super(message);
    }
}
