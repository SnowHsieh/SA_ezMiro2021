package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetResizeNotifiedToAllUser extends DomainEvent {
    public WidgetResizeNotifiedToAllUser(Date date) {
        super(date);
    }
}
