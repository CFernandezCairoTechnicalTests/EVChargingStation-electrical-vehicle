package com.api.rest.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "charging_station_location_positions")
public class ChargingStationLocationPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargingLocationPositionId;

    @Column(name = "latitude",nullable = false)
    private float lat;

    @Column(name = "longitude",nullable = false)
    private float lng;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chargingLocation_id", nullable = false)
    private ChargingStationLocation chargingStationLocation;

}
