package com.nate.simplehttpserver;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ConnectionWorker {

    private final String HTTP_OK = "HTTP/1.1 200 OK\r\n";
    private final String HTTP_NOTFOUND = "HTTP/1.1 404 Not Found\r\n";

    public void handleConnection(Socket clientConnection) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            DataOutputStream output = new DataOutputStream(clientConnection.getOutputStream());

            routeRequest(input, output);

            input.close();
            output.close();
            output.flush();
        } catch (Exception e) {
            System.out.println("The following error occured processing a client connection:");
            e.printStackTrace();
        }
    }

    protected void routeRequest(BufferedReader input, DataOutputStream output) throws IOException {
        String header = input.readLine();
        System.out.println(String.format("The header was: %s", header));
        StringTokenizer tokenizer = new StringTokenizer(header);

        try {
            String httpMethod = tokenizer.nextToken();
            String requestedResource = "";
            requestedResource = tokenizer.nextToken();
            if (httpMethod.equals("GET")) {
                System.out.println("Handling a GET HTTP request.");
                String httpResponseCode = null;
                String httpGetResponse = null;
                if (requestedResource.equals("/")) {
                    httpResponseCode = HTTP_OK;
                    httpGetResponse = "<b>The SimpleHTTPServer works!</b>";
                } else {
                    File requestedFile = new File(String.format("/resources%s", requestedResource));
                    if (requestedFile.exists()) {
                        
                    } else {
                        httpResponseCode = HTTP_NOTFOUND;
                        httpGetResponse = "<b>SimpleHTTPServer could not find the page you were looking for (404)</b>";
                    }
                }
                output.writeBytes(responseBuilder(httpResponseCode, httpGetResponse));
            } else {
                System.out.println("Handling an unsupported HTTP request.");
                String unknownHttpResponseString = "<b>The SimpleHTTPServer works! - but the requested HTTP method is not supported.</b>";
                output.writeBytes(responseBuilder(HTTP_OK, unknownHttpResponseString));
            }
        } catch (NoSuchElementException nsee){
            System.err.println("Received an incomplete request.");
            output.writeBytes(responseBuilder(HTTP_NOTFOUND, "<b>Invalid request</b>"));
        }
    }

    private String responseBuilder(String responseCode, String responseString) {
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
