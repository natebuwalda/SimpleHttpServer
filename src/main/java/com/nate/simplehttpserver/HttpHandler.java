package com.nate.simplehttpserver;


import java.io.IOException;

public abstract class HttpHandler {
    public abstract String handle(String requestedResource) throws IOException;

    protected String responseBuilder(String responseCode, String responseString) {
        StringBuilder outputString = new StringBuilder(responseCode);
        outputString.append("Server: SimpleHttpServer\r\n");
        outputString.append("Content-Type: text/html\r\n");
        outputString.append(String.format("Content-Length: %d\r\n", responseString.length()));
        outputString.append("Connection: close\r\n");
        outputString.append("\r\n");
        outputString.append(responseString);
        return outputString.toString();
    }
}
