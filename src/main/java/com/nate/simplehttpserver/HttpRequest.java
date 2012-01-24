package com.nate.simplehttpserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String httpMethod;
    private String uri;
    private String host;
    private String userAgent;
    private String contentLength;
    private String contentType;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public HttpRequest(String httpMethod, String uri, String header) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.contentType = parseOptionalHeader("Content-Type", header);
        this.contentLength = parseOptionalHeader("Content-Length", header);
        this.host = parseOptionalHeader("Host", header);
        this.userAgent = parseOptionalHeader("User-Agent", header);
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

    private String parseOptionalHeader(String headerType, String header) {
        String result = null;
        int headerTypeIndex = header.indexOf(headerType);
        if (headerTypeIndex > -1) {
            int indexOffset = headerType.length() + 2;
            result = header.substring(headerTypeIndex  + indexOffset, header.indexOf("\n", headerTypeIndex)).trim();
        }
        return result;
    }

}
