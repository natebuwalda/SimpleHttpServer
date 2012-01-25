package com.nate.simplehttpserver;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Handler for POST HTTP requests.
 *
 * @author Nate Buwalda
 */
public class PostHttpMethodHandler extends AbstractHttpMethodHandler {

    /**
     * Responds to the client based on the uri in the request.  Will try to stream the request
     * resource as a String.  Will also output any parsed contents of the request body to stdout.
     *
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public String handle(HttpRequest request) throws IOException {
        System.out.println("Handling a POST HTTP request.");

        String httpResponseCode;
        String httpPostResponse;
        String pathName = config.getSiteBasedir() + request.getUri();
        File requestedFile = new File(pathName);
        if (requestedFile.exists()) {
            //do something with the parameters
            System.out.println("POST parameters");
            for (Map.Entry entry : request.getParameters().entrySet()) {
                System.out.println(String.format("Key: %s, Value: %s", entry.getKey(), entry.getValue()));
            }

            httpResponseCode = HttpResponseConstants.HTTP_OK;
            httpPostResponse = readResourceToString(requestedFile);
        } else {
            httpResponseCode = HttpResponseConstants.HTTP_NOTFOUND;
            httpPostResponse = "<b>SimpleHTTPServer could not find the page you were looking for (404)</b>";
        }

        return responseBuilder(httpResponseCode, httpPostResponse);
    }

}
