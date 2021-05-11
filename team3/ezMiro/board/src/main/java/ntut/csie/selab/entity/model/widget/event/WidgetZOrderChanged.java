package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetZOrderChanged extends DomainEvent {
    public WidgetZOrderChanged(Date occurredOn) {
        super(occurredOn);
    }
}
