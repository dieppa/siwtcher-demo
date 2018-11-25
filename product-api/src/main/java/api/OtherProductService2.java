package api;

import feign.Headers;
import feign.RequestLine;

import static api.ApiPaths.OTHER_PRODUCT_2_PATH;
import static api.ApiPaths.OTHER_PRODUCT_PATH;
import static api.ApiPaths.ROOT_PATH;


public interface OtherProductService2 {

    @Headers("Content-Type: application/json")
    @RequestLine("GET " + ROOT_PATH + OTHER_PRODUCT_2_PATH)
    Product getProduct();
}
