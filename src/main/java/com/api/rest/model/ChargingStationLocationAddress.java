package com.api.rest.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "charging_station_location_address")
public class ChargingStationLocationAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargingLocationAddressId;

    @Column(name = "label",nullable = false)
    private String label;

    @Column(name = "countrycode",nullable = false)
    private String countryCode;

    @Column(name = "countryname",nullable = false)
    private String countryName;

    @Column(name = "statecode",nullable = false)
    private String stateCode;

    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "county",nullable = false)
    private String county;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "district",nullable = false)
    private String district;

    @Column(name = "postalcode",nullable = false)
    private String postalCode;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chargingLocation_id", nullable = false)
    private ChargingStationLocation chargingStationLocation;

}
