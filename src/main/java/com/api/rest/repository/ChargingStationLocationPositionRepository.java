package com.api.rest.repository;

import com.api.rest.model.ChargingStationLocationPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface ChargingStationLocationPositionRepository extends JpaRepository<ChargingStationLocationPosition, Long> {

}
