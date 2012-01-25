package com.nate.simplehttpserver;

/**
 * A barebones main class for the application
 *
 * @author Nate Buwalda
 */
public class SimpleHttpServerDriver {

    public static void main (String args[]) throws Exception {
        Server server = new Server();
        server.start();
    }
}
