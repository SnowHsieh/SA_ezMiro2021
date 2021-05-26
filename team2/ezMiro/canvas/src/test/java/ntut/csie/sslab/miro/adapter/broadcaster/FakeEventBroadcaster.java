package ntut.csie.sslab.miro.adapter.broadcaster;

import ntut.csie.sslab.ddd.model.RemoteDomainEvent;
import ntut.csie.sslab.miro.usecase.EventBroadcaster;
import java.util.ArrayList;
import java.util.List;

public class FakeEventBroadcaster implements EventBroadcaster {

    private List<RemoteDomainEvent> remoteDomainEvents;

    public FakeEventBroadcaster() {
        remoteDomainEvents = new ArrayList<>();
    }

    public void clearDomainEventModels() {
        remoteDomainEvents.clear();
    }

    public List<RemoteDomainEvent> getRemoteDomainEvents() {
        return remoteDomainEvents;
    }

    @Override
    public void broadcast(RemoteDomainEvent remoteDomainEvent, String channelId, String subChannel) {
        remoteDomainEvents.add(remoteDomainEvent);
    }
}