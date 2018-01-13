/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import java.util.regex.Pattern;

/**
 *
 * @author waterbucket
 */
public class IP {
    
    public final static Pattern IPRegex = Pattern.compile("\\d");
    private char one;
    private char two;
    private char three;
    private char four;
    
    public IP(char one, char two, char three, char four) {
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
    
    public static IP parse(String ip) throws Exception{
        char one, two, three, four;
        if(Pattern.matches(ip, ip)){
            
        }
        return new IP(one, two, three, four);
    }
    
}
