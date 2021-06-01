package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.*;
import ntut.csie.selab.model.AggregateRoot;

import java.util.Date;

public abstract class Widget extends AggregateRoot<String> {
    protected String boardId;
    protected Coordinate coordinate;
    protected String color;
    protected String type;

    public Widget(String id, String boardId, Coordinate coordinate, String type) {
        super(id);
        this.boardId = boardId;
        this.coordinate = coordinate;
        this.color = "#FFFAAD";
        this.type = type;

        addDomainEvent(new WidgetCreated(new Date(), boardId, id, type));
    }

    public void delete() {
        addDomainEvent((new WidgetDeleted(
                new Date(),
                this.boardId,
                getId()
        )));
    }

    public String getBoardId() {
        return boardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void move(Coordinate coordinate) {
        setCoordinate(coordinate);

        addDomainEvent(new WidgetMoved(new Date(), boardId, id));
    }

    public void resize(Coordinate coordinate) {
        setCoordinate(coordinate);

        addDomainEvent(new WidgetResized(new Date(), boardId, id));
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        addDomainEvent(new ColorOfWidgetChanged(new Date(), boardId, id));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
