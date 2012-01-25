package com.nate.simplehttpserver;

/**
 * A singleton factory for creating different HttpMethodHandlers
 *
 * @author Nate Buwalda
 */
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

    /**
     * Determine what kind of handler to make based off the specified HTTP method
     *
     * @param httpMethod
     * @return
     */
    public HttpMethodHandler createHandler(String httpMethod) {
        HttpMethodHandler handler = null;
        if ("GET".equals(httpMethod)) {
            handler = new GetHttpMethodHandler();
        } else if ("POST".equals(httpMethod)) {
            handler = new PostHttpMethodHandler();
        } else {
            handler = new UnsupportedHttpMethodHandler();
        }
        return handler;
    }
}
