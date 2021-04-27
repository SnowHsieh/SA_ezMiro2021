package ntut.csie.selab.model;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus extends EventBus {
    private int count = 0;

    public DomainEventBus() {
        super();
    }

    public void post(DomainEvent domainEvent){
        super.post(domainEvent);
        count++;
    }

    public void postAll(AggregateRoot aggregateRoot) {
        List<DomainEvent> domainEvents = new ArrayList<>(aggregateRoot.getDomainEvents());
        aggregateRoot.clearDomainEvents();

        for(DomainEvent domainEvent : domainEvents)
            post(domainEvent);

        domainEvents.clear();
    }

    public int getCount() {
        return count;
    }
}
