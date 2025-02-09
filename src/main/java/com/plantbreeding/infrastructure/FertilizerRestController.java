package com.plantbreeding.infrastructure;


import com.plantbreeding.domain.entity.Fertilizer;
import com.plantbreeding.domain.service.FertilizerService;
import com.plantbreeding.infrastructure.dto.request.CreateFertilizerRequestDto;
import com.plantbreeding.infrastructure.dto.response.GetAllFertilizerResponseDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
public class FertilizerRestController {
    private final FertilizerService fertilizerService;

    public FertilizerRestController(FertilizerService fertilizerService) {
        this.fertilizerService = fertilizerService;
    }

    @GetMapping("/fertilizer")
    public ResponseEntity<GetAllFertilizerResponseDto> getAllFertilizer(){
        List<Fertilizer> allFertilizers;
        allFertilizers = fertilizerService.findAllFerilizer();
        GetAllFertilizerResponseDto response = new GetAllFertilizerResponseDto(allFertilizers);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/fertilizer")
    public ResponseEntity<String> postFertilizer(@RequestBody @Valid CreateFertilizerRequestDto fertilizer){
        fertilizerService.addFertilizer(fertilizer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fertilizer added successfully");
    }


}
