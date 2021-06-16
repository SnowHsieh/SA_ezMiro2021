package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.*;
import ntut.csie.selab.model.AggregateRoot;

import java.util.Date;

public abstract class Widget extends AggregateRoot<String> {
    protected String boardId;
    protected Position position;
    protected String color;
    protected String type;

    public Widget(String id, String boardId, Position position, String type) {
        super(id);
        this.boardId = boardId;
        this.position = position;
        this.color = "#FFFAAD";
        this.type = type;

        addDomainEvent(new WidgetCreated(new Date(), boardId, id, type));
    }

    public void delete() {
        addDomainEvent((new WidgetDeleted(
                new Date(),
                this.boardId,
                getId(),
                type
        )));
    }

    public String getBoardId() {
        return boardId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Position position) {
        setPosition(position);

        addDomainEvent(new WidgetMoved(new Date(), boardId, id, type));
    }

    public void resize(Position position) {
        setPosition(position);

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
