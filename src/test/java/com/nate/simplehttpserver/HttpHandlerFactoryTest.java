package com.nate.simplehttpserver;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HttpHandlerFactoryTest {
    
    private HttpHandlerFactory factory = HttpHandlerFactory.getInstance();
    
    @Test
    public void handleGet() {
        HttpHandler handler = factory.createHandler("GET");
        assertNotNull(handler);
        assertTrue(handler instanceof GetHttpHandler);
    }

    @Test
    public void handleUnknown() {
        HttpHandler handler = factory.createHandler("GARBAGE");
        assertNotNull(handler);
        assertTrue(handler instanceof UnsupportedHttpHandler);
    }
}
