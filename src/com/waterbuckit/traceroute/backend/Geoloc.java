/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

/**
 *
 * @author waterbucket
 */
public class Geoloc {
    
    private final IP ip;
    private final Position position;
    private final int radius;

    public Geoloc(IP ip, Position position, int radius) {
        this.ip = ip;
        this.position = position;
        this.radius = radius;
    }

    public IP getIp() {
        return ip;
    }

    public Position getPosition() {
        return position;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return String.format("Geoloc(%s, %s, radius:%d)\n", ip.toString(),position.toString(),radius);
    }
}
