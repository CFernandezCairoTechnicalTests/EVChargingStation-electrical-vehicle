package com.api.rest.controller;


import com.api.rest.model.*;
import com.api.rest.repository.ChargingStationRepository;
import com.api.rest.service.ChargingStationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    @MockBean
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGuardarEmpleado() throws Exception {
        //given
        Set<ChargingStationPoint> chargingStationPoints = new HashSet<>();
        chargingStationPoints.add(ChargingStationPoint.builder()
                .chargingPointId(UUID.randomUUID())
                .powerLevel(20000)
                .status(ChargingStatus.IN_USE)
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

        ChargingStationLocation chargingStationLocation = chargingLocations.get(0);
        chargingStationLocation.getAddress().setChargingStationLocation(chargingStationLocation);
        chargingStationLocation.getPosition().setChargingStationLocation(chargingStationLocation);
        /*        chargingStationLocation                */

        ChargingStation chargingStation = ChargingStation.builder()
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();
        given(chargingStationService.saveChargingStation(any(ChargingStation.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(post("/api/chargingstation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chargingStation)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.chargingStatus",is(chargingStation.getChargingStatus())))
                .andExpect(jsonPath("$.chargingPointsAmount",is(chargingStation.getChargingPointsAmount())));
    }

    @Test
    void testListarEmpleados() throws Exception{
        //given
        List<ChargingStation> chargingStationList = new ArrayList<>();
        chargingStationList.add(ChargingStation.builder().chargingStationType(ChargingStationType.AC).chargingPointsAmount(0).chargingStatus(ChargingStatus.UNAVAILABLE).build());
        chargingStationList.add(ChargingStation.builder().chargingStationType(ChargingStationType.DC).chargingPointsAmount(1).chargingStatus(ChargingStatus.AVAILABLE).build());
        chargingStationList.add(ChargingStation.builder().chargingStationType(ChargingStationType.AC).chargingPointsAmount(3).chargingStatus(ChargingStatus.IN_USE).build());
        chargingStationList.add(ChargingStation.builder().chargingStationType(ChargingStationType.AC).chargingPointsAmount(2).chargingStatus(ChargingStatus.UNAVAILABLE).build());
        chargingStationList.add(ChargingStation.builder().chargingStationType(ChargingStationType.DC).chargingPointsAmount(0).chargingStatus(ChargingStatus.AVAILABLE).build());
        given(chargingStationService.getAllChargingStations()).willReturn(chargingStationList);

        //when
        ResultActions response = mockMvc.perform(get("/api/chargingstation"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(chargingStationList.size())));
    }

    @Test
    void testObtenerEmpleadoPorId() throws Exception{
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStation = ChargingStation.builder()
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();
        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.of(chargingStation));

        //when
        ResultActions response = mockMvc.perform(get("/api/chargingstation/{id}",chargingStationId));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chargingStatus",is(chargingStation.getChargingStatus())))
                .andExpect(jsonPath("$.chargingPointsAmount",is(chargingStation.getChargingPointsAmount())));
    }

    @Test
    void testObtenerEmpleadoNoEncontrado() throws Exception{
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStation = ChargingStation.builder()
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();
        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/chargingstation/{id}",chargingStationId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testActualizarEmpleado() throws Exception{
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStationSaved = ChargingStation.builder()
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();

        ChargingStation chargingStationUpdated = ChargingStation.builder()
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.AVAILABLE)
                .build();

        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.of(chargingStationSaved));
        given(chargingStationService.updateChargingStation(any(ChargingStation.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/chargingstation/{id}",chargingStationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chargingStationUpdated)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.chargingStatus",is(chargingStationUpdated.getChargingStatus())))
                .andExpect(jsonPath("$.chargingPointsAmount",is(chargingStationUpdated.getChargingPointsAmount())));

    }

    @Test
    void testActualizarEmpleadoNoEncontrado() throws Exception{
        //given
        String chargingStationId = "1L";
        ChargingStation chargingStationSaved = ChargingStation.builder()
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.UNAVAILABLE)
                .build();

        ChargingStation chargingStationUpdated = ChargingStation.builder()
                .chargingStationType(ChargingStationType.AC)
                .chargingPointsAmount(0)
                .chargingStatus(ChargingStatus.AVAILABLE)
                .build();

        given(chargingStationService.getChargingStationById(chargingStationId)).willReturn(Optional.empty());
        given(chargingStationService.updateChargingStation(any(ChargingStation.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/api/chargingstation/{id}",chargingStationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chargingStationUpdated)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testEliminarEmpleado() throws Exception{
        //given
        String chargingStationId = "1L";
        willDoNothing().given(chargingStationService).deleteChargingStation(chargingStationId);

        //when
        ResultActions response = mockMvc.perform(delete("/api/chargingstation/{id}",chargingStationId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
