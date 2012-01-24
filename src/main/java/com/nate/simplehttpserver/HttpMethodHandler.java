package com.nate.simplehttpserver;

import java.io.IOException;

public interface HttpMethodHandler {
    String handle(HttpRequest request) throws IOException;
}
