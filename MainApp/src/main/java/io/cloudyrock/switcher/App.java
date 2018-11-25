package io.cloudyrock.switcher;


import client.ProductConfiguration;
import io.cloudyrock.switcher.client.ClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplicationWithoutScan
@Import({ClientConfiguration.class, ProductConfiguration.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

