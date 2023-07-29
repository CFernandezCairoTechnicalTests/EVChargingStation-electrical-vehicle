package com.api.rest.service;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.*;
import com.api.rest.repository.ChargingStationRepository;
import com.api.rest.service.impl.ChargingStationServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.lang.reflect.Type;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ChargingStationServiceTests {

    @Mock
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @InjectMocks
    private ChargingStationServiceImpl chargingStationService;

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
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ChargingStationLocation>>() { }.getType();
        chargingLocations = gson.fromJson("[\n" +
                "    {\n" +
                "      \"title\": \"Statue of Liberty\",\n" +
                "      \"id\": \"here:pds:place:840dr5r7-9df1349708914ceba912909970598cf7\",\n" +
                "      \"language\": \"en\",\n" +
                "      \"resultType\": \"place\",\n" +
                "      \"address\": {\n" +
                "        \"label\": \"Statue of Liberty, New York, NY 10004, United States\",\n" +
                "        \"countryCode\": \"USA\",\n" +
                "        \"countryName\": \"United States\",\n" +
                "        \"stateCode\": \"NY\",\n" +
                "        \"state\": \"New York\",\n" +
                "        \"county\": \"New York\",\n" +
                "        \"city\": \"New York\",\n" +
                "        \"district\": \"Liberty Island\",\n" +
                "        \"postalCode\": \"10004\"\n" +
                "      },\n" +
                "      \"position\": {\n" +
                "        \"lat\": 40.68925,\n" +
                "        \"lng\": -74.04445\n" +
                "      },\n" +
                "      \"access\": [\n" +
                "        {\n" +
                "          \"lat\": 40.68971,\n" +
                "          \"lng\": -74.04357\n" +
                "        }\n" +
                "      ],\n" +
                "      \"distance\": 6094,\n" +
                "      \"categories\": [\n" +
                "        {\n" +
                "          \"id\": \"300-3000-0025\",\n" +
                "          \"name\": \"Historical Monument\",\n" +
                "          \"primary\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"300-3000-0000\",\n" +
                "          \"name\": \"Landmark-Attraction\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"references\": [\n" +
                "        {\n" +
                "          \"supplier\": {\n" +
                "            \"id\": \"core\"\n" +
                "          },\n" +
                "          \"id\": \"899132706\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"supplier\": {\n" +
                "            \"id\": \"tripadvisor\"\n" +
                "          },\n" +
                "          \"id\": \"103887\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"contacts\": [\n" +
                "        {\n" +
                "          \"phone\": [\n" +
                "            {\n" +
                "              \"value\": \"+12123633200\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"www\": [\n" +
                "            {\n" +
                "              \"value\": \"http://www.nps.gov/stli\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"value\": \"https://www.nps.gov/stli/index.htm\",\n" +
                "              \"categories\": [\n" +
                "                {\n" +
                "                  \"id\": \"300-3000-0025\"\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "]", listType);

        chargingStationLocation = chargingLocations.get(0);
        chargingStationLocation.getAddress().setChargingStationLocation(chargingStationLocation);
        chargingStationLocation.getPosition().setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */


        chargingStation = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC.getValue())
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE.getValue())
                .build();

        /*        chargingStationPoints                */
        //chargingStation.setChargingStationPoints(chargingStationPoints);
        chargingStation.setChargingPointsAmount(chargingStationPoints.size());
        chargingStation.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue());
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStation);
        //chargingStation.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */
    }

    @DisplayName("Save ChargingStation")
    @Test
    void testSaveChargingStation(){
        //given
        given(chargingStationRepository.findById(chargingStation.getChargingStationId()))
                .willReturn(Optional.empty());
        given(chargingStationRepository.save(chargingStation)).willReturn(chargingStation);

        //when
        ChargingStation chargingStationSaved = chargingStationService.saveChargingStation(chargingStation);

        //then
        assertThat(chargingStationSaved).isNotNull();
    }

    @DisplayName("Save ChargingStation with Throw Exception")
    @Test
    void saveChargingStationWithThrowException(){
        //given
        given(chargingStationRepository.findById(chargingStation.getChargingStationId()))
                .willReturn(Optional.of(chargingStation));
        //when
        assertThrows(ResourceNotFoundException.class,() -> {
            chargingStationService.saveChargingStation(chargingStation);
        });

        //then
        verify(chargingStationRepository,never()).save(any(ChargingStation.class));
    }

    @DisplayName("Get all ChargingStations")
    @Test
    void testListChargingStations(){
        //given
        ChargingStation chargingStationForList = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC.getValue())
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE.getValue())
                .build();

        /*        chargingStationPoints                */
        //chargingStationForList.setChargingStationPoints(chargingStationPoints);
        chargingStationForList.setChargingPointsAmount(chargingStationPoints.size());
        chargingStationForList.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue());
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStationForList);
        //chargingStationForList.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */
        given(chargingStationRepository.findAll()).willReturn(List.of(chargingStation,chargingStationForList));

        //when
        List<ChargingStation> allChargingStations = chargingStationService.getAllChargingStations();

        //then
        assertThat(allChargingStations).isNotNull();
        assertThat(allChargingStations.size()).isEqualTo(2);
    }

    @DisplayName("Get Empty ChargingStations")
    @Test
    void testEmptyChargingStations(){
        //given
        ChargingStation chargingStationForList = ChargingStation.builder()
                //.chargingStationId(chargingStationUUID)
                .chargingStationType(ChargingStationType.AC.getValue())
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE.getValue())
                .build();

        /*        chargingStationPoints                */
        //chargingStationForList.setChargingStationPoints(chargingStationPoints);
        chargingStationForList.setChargingPointsAmount(chargingStationPoints.size());
        chargingStationForList.setChargingStatus((chargingStationPoints.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue());
        /*        chargingStationPoints                */

        /*        chargingStationLocation                */
        chargingStationLocation.setChargingStation(chargingStationForList);
        //chargingStationForList.setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */
        given(chargingStationRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<ChargingStation> allChargingStations = chargingStationService.getAllChargingStations();

        //then
        assertThat(allChargingStations).isEmpty();
        assertThat(allChargingStations.size()).isEqualTo(0);
    }

    @DisplayName("Get ChargingStation by ID")
    @Test
    void testGetChargingStationByID(){
        //given
        given(chargingStationRepository.findById(null)).willReturn(Optional.of(chargingStation));

        //when
        ChargingStation chargingStationSaved = chargingStationService.getChargingStationById(chargingStation.getChargingStationId()).get();

        //then
        assertThat(chargingStationSaved).isNotNull();
    }

    @DisplayName("Update ChargingStation")
    @Test
    void testForUpdateChargingStation(){
        //given
        given(chargingStationRepository.save(chargingStation)).willReturn(chargingStation);
        chargingStation.setChargingStatus(ChargingStatus.AVAILABLE.getValue());
        chargingStation.setChargingStationType(ChargingStationType.AC.getValue());

        //when
        ChargingStation updatedChargingStation  = chargingStationService.updateChargingStation(chargingStation);

        //then
        assertThat(updatedChargingStation.getChargingStatus()).isEqualTo(ChargingStatus.AVAILABLE.getValue());
        assertThat(updatedChargingStation.getChargingStationType()).isEqualTo(ChargingStationType.AC.getValue());
    }

    @DisplayName("Remove ChargingStation by ID")
    @Test
    void testRemoveChargingStationByID(){
        //given
        String chargingStationId = "1L";
        willDoNothing().given(chargingStationRepository).deleteById(chargingStationId);

        //when
        chargingStationService.deleteChargingStation(chargingStationId);

        //then
        verify(chargingStationRepository,times(1)).deleteById(chargingStationId);
    }
}
