package com.api.rest.controller;

import com.api.rest.model.*;
import com.api.rest.repository.ChargingStationRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Type;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
public class ChargingStationControllerContainerTests {

    static final MySQLContainer MY_SQL_CONTAINER;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:latest");
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",() -> MY_SQL_CONTAINER.getJdbcUrl());
        registry.add("spring.datasource.username",() -> MY_SQL_CONTAINER.getUsername());
        registry.add("spring.datasource.password",() -> MY_SQL_CONTAINER.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "create");

    }

    Set<ChargingStationPoint> getChargingStationPoints() {

        Set<ChargingStationPoint> chargingStationPoints = new HashSet<>();
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

        return chargingStationPoints;
    }

    ChargingStationLocation getChargingStationLocation(){

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

        return chargingLocations.get(0);
    }

    ChargingStation getChargingStation(){

        Set<ChargingStationPoint> points = getChargingStationPoints();

        ChargingStation chargingStation = ChargingStation.builder()
                .chargingStationId(UUID.randomUUID().toString())
                .chargingStationPoints(points)
                .chargingPointsAmount(points.size())
                .chargingStatus((points.size() > 0) ? ChargingStatus.AVAILABLE.getValue() : ChargingStatus.UNAVAILABLE.getValue())
                .chargingStationType(ChargingStationType.AC.getValue())
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE.getValue())
                .chargingStationLocation(getChargingStationLocation())
                .build();

        return chargingStation;
    }

    @BeforeEach
    public void beforeEach(){
        ChargingStation chargingStationSaved = getChargingStation();
        chargingStationRepository.save(chargingStationSaved);
    }
    @AfterEach
    public void afterEach(){
        chargingStationRepository.deleteAll();
    }

    @Test
    void saveChargingStationEntity() {
        ChargingStation chargingStationEntity = getChargingStation();
        chargingStationEntity.setChargingPointsAmount(5);
        chargingStationEntity.setChargingStatus(ChargingStatus.IN_USE.getValue());
        webTestClient.post()
                .uri("/chargingstation")
                .bodyValue(chargingStationEntity)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(ChargingStation.class)
                .consumeWith(orderentity -> Assertions.assertNotNull(orderentity.getResponseBody().getChargingStationId()));
    }

    @Test
    void getChargingStationEntity() {
        webTestClient.get()
                .uri("/chargingstation")
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(ChargingStation.class)
                .consumeWith(listOfObject ->{
                    var list  = listOfObject.getResponseBody();
                    Assertions.assertTrue(list.size() == 1);
                });
    }

}
