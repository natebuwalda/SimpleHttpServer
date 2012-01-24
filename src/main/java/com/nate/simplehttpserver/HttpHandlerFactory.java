package com.nate.simplehttpserver;

public class HttpHandlerFactory {
    
    private static HttpHandlerFactory instance = null;
    
    protected HttpHandlerFactory() {
        //do nothing, just a singleton constructor    
    }

    public static HttpHandlerFactory getInstance() {
        if (instance == null) {
            instance = new HttpHandlerFactory();
        }
        return instance;
    }

    public HttpHandler createHandler(String httpMethod) {
        HttpHandler handler = null;
        if ("GET".equals(httpMethod)) {
            handler = new GetHttpHandler();    
        } else {
            handler = new UnsupportedHttpHandler();
        }
        return handler;
    }
}
