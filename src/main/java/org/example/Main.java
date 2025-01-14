package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.commons.ComponentInjectorConfig;
import org.example.rourter.ConnectionServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new ComponentInjectorConfig());
        ConnectionServer server = injector.getInstance(ConnectionServer.class);
        server.star();

    }
}