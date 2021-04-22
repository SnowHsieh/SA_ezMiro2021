package ntut.csie.selab.entity.model.widget;

import java.awt.*;

public class Widget {
    protected String id;
    protected String boardId;
    protected Coordinate coordinate;

    protected String text;

    public Widget(String id, String boardId, Coordinate coordinate) {
        this.id = id;
        this.boardId = boardId;
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
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
