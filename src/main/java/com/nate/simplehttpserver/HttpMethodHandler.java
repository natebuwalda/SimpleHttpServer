package com.nate.simplehttpserver;

import java.io.IOException;

public interface HttpMethodHandler {
    String handle(String requestedResource) throws IOException;
}
