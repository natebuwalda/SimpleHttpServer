package com.nate.simplehttpserver;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServerTest {
    
    private Server testServer = new Server();

    @Test
    public void startAndStopServer() throws InterruptedException {
        boolean started = testServer.start();
        assertTrue("Server should be started", started);

        Thread.sleep(1000);
        
        boolean stopped = testServer.stop();
        assertTrue("Server should be stopped", stopped);
    }
    
}
