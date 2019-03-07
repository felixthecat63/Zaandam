package com.example.zaandam;

/*
    Thanks to Jason Winn for this implementation of the Haversine formula!
    Available at:
    https://github.com/jasonwinn/haversine
 */

public class Haversine {
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static double computeDistance(Double startLat, Double startLong, Double endLat, Double endLong) {

        Double dLat  = Math.toRadians((endLat - startLat));
        Double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        Double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- distance
    }

    public static Double haversin(Double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}


