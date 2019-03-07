
package com.leysoft.service.inter;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomMessageSource {

    String OUTPUT = "custom-message-output";

    @Output(
            value = OUTPUT)
    MessageChannel output();
}
