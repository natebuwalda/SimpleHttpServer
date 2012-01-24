package com.nate.simplehttpserver;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConfigurationTest {

    private Configuration config = Configuration.getInstance();

    @Test
    public void mandatoryPropertiesNotNull() {
        assertNotNull(config.getServerHost());
        assertNotNull(config.getServerPort());
        assertNotNull(config.getSiteBasedir());
    }
}
