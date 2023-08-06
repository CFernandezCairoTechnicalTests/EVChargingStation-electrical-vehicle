package com.api.rest.repository;

import com.api.rest.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class ChargingStationRepositoryContainerTests {

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    private ChargingStation chargingStation;

    private ChargingStationLocation chargingStationLocation;

    private Set<ChargingStationPoint> chargingStationPoints;

    @BeforeEach
    void setup(){

        /*        chargingStationPoints                */
        chargingStationPoints = new HashSet<>();
        chargingStationPoints.add(ChargingStationPoint.builder()
                .chargingPointId(UUID.randomUUID())
                .powerLevel(20000)
                .status(ChargingStatus.IN_USE.getValue())
                .build()
        );
        chargingStationPoints.add(ChargingStationPoint.builder()
                .chargingPointId(UUID.randomUUID())
                .powerLevel(100000)
                .status(ChargingStatus.AVAILABLE.getValue())
                .build()
        );
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        List<ChargingStationLocation> chargingLocations = new ArrayList<>();
        chargingLocations.add(new ChargingStationLocation());
        try {
            Gson gson = new Gson();
            File dataFile = resourceLoader.getResource("classpath:data/JsonPrueba.json").getFile();

            Type listType = new TypeToken<ArrayList<ChargingStationLocation>>() { }.getType();
            Reader reader1 = new FileReader(dataFile);
            chargingLocations = gson.fromJson(reader1, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        chargingStationLocation = chargingLocations.get(0);
        /*        chargingStationLocation                */

        chargingStation = ChargingStation.builder()
                .chargingStationPoints(chargingStationPoints)
                .chargingPointsAmount(chargingStationPoints.size())
                .chargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue())
                .chargingStationType(ChargingStationType.AC.getValue())
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE.getValue())
                .chargingStationLocation(chargingStationLocation)
                .build();
    }

    @Test
    public void givenChargingStationObject_whenSave_thenReturnSavedChargingStation() {

        // given - setup or precondition
        ChargingStation chargingStationForSave = ChargingStation.builder()
                .chargingStationPoints(chargingStationPoints)
                .chargingPointsAmount(chargingStationPoints.size())
                .chargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue())
                .chargingStationType(ChargingStationType.AC.getValue())
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE.getValue())
                .chargingStationLocation(chargingStationLocation)
                .build();

        // when - action or the testing
        ChargingStation savedChargingStation = chargingStationRepository.save(chargingStationForSave);

        // then - very output
        Assertions.assertNotNull(savedChargingStation);
        Assertions.assertNotNull(savedChargingStation.getChargingStationId());

    }

    @Test
    public void givenChargingStationId_whenFindbyId_thenReturnSavedChargingStation() {

        // given - setup or precondition
        ChargingStation chargingStation = ChargingStation.builder()
                .chargingStationPoints(chargingStationPoints)
                .chargingPointsAmount(chargingStationPoints.size())
                .chargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue())
                .chargingStationType(ChargingStationType.DC.getValue())
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.IN_USE.getValue())
                .chargingStationLocation(chargingStationLocation)
                .build();
        ChargingStation savedChargingStation = chargingStationRepository.save(chargingStation);

        // when - action or the testing
        ChargingStation chargingStationInDB = chargingStationRepository.findById(chargingStation.getChargingStationId()).get();

        // then - very output
        Assertions.assertNotNull(chargingStationInDB);
    }

    @Test
    public void givenChargingStations_whenGetAllChargingStations_thenListOfChargingStations() throws Exception {
        // given - setup or precondition
        List<ChargingStation> chargingStations =
                List.of(ChargingStation.builder()
                                .chargingStationPoints(chargingStationPoints)
                                .chargingPointsAmount(chargingStationPoints.size())
                                .chargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue())
                                .chargingStationType(ChargingStationType.DC.getValue())
                                .chargingPointsAmount(0)
                                .chargingStatus(ChargingStatus.IN_USE.getValue())
                                .chargingStationLocation(chargingStationLocation)
                                .build(),
                        ChargingStation.builder()
                                .chargingStationPoints(chargingStationPoints)
                                .chargingPointsAmount(chargingStationPoints.size())
                                .chargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue())
                                .chargingStationType(ChargingStationType.AC.getValue())
                                .chargingPointsAmount(3)
                                .chargingStatus(ChargingStatus.AVAILABLE.getValue())
                                .chargingStationLocation(chargingStationLocation)
                                .build());
        chargingStationRepository.saveAll(chargingStations);

        // when - action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/chargingstation"));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(chargingStations.size())));
    }

}
