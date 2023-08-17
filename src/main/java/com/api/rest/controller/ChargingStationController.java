package com.api.rest.controller;

import com.api.rest.model.ChargingStation;
import com.api.rest.model.ChargingStatus;
import com.api.rest.service.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chargingstation")
public class ChargingStationController {
    @Autowired
    private ChargingStationService chargingStationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChargingStation saveChargingStation(@Valid @RequestBody ChargingStation chargingStation){
        return chargingStationService.save(chargingStation);
    }

    @GetMapping
    public ResponseEntity<List<ChargingStation>> listChargingStations() {
        List<ChargingStation> chargingStation = chargingStationService.findAll();
        if (chargingStation.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chargingStation);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ChargingStation>> listAvailableChargingStations() {
        List<ChargingStation> chargingStation = chargingStationService.findAllBychargingStatus(ChargingStatus.AVAILABLE.getValue());
        if (chargingStation.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chargingStation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingStation> getChargingStationById(@PathVariable("id") String chargingStationId) {
        return chargingStationService.findById(chargingStationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingStation> updateChargingStation(@PathVariable("id") String chargingStationId, @Valid @RequestBody ChargingStation chargingStation){
        return chargingStationService.findById(chargingStationId)
                .map(chargingStationSaved -> {
                    chargingStationSaved.setChargingStationType(chargingStation.getChargingStationType());
                    chargingStationSaved.setChargingPointsAmount(chargingStation.getChargingPointsAmount());
                    chargingStationSaved.setChargingStatus(chargingStation.getChargingStatus());

                    ChargingStation chargingStationUpdated = chargingStationService.update(chargingStationSaved);
                    return new ResponseEntity<>(chargingStationUpdated,HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChargingStation(@PathVariable("id") String chargingStationId){
        chargingStationService.deleteById(chargingStationId);
        return new ResponseEntity<String>("CargingStation successfully removed",HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            chargingStationService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
