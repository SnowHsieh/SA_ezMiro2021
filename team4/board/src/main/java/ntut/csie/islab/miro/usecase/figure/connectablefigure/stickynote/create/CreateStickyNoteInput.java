package ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote.create;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;

import java.util.UUID;

public class CreateStickyNoteInput {
    private UUID boardId;
    private Position position;
    private String content;
    private double width;
    private double height;
    private Style style;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(double x, double y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return this.position;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
