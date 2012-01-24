package com.nate.simplehttpserver;

import java.io.IOException;

public class UnsupportedHttpHandler extends HttpHandler {

    @Override
    public String handle(String requestedResource) throws IOException {
        System.out.println("Handling an unsupported HTTP request.");
        String unknownHttpResponseString = "<b>The SimpleHTTPServer works! - but the requested HTTP method is not supported.</b>";
        return responseBuilder(HttpResponseConstants.HTTP_OK, unknownHttpResponseString);
    }
}
