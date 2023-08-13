package com.api.rest.repository;

import com.api.rest.model.ChargingStation;
import com.api.rest.model.ChargingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, String> {
    List<ChargingStation> findAllBychargingStatus(String status);

}