package com.leysoft.service.inter;

import com.leysoft.dto.GreetingRequest;
import com.leysoft.dto.GreetingResponse;

public interface GreetingService {
	
	public GreetingResponse greeting(GreetingRequest request);
}
