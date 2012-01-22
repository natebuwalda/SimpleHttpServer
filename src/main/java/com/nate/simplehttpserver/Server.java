package com.nate.simplehttpserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    private boolean isStarted = false;     
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor;

    public boolean start() {
        System.out.println("The server is starting for host 127.0.0.1 on port 8080...");
        try {
            serverSocket = new ServerSocket(8080, 10, InetAddress.getByName("127.0.0.1"));
            BlockingQueue<Runnable> connectionQueue = new LinkedBlockingQueue<Runnable>();
            threadPoolExecutor = new ThreadPoolExecutor(10, 20, 1000, TimeUnit.MILLISECONDS, connectionQueue);
            threadPoolExecutor.execute(new ServerWorker());
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
            threadPoolExecutor.shutdown();
        } catch (Exception e) {
            System.err.println("The server failed to stop properly due to the following reason:");
            e.printStackTrace();
            isStarted = true;
        }
        return !isStarted;
    }
    
    private class ServerWorker implements Runnable {
        @Override
        public void run() {
            while(isStarted) {
                try {
                    System.out.println("Now accepting new connections.");
                    Socket connection = serverSocket.accept();
                } catch (Exception e) {
                    System.err.println("The following error occured while the server was running:");
                    e.printStackTrace();
                }
            }
        }
    }
}
