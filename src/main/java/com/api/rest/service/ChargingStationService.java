package com.api.rest.service;

import com.api.rest.model.ChargingStation;

import java.util.List;
import java.util.Optional;

public interface ChargingStationService {

    ChargingStation saveChargingStation(ChargingStation chargingStation);

    List<ChargingStation> getAllChargingStations();

    List<ChargingStation> getAllAvailableChargingStations();

    Optional<ChargingStation> getChargingStationById(String id);

    Optional<String> getChargingStationStatusById(String id);

    ChargingStation updateChargingStation(ChargingStation chargingStationUpdated);

    void deleteChargingStationById(String id);

    public void deleteAll();

}
