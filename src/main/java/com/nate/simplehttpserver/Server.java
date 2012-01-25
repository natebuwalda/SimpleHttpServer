package com.nate.simplehttpserver;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The root class for all server work.  Maintains a thread pool for all the various worker runnables
 * and a server socket for listening for new client connections.
 * 
 * @author Nate Buwalda
 */
public class Server {

    private boolean isStarted = false;     
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor;
    private final Configuration config = Configuration.getInstance();

    /**
     * Starts the server with the specified configuration.
     * 
     * @return
     */
    public boolean start() {
        System.out.println(String.format("The server is starting for host %s on port %s...", config.getServerHost(), config.getServerPort()));
        try {
            serverSocket = new ServerSocket(config.getServerPort(), 10, InetAddress.getByName(config.getServerHost()));
            threadPoolExecutor = new ThreadPoolExecutor(10, 20, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
            threadPoolExecutor.execute(new ServerWorkerWrapper());
            isStarted = true;
        } catch (Exception e) {
            System.err.println("The server failed to start properly due to the following reason:");
            e.printStackTrace();
            isStarted = false;
        }
        return isStarted;
    }

    /**
     * Attempts to stop the server.
     * 
     * @return
     */
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

    /**
     * Runnable wrapper class for {@link ServerWorker}.
     */
    private class ServerWorkerWrapper implements Runnable {
        @Override
        public void run() {
            ServerWorker worker = new ServerWorker();
            worker.setServerSocket(serverSocket);
            worker.setThreadPoolExecutor(threadPoolExecutor);

            System.out.println("Now accepting new connections.");
            while (isStarted) {
               worker.acceptConnection();
            }
        }
    }

}
