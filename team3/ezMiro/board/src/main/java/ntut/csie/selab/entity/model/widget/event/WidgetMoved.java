package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class WidgetMoved extends DomainEvent {
    private String widgetId;

    public WidgetMoved(Date occurredOn, String widgetId) {
        super(occurredOn);
        this.widgetId = widgetId;
    }

    public String getWidgetId() {
        return widgetId;
    }
}
