package client;

import api.OtherProductService;
import api.OtherProductService2;
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
    private static final String OTHER_SERVICE_NAME = "other-product";
    private static final String OTHER_SERVICE_2_NAME = "other-product-2";

    public ProductConfiguration() {
        //initializing config without default service, it will be passed afterward
        super();
    }

    @Bean
    public ProductService getProductService() {
        //serviceName and class is needed as they weren't passed at construction time
        return (ProductService) getService(SERVICE_NAME, ProductServiceImpl.class);
    }

    @Bean
    public OtherProductService getOtherProductService() {
        //serviceName and class is needed as they weren't passed at construction time
        return (OtherProductService) getService(OTHER_SERVICE_NAME, OtherProductServiceImpl.class);
    }

    @Bean
    public OtherProductService2 getOtherProduct2Service() {
        //serviceName and class is needed as they weren't passed at construction time
        return (OtherProductService2) getService(OTHER_SERVICE_2_NAME, OtherProductService2Impl.class);
    }


}
