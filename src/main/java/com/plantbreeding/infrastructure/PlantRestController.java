package com.plantbreeding.infrastructure;

import com.plantbreeding.domain.entity.Plant;
import com.plantbreeding.domain.enumeration.PlantType;
import com.plantbreeding.domain.service.PlantRetreiver;
import com.plantbreeding.infrastructure.dto.GetAllPlantsResponseDto;
import com.plantbreeding.infrastructure.dto.GetAnnualAndTypePlantsResponseDto;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class PlantRestController {
    //private final PlantAdder plantAdder;
    private final PlantRetreiver plantRetreiver;
    private List<Plant> allPlants;
    private PlantMapper plantMapper;

    public PlantRestController(PlantRetreiver plantRetreiver) {
        this.plantRetreiver = plantRetreiver;
    }

    @GetMapping("/plants/all")
    public ResponseEntity<GetAllPlantsResponseDto> getAllPlants(@RequestParam(required = false) Integer limit){
        allPlants = plantRetreiver.findAll();
        if(limit != null) {
            List<Plant> limitedList = allPlants.stream()
                    .limit(limit)
                    .collect(Collectors.toList());
            GetAllPlantsResponseDto response = new GetAllPlantsResponseDto(limitedList);
            return ResponseEntity.ok(response);
        }
        GetAllPlantsResponseDto response = new GetAllPlantsResponseDto(allPlants);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plants")
    public ResponseEntity<GetAnnualAndTypePlantsResponseDto> getFilteredPlants(
            @RequestParam(required = false) Boolean isAnnual,
            @RequestParam(required = false) PlantType type) {

        List<Plant> plants = plantRetreiver.findAll();
        List<Plant> filteredPlants = plants.stream()
                .filter(plant -> isAnnual == null || plant.getAnnual().equals(isAnnual))
                .filter(plant -> type == null || plant.getType().equals(type))
                .collect(Collectors.toList());
        GetAnnualAndTypePlantsResponseDto response = new GetAnnualAndTypePlantsResponseDto(filteredPlants);
        return ResponseEntity.ok(response);
    }



//    @GetMapping("/plants/{id}")
//    public ResponseEntity<GetSongResponseDto> getSongByID(@PathVariable Integer id, @RequestHeader(required = false) String requestId){
//        log.info(requestId);
//        if(!songRetreiver.findAll().containsKey(id)){
//            throw new SongNotFoundException("Song with id " + id + " not found");
//        }
//        Song song = songRetreiver.findAll().get(id);
//        GetSongResponseDto response = new GetSongResponseDto(song);
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping
//    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request){
//        Song song = SongMapper.mapFromCreateSongRequestDtoToSong(request);
//        songAdder.addSong(song);
//        CreateSongResponseDto body = SongMapper.mapFromSongToCreateSongResponseDto(song);
//        return ResponseEntity.ok(body);
//    }
//
//
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id){
//        if(!songRetreiver.findAll().containsKey(id)){
//            throw new SongNotFoundException("Song with id " + id + " not found");
//        }
//        songRetreiver.findAll().remove(id);
//        return ResponseEntity.ok(new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request){
//        if(!songRetreiver.findAll().containsKey(id)){
//            throw new SongNotFoundException("Song with id " + id + " not found");
//        }
//        String newSongName = request.songName();
//        String newArtist = request.artist();
//        Song newSong = new Song(newSongName, newArtist);
//        Song oldSong = songRetreiver.findAll().put(id, newSong);
//        log.info("Update song with id: " + id +
//                " with oldSongName: " + oldSong.name() + " to newSongName: " + newSong.name() +
//                " oldArtist: " + oldSong.artist() + " to newArtist: " + newSong.artist()
//        );
//        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(), newSong.artist()));
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
//                                                                              @RequestBody PartiallyUpdateSongRequstDto request){
//        if (!songRetreiver.findAll().containsKey(id)){
//            throw new SongNotFoundException("Song with id " + id + " not found");
//        }
//        Song songFromDatabase = songRetreiver.findAll().get(id);
//        Song.SongBuilder builder = Song.builder();
//        if(request.songName() != null){
//            builder.name(request.songName());
//            log.info("partially updated song name");
//        } else {
//            builder.name(songFromDatabase.name());
//        }
//        if(request.artist() != null){
//            builder.artist(request.artist());
//            log.info("partially updated artist");
//        } else {
//            builder.artist(songFromDatabase.artist());
//        }
//        Song updatedSong = builder.build();
//        songRetreiver.findAll().put(id, updatedSong);
//        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong));
//    }
}
