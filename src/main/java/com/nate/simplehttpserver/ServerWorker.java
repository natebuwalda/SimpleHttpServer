package com.nate.simplehttpserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Worker for handling the server socket.  It will listen and accept new client connections, create a
 * new socket for that connection, then create a connection worker that is then passed to the thread
 * pool executor for work.
 * 
 * @author Nate Buwalda
 */
public class ServerWorker {

    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * Listens for new client connections
     */
    public void acceptConnection() {
        try {
            Socket connection = serverSocket.accept();
            ConnectionWorkerWrapper connectionWorker = new ConnectionWorkerWrapper(connection);
            threadPoolExecutor.execute(connectionWorker);
        } catch (Exception e) {
            System.err.println("The following error occured while the server was running:");
            e.printStackTrace();
        }
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    /**
     * Runnable wrapper class for the {@link ConnectionWorker}
     */
    private class ConnectionWorkerWrapper implements Runnable {
        private Socket clientConnection;

        private ConnectionWorkerWrapper(Socket clientConnection) {
            this.clientConnection = clientConnection;
        }

        @Override
        public void run() {
            ConnectionWorker worker = new ConnectionWorker();
            worker.handleConnection(clientConnection);
        }
    }
}
