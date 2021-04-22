package ntut.csie.sslab.miro.usecase;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class DomainEventListener {
    private int eventCount;

    @Subscribe
    public void eventHandler(DomainEvent domainEvent){
        eventCount++;
    }

    public int getEventCount() {
        return eventCount;
    }
}
