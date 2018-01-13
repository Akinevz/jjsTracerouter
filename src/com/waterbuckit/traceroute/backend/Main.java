package com.waterbuckit.traceroute.backend;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.net.util.SubnetUtils;

/**
 *
 * @author waterbucket
 * @author akinevz
 */
public class Main {

    public static void main(String[] args) throws Exception {
        WebServer server = new WebServer(8080);
        server.start();
//        System.out.println(IP.getGeo("8.8.8.8"));
//        /*
        // Test block for regex matching.
       
//        
//        String testBadIP = "254.1.2";
//        String testTHEONE = "1.0.0.0/24";
//        String testBadIP2 = "254.1.2.2/21";
//        String testGoodIP = "1.1.1.1";
//        String testGoodIP2 = "254.125.1.2";
//        String testBadIP3 = "254.125.1.2/21";
//        
//        Arrays.asList(testTHEONE)
//                .stream()
//                .map(CSVParser.IP_SUBNET_SPLIT::matcher)
//                .map(s -> {s.find();return s;})
//                .map(Matcher::group)
//                .forEach(System.err::println);
//        Matcher match = CSVParser.IP_SUBNET_SPLIT.matcher(testTHEONE);
//        String wrong = "1.0.0.0/24";
//        Matcher match = CSVParser.IP_SUBNET_SPLIT.matcher(wrong);
//        match.find();
//        System.out.println(match.start(1));
//        System.out.println(match.end(1));
//        System.out.println(wrong.substring(match.start(2), match.end(2)));
//        System.out.println(match.group(2));
//        System.out.println(match.group(1));
//        CSVParser parser = new CSVParser(new File("IPs.csv"));
//        Map<IP,Geoloc> geolocs = parser
//                .getGeolocs()
//                .stream()
//                .collect(Collectors.toMap(Geoloc::getIp, s->s));
//        System.out.println("Loaded");
//        String targetIP = "8.8.8.8";
//        for(IP ip : geolocs.keySet()){
//            SubnetUtils utils = new SubnetUtils(ip.getIp());
//            if(utils.getInfo().isInRange(targetIP)){
//                System.out.println(geolocs.get(ip));
//                System.exit(0);
//            }
//        }
//        System.out.println(geolocs.toString());

//        if(args[0] == null){
//            WebServer server = new WebServer(80);
//        }else{
//            WebServer server = new WebServer(Integer.parseInt(args[0]));
//        }
    }
}