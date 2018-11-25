package api;

import feign.Headers;
import feign.RequestLine;

import static api.ApiPaths.PRODUCT_PATH;
import static api.ApiPaths.ROOT_PATH;


public interface ProductService {

    @Headers("Content-Type: application/json")
    @RequestLine("GET " + ROOT_PATH + PRODUCT_PATH)
    Product getProduct();
}
