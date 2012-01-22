package com.nate.simplehttpserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private boolean isStarted = false;     
    private ServerSocket serverSocket;

    public boolean start() {
        System.out.println("The server is starting for host 127.0.0.1 on port 8080...");
        try {
            serverSocket = new ServerSocket(8080, 10, InetAddress.getByName("127.0.0.1"));
            run();
            isStarted = true;
        } catch (Exception e) {
            System.err.println("The server failed to start properly due to the following reason:");
            e.printStackTrace();
            isStarted = false;
        }
        return isStarted;
    }

    public boolean stop() {
        System.out.println("Server is now shutting down...");
        try {
            isStarted = false;
            serverSocket.close();
        } catch (Exception e) {
            System.err.println("The server failed to stop properly due to the following reason:");
            e.printStackTrace();
            isStarted = true;
        }
        return !isStarted;
    }

    private void run() throws IOException {
        while(isStarted) {
            Socket connection = serverSocket.accept();
        }
    }
}
