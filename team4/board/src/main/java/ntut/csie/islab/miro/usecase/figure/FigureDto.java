package ntut.csie.islab.miro.usecase.figure;

import ntut.csie.islab.miro.entity.model.figure.Position;
import ntut.csie.islab.miro.entity.model.figure.Style;

import java.util.UUID;

public class FigureDto {
    private UUID figureId;
    private UUID boardId;
    private Position position;
    private String content;
    private Style style;

    public UUID getFigureId() {
        return figureId;
    }

    public void setFigureId(UUID figureId) {
        this.figureId = figureId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
}