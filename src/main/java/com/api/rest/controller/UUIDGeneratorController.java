package com.api.rest.controller;

import com.api.rest.service.ServiceUUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("uuid-generator")
public class UUIDGeneratorController {

    @Autowired
    private ServiceUUIDGenerator serviceUUIDGenerator;

    @GetMapping("/newType1")
    private UUID getType1ID(){
        return serviceUUIDGenerator.generateType1UUID();
    }

    @GetMapping("/newType4")
    private UUID getType4ID(){
        return serviceUUIDGenerator.generateType4UUID();
    }

    @GetMapping("/check")
    private Boolean checkID(@RequestParam("id") String id){
        return serviceUUIDGenerator.checkID(id);
    }
}
