package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetTextOfWidgetModifiedToAllUser extends DomainEvent {
    public WidgetTextOfWidgetModifiedToAllUser(Date date) {
        super(date);
    }
}
