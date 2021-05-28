package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.*;
import ntut.csie.selab.model.AggregateRoot;

import java.util.Date;

public abstract class Widget extends AggregateRoot<String> {
    protected String boardId;
    protected Coordinate coordinate;
    protected String text;
    protected String color;
    protected String textColor;
    protected int fontSize;

    public Widget(String id, String boardId, Coordinate coordinate) {
        super(id);
        this.boardId = boardId;
        this.coordinate = coordinate;
        this.color = "#FFFAAD";
        this.textColor = "#000000";
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
    }

    public void move(Coordinate coordinate) {
        setCoordinate(coordinate);

        addDomainEvent(new WidgetMoved(new Date(), boardId, id));
    }

    public void resize(Coordinate coordinate) {
        setCoordinate(coordinate);

        addDomainEvent(new WidgetResized(new Date(), boardId, id));
    }

    public void setText(String text) {
        this.text = text;

        addDomainEvent(new TextOfWidgetEdited(new Date(), boardId, id));
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        addDomainEvent(new ColorOfWidgetChanged(new Date(), boardId, id));
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
