package com.nate.simplehttpserver;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractHttpMethodHandler implements HttpMethodHandler {

    protected final Configuration config = Configuration.getInstance();

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

    protected StringBuilder readResourceToString(File requestedFile) throws IOException {
        BufferedReader pageReader = new BufferedReader(new FileReader(requestedFile));
        String pageFileLine = pageReader.readLine();
        StringBuilder page = new StringBuilder();
        while (pageFileLine != null) {
            page.append(pageFileLine);
            pageFileLine = pageReader.readLine();
        }
        return page;
    }
}
