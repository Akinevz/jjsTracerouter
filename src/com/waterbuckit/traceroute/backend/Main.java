package com.waterbuckit.traceroute.backend;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 *
 * @author waterbucket
 * @author akinevz
 */
public class Main {

    public static void main(String[] args) {
//        /*
        // Test block for regex matching.
        
        String testBadIP = "254.1.2";
        String testBadIP2 = "254.1.2.c";
        String testGoodIP = "1.1.1.1";
        String testGoodIP2 = "254.125.1.2";
        String testBadIP3 = "254.125.1.2/21";
        
        Arrays.asList(testBadIP,testBadIP2,testGoodIP,testGoodIP2,testBadIP3)
                .stream()
                .map(IP.IP_REGEX::matcher)
                .map(Matcher::find)
                .forEach(System.out::println);
//        */
        if(args[0] == null){
            WebServer server = new WebServer(80);
        }else{
            WebServer server = new WebServer(Integer.parseInt(args[0]));
        }
    }
    
}