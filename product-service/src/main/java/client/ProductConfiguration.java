package client;

import api.ProductService;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.cloudyrock.switcher.api.util.ServiceApi;
import io.cloudyrock.switcher.api.util.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

@Configuration
public class ProductConfiguration extends ServiceConfiguration {


    private static final String SERVICE_NAME = "product";

    public ProductConfiguration() {
        super(SERVICE_NAME, ProductServiceImpl.class);
    }

    @Bean
    public ProductService getProductService() {
        return (ProductService) getService(SERVICE_NAME, ProductServiceImpl.class);
    }

}
