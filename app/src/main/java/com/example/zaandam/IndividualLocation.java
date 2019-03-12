package com.example.zaandam;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * POJO class for an individual location
 */
public class IndividualLocation {

    private String name;
    private String address;
    private String distance;
    private LatLng location;
    private String coordinates;
    private String category;

    public IndividualLocation(String name, String address, LatLng location, String coordinates, String category) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.coordinates = coordinates;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getCategory() {
        return category;
    }
}