package org.example.rourter;

import com.google.inject.Inject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionServer {
    private final ApplianceRouterTCP applianceRouterTCP;
    private final ServerSocket serverSocket;

    @Inject
    public ConnectionServer(ApplianceRouterTCP applianceRouterTCP, ServerSocket serverSocket) {
        this.applianceRouterTCP = applianceRouterTCP;
        this.serverSocket = serverSocket;
    }

    public void star() throws IOException {
        Socket socket;
        while (true) {
            socket = serverSocket.accept();
            applianceRouterTCP.setSocket(socket);
            applianceRouterTCP.start();
        }
    }
}
