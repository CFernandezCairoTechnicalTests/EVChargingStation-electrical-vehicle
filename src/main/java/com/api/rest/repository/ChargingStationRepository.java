package com.api.rest.repository;

import com.api.rest.model.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by cfernandezcairo on 20/07/23.
 */
@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, String> {
    List<ChargingStation> findAllBychargingStatus(String status);

    Optional<String> findStatusByChargingStationId(String id);

}