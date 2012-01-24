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

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpMethod='" + httpMethod + '\'' +
                ", uri='" + uri + '\'' +
                ", host='" + host + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", contentLength='" + contentLength + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
