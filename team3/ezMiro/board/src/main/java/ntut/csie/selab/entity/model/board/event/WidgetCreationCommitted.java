package ntut.csie.selab.entity.model.board.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetCreationCommitted extends DomainEvent {
    public WidgetCreationCommitted(Date date) {
        super(date);
    }
}
