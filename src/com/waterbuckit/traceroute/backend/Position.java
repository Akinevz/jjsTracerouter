/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import java.util.regex.Matcher;

/**
 *
 * @author waterbucket
 */
public class Position {
    
    private final double lon;
    private final double lat;

    public Position(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return String.format("Position(%f,%f)", lon,lat);
    }
    public static Position parse(String lon, String lat) throws Exception{
        double nLon, nLat;
        try {
            nLon = Double.parseDouble(lon);
            nLat = Double.parseDouble(lat);
            return new Position(nLon, nLat);
        } catch (RuntimeException e) {
            throw new Exception("Malformed entry for lon/lat");
        }
    }
}
