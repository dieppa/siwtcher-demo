package io.cloudyrock.switcher.client;

import io.cloudyrock.switcher.api.util.ServiceConfiguration;
import io.cloudyrock.switcher.client.api.ClientService;
import io.cloudyrock.switcher.client.api.OtherClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration  extends ServiceConfiguration {

    private static final String SERVICE_NAME = "client";

    public ClientConfiguration() {
        //Configuration initialized with default service.
        //getServiceDefault() can be called without passing parameters.
        //Also getting specific service passing no default service is possible
        super(SERVICE_NAME, ClientServiceImpl.class);
    }

    @Bean
    public ClientService getClientService() {
        //getting service with default service
        return (ClientService) getServiceDefault();
    }

    @Bean
    public OtherClientService getOtherClientService() {
        //getting service with specific service
        return (OtherClientService) getService("other-client", OtherClientServiceImpl.class);
    }

    @Bean
    public ClientRepository getClientRepository() {
        return new ClientRepository();
    }

}
