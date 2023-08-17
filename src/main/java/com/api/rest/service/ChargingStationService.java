package com.api.rest.service;

import com.api.rest.model.ChargingStation;

import java.util.List;
import java.util.Optional;

public interface ChargingStationService {

    ChargingStation save(ChargingStation chargingStation);

    List<ChargingStation> findAll();

    List<ChargingStation> findAllBychargingStatus(String status);

    Optional<ChargingStation> findById(String id);

    ChargingStation update(ChargingStation chargingStationUpdated);

    void deleteById(String id);

    public void deleteAll();

}
