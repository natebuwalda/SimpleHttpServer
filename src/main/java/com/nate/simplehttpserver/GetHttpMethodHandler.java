package com.nate.simplehttpserver;

import java.io.File;
import java.io.IOException;

/**
 * Handler for GET HTTP requests.
 *
 * @author
 */
public class GetHttpMethodHandler extends AbstractHttpMethodHandler {

    /**
     * Responds to the client based on the uri in the request.  Will try to stream the request
     * resource as a String.
     *
     * @param request
     * @return
     * @throws IOException
     */
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
                httpResponseCode = HttpResponseConstants.HTTP_OK;
                httpGetResponse = readResourceToString(requestedFile);
            } else {
                httpResponseCode = HttpResponseConstants.HTTP_NOTFOUND;
                httpGetResponse = "<b>SimpleHTTPServer could not find the page you were looking for (404)</b>";
            }
        }
        return responseBuilder(httpResponseCode, httpGetResponse);
    }
}
