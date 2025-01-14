package org.example.rourter;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionServer {
    private final Provider<ApplianceRouterTCP> applianceRouterTCP;
    private final ServerSocket serverSocket;

    @Inject
    public ConnectionServer(Provider<ApplianceRouterTCP> applianceRouterTCP, ServerSocket serverSocket) {
        this.applianceRouterTCP = applianceRouterTCP;
        this.serverSocket = serverSocket;
    }

    public void star() throws IOException {
        Socket socket;
        while (true) {
            socket = serverSocket.accept();
            ApplianceRouterTCP router = applianceRouterTCP.get();
            router.setSocket(socket);
            router.start();
        }
    }
}
