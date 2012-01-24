package com.nate.simplehttpserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpRequestTest {
    
    @Test
    public void constructor(){
        String fullHeader = "GET /index.html HTTP/1.1 \n" +
                "Host: 127.0.0.1:8080 \n" +
                "Connection: keep-alive \n" +
                "Content-Length: 17 \n" +
                "User-Agent: Mozilla/5.0 \n" +
                "Content-Type: application/xml \n" +
                "Accept: */* \n" +
                "Accept-Encoding: gzip,deflate,sdch \n" +
                "Accept-Language: en-US,en;q=0.8 \n" +
                "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3";
        HttpRequest request = new HttpRequest("GET", "/index.html", fullHeader);

        assertEquals("GET", request.getHttpMethod());
        assertEquals("/index.html", request.getUri());
        assertEquals("127.0.0.1:8080", request.getHost());
        assertEquals("17", request.getContentLength());
        assertEquals("application/xml", request.getContentType());
        assertEquals("Mozilla/5.0", request.getUserAgent());
    }
}
