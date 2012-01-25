package com.nate.simplehttpserver;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * An abstract implementation of the HttpMethodHandler interface that provides
 * a few utility methods for the various child handlers.
 *
 * @author Nate Buwalda
 */
public abstract class AbstractHttpMethodHandler implements HttpMethodHandler {

    protected final Configuration config = Configuration.getInstance();

    /**
     * A helper method for building HTTP response strings.
     *
     * @param responseCode
     * @param responseString
     * @return
     */
    protected String responseBuilder(String responseCode, String responseString) {
        StringBuilder outputString = new StringBuilder(responseCode);
        outputString.append("Server: SimpleHttpServer\r\n");
        outputString.append("Content-Type: text/html\r\n");
        outputString.append(String.format("Content-Length: %d\r\n", responseString.length()));
        outputString.append("Connection: close\r\n");
        outputString.append("\r\n");
        outputString.append(responseString);
        return outputString.toString();
    }

    /**
     * A helper method for reading a server resource to a String
     * @param requestedFile
     * @return
     * @throws IOException
     */
    protected String readResourceToString(File requestedFile) throws IOException {
        BufferedReader pageReader = new BufferedReader(new FileReader(requestedFile));
        String pageFileLine = pageReader.readLine();
        StringBuilder page = new StringBuilder();
        while (pageFileLine != null) {
            page.append(pageFileLine);
            pageFileLine = pageReader.readLine();
        }
        return page.toString();
    }
}
