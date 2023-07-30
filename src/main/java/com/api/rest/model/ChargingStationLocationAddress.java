package com.api.rest.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
public class ChargingStationLocationAddress {

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


}
