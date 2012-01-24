package com.nate.simplehttpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GetHttpMethodHandler extends AbstractHttpMethodHandler {

    private final Configuration config = Configuration.getInstance();

    @Override
    public String handle(String requestedResource) throws IOException {
        System.out.println("Handling a GET HTTP request.");

        String httpResponseCode;
        String httpGetResponse;

        if (requestedResource.equals("/")) {
            httpResponseCode = HttpResponseConstants.HTTP_OK;
            httpGetResponse = "<b>The SimpleHTTPServer works!</b>";
        } else {
            String pathName = config.getSiteBasedir() + requestedResource;
            File requestedFile = new File(pathName);
            if (requestedFile.exists()) {
                BufferedReader pageReader = new BufferedReader(new FileReader(requestedFile));
                String pageFileLine = pageReader.readLine();
                StringBuilder page = new StringBuilder();
                while (pageFileLine != null) {
                    page.append(pageFileLine);
                    pageFileLine = pageReader.readLine();
                }
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
