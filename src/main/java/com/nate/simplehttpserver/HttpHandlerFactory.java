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

    public AbstractHttpMethodHandler createHandler(String httpMethod) {
        AbstractHttpMethodHandler handler = null;
        if ("GET".equals(httpMethod)) {
            handler = new GetHttpMethodHandler();
        } else {
            handler = new UnsupportedHttpMethodHandler();
        }
        return handler;
    }
}
