package com.api.rest.model;

public class ChargingStatus implements java.io.Serializable,
        Comparable<ChargingStatus> {
    public static final ChargingStatus AVAILABLE = new ChargingStatus("AVAILABLE");

    public static final ChargingStatus IN_USE = new ChargingStatus("IN_USE");

    public static final ChargingStatus UNAVAILABLE = new ChargingStatus("UNAVAILABLE");

    public static final Class<ChargingStatus> TYPE;

    static {
        try {
            TYPE = (Class<ChargingStatus>) Class.forName(String.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String value;

    public ChargingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean parseAVAILABLE(String s) {
        return AVAILABLE.value.equalsIgnoreCase(s);
    }

    public static boolean parseUNAVAILABLE(String s) {
        return UNAVAILABLE.value.equalsIgnoreCase(s);
    }

    public static ChargingStatus valueOf(String s) { return parseAVAILABLE(s) ? AVAILABLE : parseUNAVAILABLE(s) ? UNAVAILABLE : IN_USE;  }

    public static String toString(String b) {
        return String.valueOf(b);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ChargingStatus) {
            return value == ((ChargingStatus)obj).getValue();
        }
        return false;
    }

    public static boolean getBoolean(String name) {
        boolean result = false;
        try {
            result = parseAVAILABLE(System.getProperty(name));
        } catch (IllegalArgumentException | NullPointerException e) {
        }
        return result;
    }

    public int compareTo(ChargingStatus b) {
        return compare(this.value, b.value);
    }

    public static int compare(String x, String y) {
        return x.compareTo(y);
    }
}
