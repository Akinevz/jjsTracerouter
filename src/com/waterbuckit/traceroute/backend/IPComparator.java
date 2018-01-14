///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.waterbuckit.traceroute.backend;
//
//import java.util.Comparator;
//import java.util.stream.Stream;
//
///**
// *
// * @author waterbucket
// */
//public class IPComparator implements Comparator<IP> {
//    
//    /**
//     * Returns 1, 0 or -1 to return whether o1 is "before", equal to, or after
//     * o2 in the list of IPs by comparing bytes of the IP address.
//     * The first element that is not 0 is then returned which dictates whether
//     * o2 is "before" or "after" o1. If all were 0 then 0 will be returned 
//     * so it is clear that the IP addresses were the same.
//     * 
//     * @param o1
//     * @param o2
//     * @return 
//     */
//    @Override
//    public int compare(IP o1, IP o2) {
//        return Stream.of(Integer.compare(o1.getOne(), o2.getOne()),
//                Integer.compare(o1.getTwo(), o2.getTwo()),
//                Integer.compare(o1.getThree(), o2.getThree()),
//                Integer.compare(o1.getFour(), o2.getFour()))
//                .filter(s -> s != 0)
//                .findFirst()
//                .orElse(0);
//    }
//
//}
