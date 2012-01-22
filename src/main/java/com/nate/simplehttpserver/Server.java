package com.nate.simplehttpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
            System.out.println("Now accepting new connections.");
            while(isStarted) {
                try {
                    Socket connection = serverSocket.accept();
                    threadPoolExecutor.execute(new ConnectionWorker(connection));
                } catch (Exception e) {
                    System.err.println("The following error occured while the server was running:");
                    e.printStackTrace();
                }
            }
        }
    }
    
    private class ConnectionWorker implements Runnable {
        private Socket clientConnection;

        private ConnectionWorker(Socket connection) {
            clientConnection = connection;
        }

        @Override
        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
                DataOutputStream output = new DataOutputStream(clientConnection.getOutputStream());

                String header = input.readLine();
                StringTokenizer tokenizer = new StringTokenizer(header);
                String httpMethod = tokenizer.nextToken();

                StringBuilder outputString = new StringBuilder("HTTP/1.1 200 OK\r\n");
                if (httpMethod.equals("GET")) {
                    String httpGetResponseString = "<b>The SimpleHTTPServer works!</b>";
                    outputString.append("Server: SimpleHttpServer\r\n");
                    outputString.append("Content-Type: text/html\r\n");
                    outputString.append(String.format("Content-Length: %d\r\n", httpGetResponseString.length()));
                    outputString.append("Connection: close\r\n");
                    outputString.append("\r\n");
                    outputString.append(httpGetResponseString);
                    output.writeBytes(outputString.toString());
                } else {
                    String httpGetResponseString = "<b>The SimpleHTTPServer works! - but the requested HTTP method is not supported.</b>";
                    outputString.append("Server: SimpleHttpServer\r\n");
                    outputString.append("Content-Type: text/html\r\n");
                    outputString.append(String.format("Content-Length: %d\r\n", httpGetResponseString.length()));
                    outputString.append("Connection: close\r\n");
                    outputString.append("\r\n");
                    outputString.append(httpGetResponseString);
                    output.writeBytes(outputString.toString());
                }
                input.close();
                output.close();
                output.flush();
            } catch (Exception e) {
                System.out.println("The following error occured processing a client connection:");
                e.printStackTrace();
            }
        }
    }
}
