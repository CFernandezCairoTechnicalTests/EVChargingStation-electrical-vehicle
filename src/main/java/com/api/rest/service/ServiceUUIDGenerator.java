package com.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class ServiceUUIDGenerator {

    @Autowired
    private ServiceUUIDStorage cache;

    /*
    First of all, if privacy is a concern, UUIDv1 can alternatively be generated with a random 48-bit number instead of the MAC address
     */

    private static long get64LeastSignificantBitsForVersion1() {
        long random63BitLong = new Random().nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong + variant3BitFlag;
    }

    private static long get64MostSignificantBitsForVersion1() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }

    public UUID generateType1UUID() {

        long most64SigBits = get64MostSignificantBitsForVersion1();
        long least64SigBits = get64LeastSignificantBitsForVersion1();

        UUID id = new UUID(most64SigBits, least64SigBits);
        cache.put(id.toString());
        return id;

    }

    public UUID generateType4UUID() {

        UUID id = UUID.randomUUID();
        cache.put(id.toString());
        return id;

    }

    public Boolean checkID(String id){
        return cache.contains(id);
    }
}
