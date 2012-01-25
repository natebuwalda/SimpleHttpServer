package com.nate.simplehttpserver;

import java.io.IOException;

/**
 * Basic interface for handling HTTP requests by their method type
 *
 * @author Nate Buwalda
 */
public interface HttpMethodHandler {
    String handle(HttpRequest request) throws IOException;
}
