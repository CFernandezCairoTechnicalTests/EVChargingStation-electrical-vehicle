package com.api.rest.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Entity
@Table(name = "charging_stations")
public class ChargingStation implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String chargingStationId;

    @Embedded
    private ChargingStationLocation chargingStationLocation;

    @Column(name = "type",nullable = false)
    private String chargingStationType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "charging_station_points", joinColumns = @JoinColumn(name = "chargingStation_id"))
    private Set<ChargingStationPoint> chargingStationPoints = new HashSet<>();

    @Column(name = "size", nullable = false)
    private Integer chargingPointsAmount;

    @Column(name = "status",nullable = false)
    private String chargingStatus;

}
