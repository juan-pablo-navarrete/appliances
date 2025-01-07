package org.example.rourter;

import com.google.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApplianceRouterTCP extends Thread {
    private final ApplianceHandler applianceHandler;
    private Socket socket;

    @Inject
    public ApplianceRouterTCP(ApplianceHandler applianceHandler) {
        this.applianceHandler = applianceHandler;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String endpoint = inputReader.readLine();
            if (endpoint.matches("^appliances/\\d{3}$")) {
                String applianceid = endpoint.split("/")[1].trim();
                applianceHandler.findById(Integer.parseInt(applianceid), outputWriter);
            }

            if (endpoint.equals("appliances/")) {
                applianceHandler.findAll(outputWriter);
            }
            if (endpoint.matches("^appliances/\\d{3}/delete$")) {
                String applianceid = endpoint.split("/")[1].trim();
                applianceHandler.deleteById(Integer.parseInt(applianceid), outputWriter);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Operation not found: " + exception.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (Exception exception) {
                throw new RuntimeException("Error closing tcp connection: " + exception.getMessage());
            }
        }
    }
}
