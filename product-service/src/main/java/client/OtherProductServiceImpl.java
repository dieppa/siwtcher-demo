package client;

import api.ApiPaths;
import api.OtherProductService;
import api.Product;
import api.ProductService;
import io.cloudyrock.switcher.client.api.Client;
import io.cloudyrock.switcher.client.api.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = ApiPaths.ROOT_PATH + ApiPaths.OTHER_PRODUCT_PATH)
public class OtherProductServiceImpl implements OtherProductService {

    private final ClientService clientService;

    public OtherProductServiceImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    @GetMapping
    public Product getProduct() {
        final Client client = clientService.getClient();
        return new Product(client.getName(), "product - " + LocalDateTime.now().toString());
    }
}

