package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.model.DomainEvent;

public interface BoardSessionBroadcaster {

    void broadcast(String boardSessionId, DomainEvent domainEvent);
}
