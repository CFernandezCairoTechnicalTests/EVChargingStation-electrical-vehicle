package com.api.rest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
public class ChargingStationLocationAddress {

    @Column(name = "label",nullable = false)
    @NonNull
    @NotEmpty
    private String label;

    @Column(name = "countrycode")
    private String countryCode;

    @NonNull
    @NotEmpty
    @Column(name = "countryname",nullable = false)
    private String countryName;

    @Column(name = "statecode")
    private String stateCode;

    @NonNull
    @NotEmpty
    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "county")
    private String county;

    @NonNull
    @NotEmpty
    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "postalcode")
    private String postalCode;


}
