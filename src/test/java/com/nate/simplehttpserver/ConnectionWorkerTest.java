package com.nate.simplehttpserver;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
    This test makes use of the stubbing library Mockito in order to isolate
    the particular behaviors I would like to test for the ConnectionWorker
    class.
 */

public class ConnectionWorkerTest {

    private ConnectionWorker worker = new ConnectionWorker();

    @Test
    public void handleGet() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //stubbing behavior
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GET".getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        worker.handleConnection(mockSocket);

        //assertions
        String results = outputStream.toString();
        assertEquals("HTTP/1.1 200 OK\r\n" +
                "Server: SimpleHttpServer\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 34\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                "<b>The SimpleHTTPServer works!</b>", results);

        outputStream.close();
        outputStream.flush();
    }

    @Test
    public void handleUnknownHttpMethod() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //stubbing behavior
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GARBAGE".getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        worker.handleConnection(mockSocket);

        //assertions
        String results = outputStream.toString();
        assertEquals("HTTP/1.1 200 OK\r\n" +
                "Server: SimpleHttpServer\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 84\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                "<b>The SimpleHTTPServer works! - but the requested HTTP method is not supported.</b>", results);

        outputStream.close();
        outputStream.flush();
    }

}
