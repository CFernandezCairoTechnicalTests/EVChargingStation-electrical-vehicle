package com.api.rest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
    private static final long serialVersionUID = 5086484511706646662L;

    @Column(name = "id", nullable = false)
    @NonNull
    @NotEmpty
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "language")
    private String language;

    @Column(name = "resulttype")
    private String resultType;

    @Column(name = "distance")
    private float distance;

    @Embedded
    @Valid
    private ChargingStationLocationAddress address;

    @Embedded
    @Valid
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
