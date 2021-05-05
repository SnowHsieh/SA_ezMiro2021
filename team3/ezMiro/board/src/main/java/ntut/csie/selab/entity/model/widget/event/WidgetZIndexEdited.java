package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetZIndexEdited extends DomainEvent {
    public WidgetZIndexEdited(Date occurredOn) {
        super(occurredOn);
    }
}
