package com.nate.simplehttpserver;

import java.io.IOException;

/**
 * Handler for any HTTP method the server currently doesn't support.
 *
 * @author Nate Buwalda
 */
public class UnsupportedHttpMethodHandler extends AbstractHttpMethodHandler {

    @Override
    public String handle(HttpRequest request) throws IOException {
        System.out.println("Handling an unsupported HTTP request.");
        String unknownHttpResponseString = "<b>The SimpleHTTPServer works! - but the requested HTTP method is not supported.</b>";
        return responseBuilder(HttpResponseConstants.HTTP_OK, unknownHttpResponseString);
    }
}
