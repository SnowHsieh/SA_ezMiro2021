package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetDeletionNotifiedToAllUser extends DomainEvent {
    public WidgetDeletionNotifiedToAllUser(Date date) {
        super(date);
    }
}
