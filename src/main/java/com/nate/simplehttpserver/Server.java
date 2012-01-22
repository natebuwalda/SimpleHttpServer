package com.nate.simplehttpserver;

public class Server {

    private boolean isStarted = false;

    public boolean start() {
        System.out.println("The server is starting for host 127.0.0.1 on port 8080...");
        isStarted = true;
        return isStarted;
    }

    public boolean stop() {
        System.out.println("Server is now shutting down...");
        isStarted = false;
        return !isStarted;
    }
}
