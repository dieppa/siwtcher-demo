package io.cloudyrock.switcher.client;

import io.cloudyrock.switcher.client.api.ApiPaths;
import io.cloudyrock.switcher.client.api.Client;
import io.cloudyrock.switcher.client.api.ClientService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = ApiPaths.ROOT_PATH + ApiPaths.CLIENT_PATH)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @GetMapping
    public Client getClient() {
        return new Client("Client-" + LocalDateTime.now().toString(), "Surname");
    }
}

