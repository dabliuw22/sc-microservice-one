
package com.leysoft.client.imple;

import org.springframework.stereotype.Component;

import com.leysoft.client.inter.MicroserviceTwoClient;
import com.leysoft.dto.GreetingRequest;
import com.leysoft.dto.GreetingResponse;

@Component
public class HystrixClient implements MicroserviceTwoClient {

    @Override
    public GreetingResponse greeting(GreetingRequest request) {
        GreetingResponse response = new GreetingResponse();
        response.setMessage(request.getName() + ", an error occurred");
        return response;
    }
}
