/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author waterbucket
 */
public class IP {
    
    public final static Pattern IP_REGEX = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)");
    private char one;
    private char two;
    private char three;
    private char four;
    
    private IP(char one, char two, char three, char four) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
    }
    
    public char getOne() {
        return one;
    }

    public char getTwo() {
        return two;
    }

    public char getThree() {
        return three;
    }

    public char getFour() {
        return four;
    }
    /**
     * Guard method
     * 
     * Parses IP addresses to make sure they follow the pattern defined by
     * IP_REGEX. Creates a valid IP object.
     * Throws an exception if invalid.
     * 
     * @param ip
     * @return
     * @throws Exception 
     */
    public static IP parse(String ip) throws Exception{
        char one, two, three, four;
        Matcher match = IP_REGEX.matcher(ip);
        if(match.matches()){
            one = (char) Integer.parseInt(match.group());
            two = (char) Integer.parseInt(match.group());
            three = (char) Integer.parseInt(match.group());
            four = (char) Integer.parseInt(match.group());
            
            return new IP(one, two, three, four);
        }else{
            throw new Exception("Could not parse " + ip);
        }
    }
    
}
