/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.net.util.SubnetUtils;

/**
 *
 * @author waterbucket
 */
public class IP {
//
//    public final static Pattern IP_REGEX = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)$");
//    private char one;
//    private char two;
//    private char three;
//    private char four;
    private String ip;

    public IP(String ip) {
        this.ip = ip;
    }

//    private IP(char one, char two, char three, char four) {
//        this.one = one;
//        this.two = two;
//        this.three = three;
//        this.four = four;
//    }

    public String getIp() {
        return this.ip;
    }

//    public char getOne() {
//        return one;
//    }
//
//    public char getTwo() {
//        return two;
//    }
//
//    public char getThree() {
//        return three;
//    }
//
//    public char getFour() {
//        return four;
//    }

    /**
     * Guard method
     *
     * Parses IP addresses to make sure they follow the pattern defined by
     * IP_REGEX. Creates a valid IP object. Throws an exception if invalid.
     *
     * @param ip
     * @return
     * @throws Exception
     */
//    public static IP parse(String ip) throws Exception {
//        char one, two, three, four;
//        Matcher match = IP_REGEX.matcher(ip);
//        if (match.matches()) {
//            one = (char) Integer.parseInt(match.group(1));
//            two = (char) Integer.parseInt(match.group(2));
//            three = (char) Integer.parseInt(match.group(3));
//            four = (char) Integer.parseInt(match.group(4));
//
//            return new IP(one, two, three, four);
//        } else {
//            throw new Exception("Could not parse " + ip);
//        }
//    }

    @Override
    public String toString() {
//        return String.format("IP(%d.%d.%d.%d)", (int)one,(int)two,(int)three,(int)four);
        return String.format("IP(%s)", this.ip);
    }
    
    public static Map<IP,Geoloc> getGeolocMap() throws Exception{
        CSVParser parser = new CSVParser(new File("IPs.csv"));
        Map<IP, Geoloc> geolocs = parser
                .getGeolocs()
                .stream()
                .collect(Collectors.toMap(Geoloc::getIp, s -> s));
        System.out.println("Geolocs loaded");
        return geolocs;
    }
    
    public static Geoloc getGeo(String pIp, Map<IP,Geoloc> geolocs) {
        for (IP cidr : geolocs.keySet()) {
            SubnetUtils utils = new SubnetUtils(cidr.getIp());
            if (utils.getInfo().isInRange(pIp)) {
                return geolocs.get(cidr);
            }
        }
        return null;
    }
}
