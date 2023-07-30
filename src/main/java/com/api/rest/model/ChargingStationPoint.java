package com.api.rest.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
@Table(name = "charging_station_points")
public class ChargingStationPoint {

    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    //@Column(name = "charging_point_id",nullable = false)
    private UUID chargingPointId;

    @Column(name = "powerlevel",nullable = false)
    private Integer powerLevel;

    @Column(name = "status",nullable = false)
    private String status;

}
