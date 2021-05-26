package ntut.csie.sslab.miro.adapter.controller.websocket;

import ntut.csie.sslab.ddd.model.RemoteDomainEvent;
import ntut.csie.sslab.miro.usecase.EventBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketBroadcaster implements EventBroadcaster {

    @Autowired
    SimpMessagingTemplate template;

    @Override
    public void broadcast(RemoteDomainEvent domainEvent, String channelId, String subChannel) {
        template.convertAndSend("/topic/" + channelId + "/" + subChannel, domainEvent);
    }
}