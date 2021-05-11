package ntut.csie.sslab.kanban.usecase;

import ntut.csie.sslab.ddd.model.DomainEvent;

public interface BoardSessionBroadcaster {
    void broadcast(DomainEvent domainEvent, String sessionId);
}
