/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterbuckit.traceroute.backend;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author waterbucket
 */
public class WebServer {
    private Gson gson;
    private final int port;
    private boolean running;
    private ServerSocket serverSocket;
    final Map<IP, Geoloc> geolocs;

    WebServer(int port, Map<IP, Geoloc> geolocs) {
        this.port = port;
        this.running = false;
        this.geolocs = geolocs;
        this.gson = new Gson();
    }

    public void start() {
        setServerSocket();
        this.running = true;
        while (running) {
            try {
                Socket clientSocket = null;
                System.out.println("Waiting...");
                clientSocket = serverSocket.accept();
                new Thread(
                        new WorkerThread(
                                clientSocket, this.geolocs, this.gson)
                ).start();
            } catch (IOException ex) {
                System.out.println("");
            }
        }
    }

    private void setServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException ex) {
            System.out.println("Cannot open server on port " + this.port);
        }
    }
}

class WorkerThread implements Runnable {

    private final Socket clientSock;
    private final Map<IP, Geoloc> geolocs;
    private Gson gson;
    
    WorkerThread(Socket clientSocket, Map<IP, Geoloc> geolocs, Gson gson) {
        this.clientSock = clientSocket;
        this.geolocs = geolocs;
        this.gson = gson;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSock.getInputStream();
            OutputStream output = clientSock.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            StringBuilder sbRecevieved = new StringBuilder();
            String line;
            while (!"".equals(line = br.readLine()) && sbRecevieved.length() < 300) {
                sbRecevieved.append(line).append("\n");
            }
            if(sbRecevieved.toString().trim().length() == 0){
                output.close();
                input.close();
                return;
            }
            System.out.println(sbRecevieved.toString());
            String parsedIP = parseIP(sbRecevieved);
            System.out.println("PARSED: " + parsedIP);
            if (parsedIP != null) {
                LinkedHashSet<String> parsedTrRoutes = new LinkedHashSet<>();
                parsedTrRoutes.add(this.getExternalIP());
                parsedTrRoutes.addAll(parseTraceRoute(traceRoute(parsedIP).split("\n")));
                // no response was got
                if (parsedTrRoutes.isEmpty()) {
                    output.write(("HTTP/1.1 500 Internal Server Error\nContent-Type: text/plain\n\nServer could not find a connection to target").getBytes());
                    output.close();
                    Thread.currentThread().interrupt();
                }
                StringBuilder sb = new StringBuilder();
                for (String string : parsedTrRoutes) {
                    Geoloc temp = IP.getGeo(string, geolocs);
                    if(temp == null){
                        continue;
                    }
                    sb.append(gson.toJson(temp)).append("\n");
                }
                output.write(("HTTP/1.1 200 Ok\nContent-Type: text/plain\n\n" + sb.toString()).getBytes());
//                String[] parsedTr = parseTraceRoute(traceRoute(parsedIP).split("\n"));
            } else {
                output.write(("HTTP/1.1 500 Internal Server Error\nContent-Type: text/plain\n\nServer could not find a connection to target").getBytes());
            }
//            output.write(("HTTP/1.1 200 Ok\nContent-Type: text/plain\n\n" + IP.getGeo("8.8.8.8", this.geolocs)).getBytes("UTF-8"));
            input.close();
            output.close();
            System.out.println("AND I RETURN");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String traceRoute(String ip) {
        StringBuilder sb = null;
        try {
            Process traceRoute;
            traceRoute = Runtime.getRuntime().exec("traceroute " + ip);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    traceRoute.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return sb.toString();
    }

    private String parseIP(StringBuilder sb) {
        String path = sb.substring(5);
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == ' ') {
                path = path.substring(0, i);
                break;
            }
        }
        System.err.println(path);
        if (path.matches("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)")) {
            return path;
        }
        return null;
    }

    private List<String> parseTraceRoute(String[] traceRoute) {
        List<String> matchedIPs = new ArrayList<>();
        Pattern regex = Pattern.compile("\\((.*?)\\)");
        for (int i = 1; i < traceRoute.length; i++) {
            System.out.println(traceRoute[i]);
            Matcher regexMatcher = regex.matcher(traceRoute[i]);
            if (regexMatcher.find()) {
                matchedIPs.add(regexMatcher.group(1));
            }
        }
        return matchedIPs;
    }

    private String getExternalIP() {
        BufferedReader in = null;
        String ip = "";
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            ip = in.readLine(); //you get the IP as a String
            in.close();
        } catch (IOException ex) {
            System.out.println("Malformed URL");
        }
        return ip;
    }
}
