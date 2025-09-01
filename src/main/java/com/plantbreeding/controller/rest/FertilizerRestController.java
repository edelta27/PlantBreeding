package com.plantbreeding.controller.rest;

import com.plantbreeding.dto.response.MessageResponseDto;
import com.plantbreeding.service.FertilizerService;
import com.plantbreeding.dto.request.FertilizerDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing fertilizers.
 * Provides endpoints for retrieving and adding.
 */

@RestController
@RequestMapping("/fertilizers")
@Log4j2
public class FertilizerRestController {
    private final FertilizerService fertilizerService;

    public FertilizerRestController(FertilizerService fertilizerService) {
        this.fertilizerService = fertilizerService;
    }

    /**
     * Retrieves all fertilizers.
     *
     * @return a list of all available fertilizers
     */
    @GetMapping()
    public ResponseEntity<List<FertilizerDto>> getAllFertilizers(){
        List<FertilizerDto> response = fertilizerService.getAllFertilizers();
        return ResponseEntity.ok(response);
    }

    /**
     * Adds a new fertilizer.
     *
     * @param fertilizerDto the fertilizer details
     * @return the created fertilizer
     */
    @PostMapping()
    public ResponseEntity<FertilizerDto> postFertilizer(@RequestBody @Valid FertilizerDto fertilizerDto){
        FertilizerDto createdFertilizer = fertilizerService.addFertilizer(fertilizerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFertilizer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteFertilizer(@PathVariable Long id){
        fertilizerService.deleteFertilizer(id);
        return ResponseEntity.ok(new MessageResponseDto("You deleted fertilizer with id: " + id, HttpStatus.OK));
    }

}
