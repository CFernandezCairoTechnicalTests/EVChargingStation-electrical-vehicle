package com.api.rest.controller;


import com.api.rest.model.*;
import com.api.rest.service.ChargingStationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Type;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ChargingStationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChargingStationService chargingStationService;

    @Autowired
    private ObjectMapper objectMapper;

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
    @DisplayName("POST :: Save ChargingStation")
    @Test
    void testSaveChargingStation() throws Exception {
        //given
        ChargingStation chargingStation = getChargingStation();
        given(chargingStationService.saveChargingStation(any(ChargingStation.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/chargingstation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chargingStation)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.chargingStatus",is(chargingStation.getChargingStatus())))
                .andExpect(jsonPath("$.chargingPointsAmount",is(chargingStation.getChargingPointsAmount())));
    }

    @DisplayName("Get :: All not empty ChargingStations")
    @Test
    void testListChargingStations() throws Exception{
        //given
        List<ChargingStation> chargingStationList = new ArrayList<>();
        chargingStationList.add(getChargingStation());
        chargingStationList.add(getChargingStation());
        given(chargingStationService.getAllChargingStations()).willReturn(chargingStationList);

        //when
        ResultActions response = mockMvc.perform(get("/chargingstation"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(chargingStationList.size())));
    }

    @DisplayName("Get :: All not empty ChargingStations")
    @Test
    void testListAvailableChargingStations() throws Exception{
        //given
        List<ChargingStation> availableChargingStationList = new ArrayList<>();
        availableChargingStationList.add(getChargingStation());
        availableChargingStationList.add(getChargingStation());
        given(chargingStationService.getAllAvailableChargingStations()).willReturn(availableChargingStationList);

        //when
        ResultActions response = mockMvc.perform(get("/chargingstation/available"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(availableChargingStationList.size())));
    }

    @DisplayName("GET :: All empty ChargingStations")
    @Test
    void testListEmptyChargingStations() throws Exception{
        //given
        List<ChargingStation> chargingStationList = new ArrayList<>();
        given(chargingStationService.getAllChargingStations()).willReturn(chargingStationList);

        //when
        ResultActions response = mockMvc.perform(get("/chargingstation"));

        //then
        response.andExpect(status().isNoContent());
    }

    @DisplayName("GET :: ChargingStation by ID")
    @Test
    void testGetChargingStationByID() throws Exception {
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStation = getChargingStation();
        chargingStation.setChargingStatus(ChargingStatus.IN_USE.getValue());
        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.of(chargingStation));

        //when
        ResultActions response = mockMvc.perform(get("/chargingstation/{id}",chargingStationId));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chargingStatus",is(chargingStation.getChargingStatus())))
                .andExpect(jsonPath("$.chargingPointsAmount",is(chargingStation.getChargingPointsAmount())));
    }


    @DisplayName("GET :: ChargingStationAvailable by ID")
    @Test
    void testGetChargingStationStatusByID() throws Exception {
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStation = getChargingStation();
        chargingStation.setChargingStatus(ChargingStatus.IN_USE.getValue());
        given(chargingStationService.getChargingStationAvailableById(chargingStationId)).willReturn(Optional.of(chargingStation));

        //when
        ResultActions response = mockMvc.perform(get("/chargingstation/available/{id}",chargingStationId));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chargingStatus",is(chargingStation.getChargingStatus())))
                .andExpect(jsonPath("$.chargingPointsAmount",is(chargingStation.getChargingPointsAmount())));
    }

    @DisplayName("GET :: Not found ChargingStations")
    @Test
    void testNotFoundChargingStations() throws Exception{
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStation = getChargingStation();
        chargingStation.setChargingStationType(ChargingStationType.DC.getValue());
        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/chargingstation/{id}",chargingStationId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("PUT :: Update ChargingStation")
    @Test
    void testForUpdateChargingStation() throws Exception{
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStationSaved = getChargingStation();
        chargingStationSaved.setChargingPointsAmount(0);

        ChargingStation chargingStationUpdated = getChargingStation();
        chargingStationUpdated.setChargingPointsAmount(5);

        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.of(chargingStationSaved));
        given(chargingStationService.updateChargingStation(any(ChargingStation.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/chargingstation/{id}",chargingStationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chargingStationUpdated)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.chargingStatus",is(chargingStationUpdated.getChargingStatus())))
                .andExpect(jsonPath("$.chargingPointsAmount",is(chargingStationUpdated.getChargingPointsAmount())));

    }

    @DisplayName("PUT :: Update not found ChargingStations")
    @Test
    void testUpdateNotFoundChargingStations() throws Exception{
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStationSaved = getChargingStation();
        chargingStationSaved.setChargingStationType(ChargingStationType.AC.getValue());
        chargingStationSaved.setChargingStatus(ChargingStatus.AVAILABLE.getValue());

        ChargingStation chargingStationUpdated = getChargingStation();
        chargingStationSaved.setChargingStationType(ChargingStationType.DC.getValue());
        chargingStationSaved.setChargingStatus(ChargingStatus.UNAVAILABLE.getValue());

        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.empty());
        given(chargingStationService.updateChargingStation(any(ChargingStation.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/chargingstation/{id}",chargingStationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chargingStationUpdated)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("DELETE :: Remove ChargingStation")
    @Test
    void testRemoveChargingStationBy() throws Exception{
        //given
        String chargingStationId = "1L";
        willDoNothing().given(chargingStationService).deleteChargingStationById(chargingStationId);

        //when
        ResultActions response = mockMvc.perform(delete("/chargingstation/{id}",chargingStationId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
