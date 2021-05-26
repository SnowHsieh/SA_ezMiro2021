package ntut.csie.sslab.miro.usecase;

import ntut.csie.sslab.ddd.model.RemoteDomainEvent;

public interface EventBroadcaster {
    void broadcast(RemoteDomainEvent domainEvent, String channelId, String subChannel);
}