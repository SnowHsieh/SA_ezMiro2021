package ntut.csie.selab.model;

import com.google.common.eventbus.EventBus;

public class DomainEventBus extends EventBus {
    private int count = 0;

    public DomainEventBus() {
        super();
    }

    public void post(DomainEvent domainEvent){
        super.post(domainEvent);
        count++;
    }

    public int getCount() {
        return count;
    }
}
