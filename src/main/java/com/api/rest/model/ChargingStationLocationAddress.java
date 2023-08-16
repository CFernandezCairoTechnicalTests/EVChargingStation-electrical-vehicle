package com.api.rest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
public class ChargingStationLocationAddress implements Serializable {
    private static final long serialVersionUID = 7715517219286093931L;

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
