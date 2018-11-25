package io.cloudyrock.switcher.client;

import io.cloudyrock.switcher.api.util.ServiceConfiguration;
import io.cloudyrock.switcher.client.api.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration  extends ServiceConfiguration {

    public ClientConfiguration() {
        super("client");
    }

    @Bean
    public ClientService getClientService() {
        return (ClientService) getService(ClientServiceImpl.class);
    }

    @Bean
    public ClientRepository getClientRepository() {
        return new ClientRepository();
    }

}
