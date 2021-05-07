package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.TextOfWidgetEdited;
import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
import ntut.csie.selab.entity.model.widget.event.WidgetDeleted;
import ntut.csie.selab.entity.model.widget.event.WidgetMoved;
import ntut.csie.selab.model.AggregateRoot;

import java.util.Date;

public abstract class Widget extends AggregateRoot<String> {
    protected String boardId;
    protected Coordinate coordinate;
    protected String text;
    protected String color;
    protected String textColor;

    public Widget(String id, String boardId, Coordinate coordinate) {
        super(id);
        this.boardId = boardId;
        this.coordinate = coordinate;
        this.color = "#FFFAAD";
        this.textColor = "#ffffff";
        this.text = "";

        addDomainEvent(new WidgetCreated(new Date(), boardId, id));
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

        addDomainEvent(new WidgetMoved(new Date(), id));
    }

    public void setText(String text) {
        this.text = text;

        addDomainEvent(new TextOfWidgetEdited(new Date()));
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
