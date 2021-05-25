package ntut.csie.sslab.miro.usecase;

import ntut.csie.sslab.ddd.model.DomainEvent;

public interface BoardSessionBroadcaster {
    void broadcast(DomainEvent domainEvent, String sessionId);
}
