package com.api.rest.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Created by rajeevkumarsingh on 20/11/17.
 */
@Embeddable
public class ChargingStationLocation implements Serializable {

    private String id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "language",nullable = false)
    private String language;

    @Column(name = "resulttype",nullable = false)
    private String resultType;

    @Column(name = "distance",nullable = false)
    private float distance;

    @Embedded
    private ChargingStationLocationAddress address;

    @Embedded
    ChargingStationLocationPosition position;

    /*@ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "charging_station_location_access", joinColumns = @JoinColumn(name = "chargingLocation_id"))
    private Set<ChargingStationPoint> access = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "charging_station_location_categories", joinColumns = @JoinColumn(name = "chargingLocation_id"))
    private Set<ChargingStationPoint> categories = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "charging_station_location_references", joinColumns = @JoinColumn(name = "chargingLocation_id"))
    private Set<ChargingStationPoint> references = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "charging_station_location_contacts", joinColumns = @JoinColumn(name = "chargingLocation_id"))
    private Set<ChargingStationPoint> contacts = new HashSet<>();*/

}
