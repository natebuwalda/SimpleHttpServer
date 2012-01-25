package com.nate.simplehttpserver;

import java.util.HashMap;
import java.util.Map;

/**
 * A handy data transfer object for parsed HTTP requests
 *
 * @author Nate Buwalda
 */
public class HttpRequest {
    private String httpMethod;
    private String uri;
    private String host;
    private String userAgent;
    private String contentLength;
    private String contentType;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    /**
     * The basic constructor for a HttpRequest requires a parsed HTTP method, the
     * uri of the resource asked for, and then the entire HTTP request string itself
     *
     * @param httpMethod
     * @param uri
     * @param requestString
     */
    public HttpRequest(String httpMethod, String uri, String requestString) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.contentType = parseOptionalHeader("Content-Type", requestString);
        this.contentLength = parseOptionalHeader("Content-Length", requestString);
        this.host = parseOptionalHeader("Host", requestString);
        this.userAgent = parseOptionalHeader("User-Agent", requestString);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public String getHost() {
        return host.trim();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Parses the full request string for additional headers.
     *
     * @param headerType
     * @param requestString
     * @return
     */
    private String parseOptionalHeader(String headerType, String requestString) {
        String result = null;
        int headerTypeIndex = requestString.indexOf(headerType);
        if (headerTypeIndex > -1) {
            int indexOffset = headerType.length() + 2;
            result = requestString.substring(headerTypeIndex  + indexOffset, requestString.indexOf("\n", headerTypeIndex)).trim();
        }
        return result;
    }

}
