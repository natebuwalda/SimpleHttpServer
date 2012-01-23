package com.nate.simplehttpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ConnectionWorker {
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
        String httpMethod = tokenizer.nextToken();

        if (httpMethod.equals("GET")) {
            System.out.println("Handling a GET HTTP request.");
            String httpGetResponseString = "<b>The SimpleHTTPServer works!</b>";
            output.writeBytes(responseBuilder("HTTP/1.1 200 OK\r\n", httpGetResponseString));
        } else {
            System.out.println("Handling an unsupported HTTP request.");
            String unknownHttpResponseString = "<b>The SimpleHTTPServer works! - but the requested HTTP method is not supported.</b>";
            responseBuilder("HTTP/1.1 200 OK\r\n", unknownHttpResponseString);
            output.writeBytes(responseBuilder("HTTP/1.1 200 OK\r\n", unknownHttpResponseString));
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
