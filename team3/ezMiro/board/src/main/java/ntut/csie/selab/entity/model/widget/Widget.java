package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
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
        this.color = "#FFFFFF";
        this.textColor = "#000000";

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
