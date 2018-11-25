package client;

import api.ApiPaths;
import api.OtherProductService;
import api.OtherProductService2;
import api.Product;
import io.cloudyrock.switcher.client.api.Client;
import io.cloudyrock.switcher.client.api.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = ApiPaths.ROOT_PATH + ApiPaths.OTHER_PRODUCT_2_PATH)
public class OtherProductService2Impl implements OtherProductService2 {

    private final ClientService clientService;

    public OtherProductService2Impl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    @GetMapping
    public Product getProduct() {
        final Client client = clientService.getClient();
        return new Product(client.getName(), "product - " + LocalDateTime.now().toString());
    }
}

