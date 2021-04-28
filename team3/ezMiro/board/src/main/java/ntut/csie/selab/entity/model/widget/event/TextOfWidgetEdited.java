package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class TextOfWidgetEdited extends DomainEvent {

    public TextOfWidgetEdited(Date occurredOn) {
        super(occurredOn);
    }

}
