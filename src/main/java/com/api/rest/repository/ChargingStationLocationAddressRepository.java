package com.api.rest.repository;

import com.api.rest.model.ChargingStationLocationAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Repository
public interface ChargingStationLocationAddressRepository extends JpaRepository<ChargingStationLocationAddress, Long> {

}
