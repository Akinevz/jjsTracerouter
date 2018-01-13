/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author waterbucket
 */
public class WebServer {

    private final int port;
    private boolean running;
    private ServerSocket serverSocket;

    public WebServer(int port) {
        this.running = false;
        this.port = port;
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.running = true;
            while (running) {
                Socket clientSocket = null;
                System.out.println("Waiting...");
                clientSocket = serverSocket.accept();
                new Thread(
                        new WorkerThread(
                                clientSocket, "SomeText")
                ).start();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Cannot open server port " + this.port);
        }
    }
}
class WorkerThread implements Runnable {

    private Socket clientSock;
    private String text;

    WorkerThread(Socket clientSocket, String text) {
        this.clientSock = clientSocket;
        this.text = text;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSock.getInputStream();
            OutputStream output = clientSock.getOutputStream();
            output.write(("HTTP/1.1 200 OK\n\n" + this.text).getBytes("UTF-8"));
            output.close();
            input.close();
            System.out.println("Request processed");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
