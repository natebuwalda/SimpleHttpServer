package com.nate.simplehttpserver;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Configuration instance = null;
    private Properties properties = new Properties();

    protected Configuration() {
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("env.properties"));
        } catch (IOException e) {
            System.err.println("Unable to load system properties:");
            e.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getServerHost() {
        return (String) properties.get("server.host");
    }

    public Integer getServerPort() {
        return Integer.parseInt((String)properties.get("server.port"));
    }

    public String getSiteBasedir() {
        return (String) properties.get("server.site.basedir");
    }
}
