package com.api.rest.model;

public class ChargingStationType implements java.io.Serializable,
        Comparable<ChargingStationType> {

    public static final ChargingStationType AC = new ChargingStationType("AC");

    public static final ChargingStationType DC = new ChargingStationType("DC");

    public static final Class<ChargingStationType> TYPE;

    static {
        try {
            TYPE = (Class<ChargingStationType>) Class.forName(String.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String value;

    public ChargingStationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean parseAC(String s) {
        return AC.value.equalsIgnoreCase(s);
    }

    public static ChargingStationType valueOf(String s) { return parseAC(s) ? AC : DC;  }

    public static String toString(String b) {
        return String.valueOf(b);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ChargingStationType) {
            return value == ((ChargingStationType)obj).getValue();
        }
        return false;
    }

    public static boolean getBoolean(String name) {
        boolean result = false;
        try {
            result = parseAC(System.getProperty(name));
        } catch (IllegalArgumentException | NullPointerException e) {
        }
        return result;
    }

    public int compareTo(ChargingStationType b) {
        return compare(this.value, b.value);
    }

    public static int compare(String x, String y) {
        return x.compareTo(y);
    }

}
