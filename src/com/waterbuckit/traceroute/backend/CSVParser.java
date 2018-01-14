/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author waterbucket
 */
public class CSVParser {

    private final File sourceFile;
    static final Pattern IP_SUBNET_SPLIT = Pattern.compile("^(.*)\\/(.*)$");

    public CSVParser(File file) {
        this.sourceFile = file;
    }

    /* 
    header
    network,geoname_id,registered_country_geoname_id,
    represented_country_geoname_id,is_anonymous_proxy,is_satellite_provider,
    postal_code,latitude,longitude,accuracy_radius
     */
    public List<Geoloc> getGeolocs() throws FileNotFoundException,
            Exception {
        List<Geoloc> geolocs = new ArrayList<>();
        Scanner s = new Scanner(this.sourceFile);
        s.useDelimiter("\n");
        s.next();
        while (s.hasNext()) {
            String[] entry = s.next().split(",");
//            System.out.println(Arrays.toString(entry));
            try {
//                geolocs.add(new Geoloc(IP.parse(this.splitIPForIP(entry[0])),
//                        Position.parse(entry[8], entry[7]),
//                        Integer.parseInt(entry[9])));
                geolocs.add(new Geoloc(new IP(entry[0]),
                        Position.parse(entry[8], entry[7]),
                        Integer.parseInt(entry[9])));
            } catch (Exception e) {
//                System.err.println("Malformed entry!");
            }
        }
        return geolocs;
    }
    
//    private String splitIPForIP(String ip) {
//        Matcher match = IP_SUBNET_SPLIT.matcher(ip);
//        match.find();
//        return match.group(1);
//    }
}
