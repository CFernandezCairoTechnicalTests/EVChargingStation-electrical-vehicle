package com.api.rest.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
public class ChargingStationLocationPosition {

    @Column(name = "latitude",nullable = false)
    private float lat;

    @Column(name = "longitude",nullable = false)
    private float lng;

}
