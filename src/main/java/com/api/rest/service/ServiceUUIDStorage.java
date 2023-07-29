package com.api.rest.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ServiceUUIDStorage {

    // create global scope
    Set<String> idSet = new HashSet<>();

    public Boolean put(String id){
        return idSet.add(id);
    }

    public Boolean contains(String id){
        return idSet.contains(id);
    }
}
