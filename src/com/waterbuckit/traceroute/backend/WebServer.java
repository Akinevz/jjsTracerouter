/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import java.io.IOException;
import java.net.ServerSocket;
/**
 *
 * @author waterbucket
 */
public class WebServer {

    private final int port;
    
    public WebServer(int port) {
        this.port = port;
    }
    
    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot open server port " + this.port);
        }
    }
}
