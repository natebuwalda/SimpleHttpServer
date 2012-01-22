package com.nate.simplehttpserver;

public class SimpleHttpServerDriver {

    public static void main (String args[]) throws Exception {
        Server server = new Server();
        server.start();
    }
}
