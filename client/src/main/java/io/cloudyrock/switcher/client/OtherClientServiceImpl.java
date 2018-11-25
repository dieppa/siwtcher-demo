package io.cloudyrock.switcher.client;

import io.cloudyrock.switcher.client.api.ApiPaths;
import io.cloudyrock.switcher.client.api.Client;
import io.cloudyrock.switcher.client.api.ClientService;
import io.cloudyrock.switcher.client.api.OtherClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = ApiPaths.ROOT_PATH + ApiPaths.OTHER_CLIENT_PATH)
public class OtherClientServiceImpl implements OtherClientService {

    private final ClientRepository clientRepository;

    public OtherClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @GetMapping
    public Client getClient() {
        return new Client("Client-" + LocalDateTime.now().toString(), "Surname");
    }
}

