package com.plantbreeding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.plantbreeding")
@EnableScheduling
public class PlantBreedingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlantBreedingApplication.class, args);
    }

}
