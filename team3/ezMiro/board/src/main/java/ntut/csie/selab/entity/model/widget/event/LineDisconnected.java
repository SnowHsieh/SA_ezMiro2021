package ntut.csie.selab.entity.model.widget.event;

import ntut.csie.selab.model.DomainEvent;

import java.util.Date;

public class LineDisconnected extends DomainEvent {
    private String lineId;
    private String lineEndPoint;

    public LineDisconnected(Date date, String id, String lineEndPoint) {
        super(date);
        this.lineId = id;
        this.lineEndPoint = lineEndPoint;
    }
}
