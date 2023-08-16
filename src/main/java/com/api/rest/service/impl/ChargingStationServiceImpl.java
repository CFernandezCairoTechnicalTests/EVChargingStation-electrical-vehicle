package com.api.rest.service.impl;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.ChargingStation;
import com.api.rest.model.ChargingStatus;
import com.api.rest.repository.ChargingStationRepository;
import com.api.rest.service.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
public class ChargingStationServiceImpl implements ChargingStationService {

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Override
    @Caching(
            cacheable = {
                    @Cacheable(value="chargingstation_available", key="#result.chargingStationId", condition = "#result.chargingStatus==\"AVAILABLE\""),
                    @Cacheable(value="chargingstation", key="#result.chargingStationId")
            }
    )
    public ChargingStation saveChargingStation(ChargingStation chargingStation) {
        Optional<ChargingStation> chargingStationSaved = chargingStationRepository.findById(chargingStation.getChargingStationId());
        if(chargingStationSaved.isPresent()){
            throw new ResourceNotFoundException("The ChargingStation Exists : " + chargingStation.getChargingStationId());
        }
        ChargingStation result = chargingStationRepository.save(chargingStation);
        return result;
    }

    @Override
    @Cacheable("chargingstations")
    public List<ChargingStation> getAllChargingStations() {
        return chargingStationRepository.findAll();
    }

    @Override
    @Cacheable("chargingstations_available")
    public List<ChargingStation> getAllAvailableChargingStations() {
        return chargingStationRepository.findAllBychargingStatus(ChargingStatus.AVAILABLE.getValue());
    }

    @Override
    @Cacheable("chargingstation")
    public Optional<ChargingStation> getChargingStationById(String id) {
        return chargingStationRepository.findById(id);
    }

    @Override
    public Optional<String> getChargingStationStatusById(String id) {
        return chargingStationRepository.findStatusByChargingStationId(id);
    }

    @Override
    @CacheEvict(value = { "chargingstation", "chargingstation_available" }, key = "#chargingStationUpdated.id")
    public ChargingStation updateChargingStation(ChargingStation chargingStationUpdated) {
        return chargingStationRepository.save(chargingStationUpdated);
    }

    @Override
    @CacheEvict(value = { "chargingstation", "chargingstation_available" }, key = "#id")
    public void deleteChargingStationById(String id) {
        chargingStationRepository.deleteById(id);
    }

    @CacheEvict(value = { "tutorial", "tutorials", "published_tutorials" }, allEntries = true)
    public void deleteAll() {
        chargingStationRepository.deleteAll();
    }
}
