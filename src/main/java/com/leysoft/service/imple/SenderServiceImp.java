
package com.leysoft.service.imple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.leysoft.dto.MessageRequest;
import com.leysoft.service.inter.SenderService;

@Service
public class SenderServiceImp implements SenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SenderServiceImp.class);

    @Autowired
    private Source source;

    @Override
    public boolean send(MessageRequest message) {
        String info = "send message: " + message.getMessage();
        LOGGER.info(info);
        return source.output().send(MessageBuilder.withPayload(message).build());
    }

}
