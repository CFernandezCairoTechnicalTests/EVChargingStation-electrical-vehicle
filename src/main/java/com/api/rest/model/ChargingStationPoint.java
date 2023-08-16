package com.api.rest.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Embeddable
@Table(name = "charging_station_points")
public class ChargingStationPoint implements Serializable {
    private static final long serialVersionUID = -7989783710948601186L;

    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "charging_point_id",nullable = false)
    @NonNull
    private UUID chargingPointId;

    @Column(name = "powerlevel",nullable = false)
    @NonNull
    private Integer powerLevel;

    @Column(name = "status",nullable = false)
    @NonNull
    @NotEmpty
    private String status;

}
