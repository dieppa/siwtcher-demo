package io.cloudyrock.switcher.client;

import io.cloudyrock.switcher.api.util.ServiceConfiguration;
import io.cloudyrock.switcher.client.api.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration  extends ServiceConfiguration {

    private static final String SERVICE_NAME = "client";

    public ClientConfiguration() {
        super(SERVICE_NAME, ClientServiceImpl.class);
    }

    @Bean
    public ClientService getClientService() {
        return (ClientService) getServiceDefault();
    }

    @Bean
    public ClientRepository getClientRepository() {
        return new ClientRepository();
    }

}
