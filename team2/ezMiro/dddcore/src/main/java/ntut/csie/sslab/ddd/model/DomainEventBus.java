package ntut.csie.sslab.ddd.model;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {
    public DomainEventBus() {
        super();
    }

    public void post(DomainEvent domainEvent){
        super.post(domainEvent);
    }

    public void postAll(AggregateRoot aggregateRoot) {
        List<DomainEvent> domainEvents = new ArrayList<>(aggregateRoot.getDomainEvents());
        aggregateRoot.clearDomainEvents();

        for(DomainEvent domainEvent : domainEvents)
            super.post(domainEvent);

        domainEvents.clear();
    }

}
