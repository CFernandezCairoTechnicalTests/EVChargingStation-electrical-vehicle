package com.api.rest.service.impl;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.ChargingStation;
import com.api.rest.model.ChargingStatus;
import com.api.rest.repository.ChargingStationRepository;
import com.api.rest.service.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChargingStationServiceImpl implements ChargingStationService {

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Override
    public ChargingStation saveChargingStation(ChargingStation chargingStation) {
        Optional<ChargingStation> chargingStationSaved = chargingStationRepository.findById(chargingStation.getChargingStationId());
        if(chargingStationSaved.isPresent()){
            throw new ResourceNotFoundException("El empleado con ese email ya existe : " + chargingStation.getChargingStationId());
        }
        return chargingStationRepository.save(chargingStation);
    }

    @Override
    public List<ChargingStation> getAllChargingStations() {
        return chargingStationRepository.findAll();
    }

    @Override
    public List<ChargingStation> getAllAvailableChargingStations() {
        return chargingStationRepository.findAllBychargingStatus(ChargingStatus.AVAILABLE.getValue());
    }

    @Override
    public Optional<ChargingStation> getChargingStationById(String id) {
        return chargingStationRepository.findById(id);
    }

    @Override
    public ChargingStation updateChargingStation(ChargingStation chargingStationUpdated) {
        return chargingStationRepository.save(chargingStationUpdated);
    }

    @Override
    public void deleteChargingStation(String id) {
        chargingStationRepository.deleteById(id);
    }
}
