package com.api.rest.repository;

import com.api.rest.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChargingStationRepositoryTests {

    @Autowired
    private ChargingStationRepository chargingStationRepository;

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
                .status(ChargingStatus.IN_USE)
                .build()
        );
        chargingStationPoints.add(ChargingStationPoint.builder()
                .chargingPointId(UUID.randomUUID())
                .powerLevel(100000)
                .status(ChargingStatus.AVAILABLE)
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
        chargingStationLocation.getAddress().setChargingStationLocation(chargingStationLocation);
        chargingStationLocation.getPosition().setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */


        chargingStation = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();

        /*        chargingStationPoints                */
        chargingStation.setChargingStationPoints(chargingStationPoints);
        chargingStation.setChargingPointsAmount(chargingStationPoints.size());
        chargingStation.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE : ChargingStatus.UNAVAILABLE);
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStation);
        chargingStation.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */
    }

    @DisplayName("Save ChargingStation")
    @Test
    void testSaveChargingStation(){

        //given - dado o condición previa o configuración
        ChargingStation chargingStationForSave = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();

        /*        chargingStationPoints                */
        chargingStationForSave.setChargingStationPoints(chargingStationPoints);
        chargingStationForSave.setChargingPointsAmount(chargingStationPoints.size());
        chargingStationForSave.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE : ChargingStatus.UNAVAILABLE);
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStationForSave);
        chargingStationForSave.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */

        //when - acción o el comportamiento que vamos a probar
        ChargingStation savedChargingStation = chargingStationRepository.save(chargingStationForSave);

        //then - verificar la salida
        assertThat(savedChargingStation).isNotNull();
        assertThat(savedChargingStation.getChargingStationId()).isNotNull();

    }

    @DisplayName("Get ChargingStation by ID")
    @Test
    void testGetChargingStationByID(){
        chargingStationRepository.save(chargingStation);

        //when - comportamiento o accion que vamos a probar
        ChargingStation chargingStationDB = chargingStationRepository.findById(chargingStation.getChargingStationId()).get();

        //then
        assertThat(chargingStationDB).isNotNull();
    }

    @DisplayName("Update ChargingStation")
    @Test
    void testForUpdateChargingStation(){
        //given - dado o condición previa o configuración
        ChargingStation chargingStationForUpdate = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();

        /*        chargingStationPoints                */
        chargingStationForUpdate.setChargingStationPoints(chargingStationPoints);
        chargingStationForUpdate.setChargingPointsAmount(chargingStationPoints.size());
        chargingStationForUpdate.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE : ChargingStatus.UNAVAILABLE);
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStationForUpdate);
        chargingStationForUpdate.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */
        chargingStationRepository.save(chargingStationForUpdate);

        //when
        ChargingStation chargingStationUpdated = chargingStationRepository.findById(chargingStationForUpdate.getChargingStationId()).get();
        chargingStationUpdated.setChargingStationType(ChargingStationType.DC);
        chargingStationUpdated.setChargingStatus(ChargingStatus.IN_USE);
        ChargingStation chargingStationResult = chargingStationRepository.save(chargingStationUpdated);

        //then
        assertThat(chargingStationResult.getChargingStationType()).isEqualTo(ChargingStationType.DC);
        assertThat(chargingStationResult.getChargingStatus()).isEqualTo(ChargingStatus.IN_USE);
    }

    @DisplayName("Get all ChargingStations")
    @Test
    void testListChargingStations(){
        //given
        ChargingStation chargingStationForList = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();

        /*        chargingStationPoints                */
        chargingStationForList.setChargingStationPoints(chargingStationPoints);
        chargingStationForList.setChargingPointsAmount(chargingStationPoints.size());
        chargingStationForList.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE : ChargingStatus.UNAVAILABLE);
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStationForList);
        chargingStationForList.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */
        chargingStationRepository.save(chargingStationForList);
        chargingStationRepository.save(chargingStation);

        //when
        List<ChargingStation> chargingStationList = chargingStationRepository.findAll();

        //then
        assertThat(chargingStationList).isNotNull();
        assertThat(chargingStationList.size()).isEqualTo(2);
    }

    @DisplayName("Remove ChargingStation by ID")
    @Test
    void testRemoveChargingStationByID(){
        //given - dado o condición previa o configuración
        ChargingStation chargingStationForRemove = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();

        /*        chargingStationPoints                */
        chargingStationForRemove.setChargingStationPoints(chargingStationPoints);
        chargingStationForRemove.setChargingPointsAmount(chargingStationPoints.size());
        chargingStationForRemove.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE : ChargingStatus.UNAVAILABLE);
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStationForRemove);
        chargingStationForRemove.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */
        chargingStationForRemove = chargingStationRepository.save(chargingStationForRemove);

        //when
        chargingStationRepository.deleteById(chargingStationForRemove.getChargingStationId());
        Optional<ChargingStation> chargingStationOptional = chargingStationRepository.findById(chargingStationForRemove.getChargingStationId());

        //then
        assertThat(chargingStationOptional).isEmpty();
    }
}
