package com.nate.simplehttpserver;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ConnectionWorker {

    private final Configuration config = Configuration.getInstance();
    private final HttpHandlerFactory handlerFactory = HttpHandlerFactory.getInstance();

    public void handleConnection(Socket clientConnection) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            DataOutputStream output = new DataOutputStream(clientConnection.getOutputStream());

            StringBuilder headerBuilder = new StringBuilder();
            String headerLine = input.readLine();
            boolean continueReading = (headerLine != null && !headerLine.trim().isEmpty());
            while (continueReading) {
                headerBuilder.append(headerLine.trim() + "\n");
                headerLine = input.readLine();
                continueReading = (headerLine != null && !headerLine.trim().isEmpty());
            }
            String header = headerBuilder.toString();
            System.out.println(String.format("The header was: %s", header));
            StringTokenizer tokenizer = new StringTokenizer(header);

            try {
                String httpMethod = tokenizer.nextToken();
                String requestedResource = "";
                requestedResource = tokenizer.nextToken();

                HttpHandler handler = handlerFactory.createHandler(httpMethod);
                output.writeBytes(handler.handle(requestedResource));

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

}
