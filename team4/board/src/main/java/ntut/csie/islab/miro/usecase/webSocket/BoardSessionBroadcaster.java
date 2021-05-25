package ntut.csie.islab.miro.usecase.webSocket;

import ntut.csie.sslab.ddd.model.DomainEvent;

public interface BoardSessionBroadcaster {
    void broadcast(DomainEvent domainEvent, String sessionId);
}
