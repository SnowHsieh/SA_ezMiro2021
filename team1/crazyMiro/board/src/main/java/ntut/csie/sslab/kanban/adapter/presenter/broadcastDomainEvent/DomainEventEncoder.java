package ntut.csie.sslab.kanban.adapter.presenter.broadcastDomainEvent;


import ntut.csie.sslab.ddd.model.DomainEvent;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DomainEventEncoder implements Encoder.Text<DomainEvent> {

    @Override
    public String encode(DomainEvent domainEvent) throws EncodeException {
        StringBuilder sb = new StringBuilder();
//        sb.append(domainEvent.getJsonEvent());

//        EventDataBuilderJava8.json(remoteDomainEvent.getEventType())
//        return Json.asString(domainEvent);
        return null;
    }


    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {

    }
}
