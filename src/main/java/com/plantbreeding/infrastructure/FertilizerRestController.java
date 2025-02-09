package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.service.FertilizerService;
import com.plantbreeding.infrastructure.dto.request.CreateFertilizerRequestDto;
import com.plantbreeding.infrastructure.dto.response.GetAllFertilizerResponseDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fertilizers")
@Log4j2
public class FertilizerRestController {
    private final FertilizerService fertilizerService;

    public FertilizerRestController(FertilizerService fertilizerService) {
        this.fertilizerService = fertilizerService;
    }

    @GetMapping()
    public ResponseEntity<GetAllFertilizerResponseDto> getAllFertilizer(){
        GetAllFertilizerResponseDto response = fertilizerService.getAllFertilizers();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<String> postFertilizer(@RequestBody @Valid CreateFertilizerRequestDto fertilizer){
        fertilizerService.addFertilizer(fertilizer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fertilizer added successfully");
    }

}
