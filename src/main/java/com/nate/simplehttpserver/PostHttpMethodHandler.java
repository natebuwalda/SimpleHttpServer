package com.nate.simplehttpserver;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
            
            //do something with the parameters
            System.out.println("POST parameters");
            for (Map.Entry entry : request.getParameters().entrySet()) {
                System.out.println(String.format("Key: %s, Value: %s", entry.getKey(), entry.getValue()));
            }
            
            httpPostResponse = page.toString();
        } else {
            httpResponseCode = HttpResponseConstants.HTTP_NOTFOUND;
            httpPostResponse = "<b>SimpleHTTPServer could not find the page you were looking for (404)</b>";
        }

        return responseBuilder(httpResponseCode, httpPostResponse);
    }

}
