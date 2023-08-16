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
public class ChargingStationLocationPosition implements Serializable {

    @Column(name = "latitude",nullable = false)
    @NonNull
    private float lat;

    @Column(name = "longitude",nullable = false)
    @NonNull
    private float lng;

}
