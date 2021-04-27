package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
import ntut.csie.selab.model.AggregateRoot;

import java.util.Date;

public class Widget extends AggregateRoot<String> {
    protected String boardId;
    protected Coordinate coordinate;
    protected String text;

    public Widget(String id, String boardId, Coordinate coordinate) {
        super(id);
        this.boardId = boardId;
        this.coordinate = coordinate;

        addDomainEvent(new WidgetCreated(new Date(), boardId, id));
    }

    public String getBoardId() {
        return boardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
