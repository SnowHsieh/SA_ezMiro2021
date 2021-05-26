package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetMovementNotifiedToAllUser extends DomainEvent {
    public WidgetMovementNotifiedToAllUser(Date date) {
        super(date);
    }
}
