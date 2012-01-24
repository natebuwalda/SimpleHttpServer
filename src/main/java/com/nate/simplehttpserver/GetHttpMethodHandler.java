package com.nate.simplehttpserver;

import java.io.File;
import java.io.IOException;

public class GetHttpMethodHandler extends AbstractHttpMethodHandler {

    @Override
    public String handle(HttpRequest request) throws IOException {
        System.out.println("Handling a GET HTTP request.");

        String httpResponseCode;
        String httpGetResponse;

        if ("/".equals(request.getUri())) {
            httpResponseCode = HttpResponseConstants.HTTP_OK;
            httpGetResponse = "<b>The SimpleHTTPServer works!</b>";
        } else {
            String pathName = config.getSiteBasedir() + request.getUri();
            File requestedFile = new File(pathName);
            if (requestedFile.exists()) {
                StringBuilder page = readResourceToString(requestedFile);
                httpResponseCode = HttpResponseConstants.HTTP_OK;
                httpGetResponse = page.toString();
            } else {
                httpResponseCode = HttpResponseConstants.HTTP_NOTFOUND;
                httpGetResponse = "<b>SimpleHTTPServer could not find the page you were looking for (404)</b>";
            }
        }
        return responseBuilder(httpResponseCode, httpGetResponse);
    }
}
