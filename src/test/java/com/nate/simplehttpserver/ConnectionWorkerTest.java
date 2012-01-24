package com.nate.simplehttpserver;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

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
    public void handleGetOK() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //stubbing behavior
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GET / HTTP/1.1".getBytes()));
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
    public void handleGetNotFound() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //stubbing behavior
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GET /noexist.html HTTP/1.1".getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        worker.handleConnection(mockSocket);

        //assertions
        String results = outputStream.toString();
        assertEquals("HTTP/1.1 404 Not Found\r\n" +
                "Server: SimpleHttpServer\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: 74\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                "<b>SimpleHTTPServer could not find the page you were looking for (404)</b>", results);

        outputStream.close();
        outputStream.flush();
    }

    @Test
    public void handleGetFound() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //stubbing behavior
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GET /index.html HTTP/1.1".getBytes()));
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        worker.handleConnection(mockSocket);

        //assertions (leaving out the full results because it is large)
        String results = outputStream.toString();
        StringTokenizer tokenizer = new StringTokenizer(results, "\r\n");
        String httpCode = tokenizer.nextToken();
        assertEquals("HTTP/1.1 200 OK", httpCode);
        assertEquals(298, results.length());

        outputStream.close();
        outputStream.flush();
    }

    @Test
    public void handleUnknownHttpMethod() throws Exception {
        Socket mockSocket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //stubbing behavior
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream("GARBAGE / HTTP/1.1".getBytes()));
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
