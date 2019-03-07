
package com.leysoft.service.inter;

import com.leysoft.dto.MessageRequest;

public interface SenderService {

    public boolean send(MessageRequest message);

    public boolean sendCustomMessageSource(MessageRequest message);
}
