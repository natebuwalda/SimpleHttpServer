package com.nate.simplehttpserver;

import java.io.File;
import java.io.IOException;

public class PostHttpMethodHandler extends AbstractHttpMethodHandler {

    @Override
    public String handle(HttpRequest request) throws IOException {
        System.out.println("Handling a POST HTTP request.");

        String httpResponseCode;
        String httpPostResponse;
        String pathName = config.getSiteBasedir() + request.getUri();
        File requestedFile = new File(pathName);
        if (requestedFile.exists()) {
            StringBuilder page = readResourceToString(requestedFile);
            httpResponseCode = HttpResponseConstants.HTTP_OK;
            httpPostResponse = page.toString();
        } else {
            httpResponseCode = HttpResponseConstants.HTTP_NOTFOUND;
            httpPostResponse = "<b>SimpleHTTPServer could not find the page you were looking for (404)</b>";
        }

        return responseBuilder(httpResponseCode, httpPostResponse);
    }

}
