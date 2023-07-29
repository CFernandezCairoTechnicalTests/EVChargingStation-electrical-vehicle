package com.api.rest.controller;

import com.api.rest.model.ChargingStation;
import com.api.rest.service.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chargingstation")
public class ChargingStationController {
    @Autowired
    private ChargingStationService chargingStationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChargingStation saveChargingStation(@RequestBody ChargingStation chargingStation){
        return chargingStationService.saveChargingStation(chargingStation);
    }

    @GetMapping
    public ResponseEntity<List<ChargingStation>> listChargingStations() {
        List<ChargingStation> chargingStation = chargingStationService.getAllChargingStations();
        if (chargingStation.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chargingStation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingStation> getChargingStationById(@PathVariable("id") String chargingStationId) {
        return chargingStationService.getChargingStationById(chargingStationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingStation> updateChargingStation(@PathVariable("id") String chargingStationId,@RequestBody ChargingStation chargingStation){
        return chargingStationService.getChargingStationById(chargingStationId)
                .map(chargingStationSaved -> {
                    chargingStationSaved.setChargingStationType(chargingStation.getChargingStationType());
                    chargingStationSaved.setChargingStatus(chargingStation.getChargingStatus());

                    ChargingStation chargingStationUpdated = chargingStationService.updateChargingStation(chargingStationSaved);
                    return new ResponseEntity<>(chargingStationUpdated,HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChargingStation(@PathVariable("id") String chargingStationId){
        chargingStationService.deleteChargingStation(chargingStationId);
        return new ResponseEntity<String>("CargingStation successfully removed",HttpStatus.OK);
    }
}
