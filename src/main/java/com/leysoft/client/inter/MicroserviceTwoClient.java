
package com.leysoft.client.inter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.leysoft.client.imple.HystrixClient;
import com.leysoft.dto.GreetingRequest;
import com.leysoft.dto.GreetingResponse;

@FeignClient(
        value = "microservice-two",
        fallback = HystrixClient.class)
public interface MicroserviceTwoClient {

    @PostMapping(
            value = {
                "/greeting"
            })
    public GreetingResponse greeting(@RequestBody GreetingRequest request);
}
