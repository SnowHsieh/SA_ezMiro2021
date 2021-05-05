package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetDeletionCommitted extends DomainEvent {
    public WidgetDeletionCommitted(Date occurredOn) {
        super(occurredOn);
    }
}
