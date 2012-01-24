package com.nate.simplehttpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ConnectionWorker {

    private final HttpHandlerFactory handlerFactory = HttpHandlerFactory.getInstance();

    public void handleConnection(Socket clientConnection) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            DataOutputStream output = new DataOutputStream(clientConnection.getOutputStream());

            StringBuilder headerBuilder = new StringBuilder();
            String headerLine = input.readLine();
            boolean continueReading = shouldContinueReading(headerLine);
            while (continueReading) {
                headerBuilder.append(headerLine.trim()).append("\n");
                headerLine = input.readLine();
                continueReading = shouldContinueReading(headerLine);
            }
            String header = headerBuilder.toString();
            System.out.println(String.format("The header was: %s", header));

            try {
                StringTokenizer httpMethodLineTokenizer = new StringTokenizer(header);
                String httpMethod = httpMethodLineTokenizer.nextToken();
                String requestedResource = httpMethodLineTokenizer.nextToken();

                HttpMethodHandler handler = handlerFactory.createHandler(httpMethod);
                HttpRequest request = new HttpRequest(httpMethod, requestedResource, header);

                if (request.getContentLength() != null && Integer.parseInt(request.getContentLength()) > 0) {
                    int requestBodyLength = Integer.parseInt(request.getContentLength());
                    char[] requestBodyBuffer = new char[requestBodyLength];
                    input.read(requestBodyBuffer);
                    String requestBody = new String(requestBodyBuffer);
                    System.out.println(String.format("Request body: %s", requestBody));

                    String[] splitParams = requestBody.split("&");
                    for (String params : splitParams) {
                        String[] keyValue = params.split("=");
                        request.addParameter(keyValue[0], keyValue[1]);
                    }
                }
                
                output.writeBytes(handler.handle(request));

            } catch (NoSuchElementException nsee) {
                System.err.println(String.format("Received an incomplete request: %s", headerBuilder));
                throw nsee;
            }

            input.close();
            output.close();
            output.flush();
        } catch (Exception e) {
            System.out.println("The following error occured processing a client connection:");
            e.printStackTrace();
        }
    }

    private boolean shouldContinueReading(String headerLine) {
        return (headerLine != null && !headerLine.trim().isEmpty());
    }

}
