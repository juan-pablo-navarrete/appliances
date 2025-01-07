package org.example.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.multibindings.Multibinder;
import org.example.dao.ApplianceCsvDAOImpl;
import org.example.dao.ApplianceDAO;
import org.example.dao.ApplianceMySqlDAOImpl;
import org.example.repository.ApplianceRepository;
import org.example.rourter.ApplianceHandler;
import org.example.rourter.ApplianceRouterTCP;
import org.example.service.ApplianceService;
import org.example.service.ApplianceServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;

public class ComponentInjectorConfig extends AbstractModule {
    @Override
    protected void configure(){
        Multibinder<ApplianceDAO> binderSet = Multibinder.newSetBinder(binder(), ApplianceDAO.class);
        binderSet.addBinding().to(ApplianceCsvDAOImpl.class);
        binderSet.addBinding().to(ApplianceMySqlDAOImpl.class);

        bind(ApplianceRepository.class);
        bind(ApplianceService.class).to(ApplianceServiceImpl.class);
        bind(ObjectMapper.class);
        bind(ApplianceRouterTCP.class);
        bind(ServerSocket.class).toProvider(ServerSocketProvider.class);
        bind(ApplianceHandler.class);
    }
    static class ServerSocketProvider implements Provider<ServerSocket> {
        private final PropertiesReader propertiesReader;

        @Inject
        public ServerSocketProvider(PropertiesReader propertiesReader) {
            this.propertiesReader = propertiesReader;
        }

        @Override
        public ServerSocket get() {
            String portString = propertiesReader.getProperty("application.port");
            if (portString == null || portString.isEmpty()) {
                throw new IllegalArgumentException("The property 'application.port' is not set or is empty.");
            }

            int port;
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid port value in 'application.port': " + portString, e);
            }

            try {
                return new ServerSocket(port);
            } catch (IOException exception) {
                throw new RuntimeException("Error creating socket: " + exception.getMessage(), exception);
            }
        }

    }
}
