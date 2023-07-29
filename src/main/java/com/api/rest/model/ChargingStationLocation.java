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
@Entity
@Table(name = "charging_station_locations")
public class ChargingStationLocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargingLocationId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chargingStation_id", nullable = false)
    private ChargingStation chargingStation;

    private String id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "language",nullable = false)
    private String language;

    @Column(name = "resulttype",nullable = false)
    private String resultType;

    @Column(name = "distance",nullable = false)
    private float distance;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "chargingStationLocation")
    private ChargingStationLocationAddress address;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "chargingStationLocation")
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
