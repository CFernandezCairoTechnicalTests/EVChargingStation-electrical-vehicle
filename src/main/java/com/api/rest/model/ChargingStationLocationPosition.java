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
public class ChargingStationLocationPosition {

    @Column(name = "latitude",nullable = false)
    @NonNull
    private float lat;

    @Column(name = "longitude",nullable = false)
    @NonNull
    private float lng;

}
