package ntut.csie.islab.miro.usecase.websocket;

import ntut.csie.sslab.ddd.model.DomainEvent;

public interface BoardSessionBroadcaster {
    void broadcast(DomainEvent domainEvent, String sessionId);
}
