package io.cloudyrock.switcher.client.api;

import feign.Headers;
import feign.RequestLine;

import static io.cloudyrock.switcher.client.api.ApiPaths.OTHER_CLIENT_PATH;
import static io.cloudyrock.switcher.client.api.ApiPaths.ROOT_PATH;

public interface OtherClientService {

    @Headers("Content-Type: application/json")
    @RequestLine("GET " + ROOT_PATH + OTHER_CLIENT_PATH)
    Client getClient();
}