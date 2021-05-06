package ntut.csie.sslab.kanban.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

import java.util.Date;

public class StickerCreated extends DomainEvent {
    private final String boardId;
    private final String figureId;
    private final String content;
    private final int width;
    private final int length;
    private final String color;
    private final Coordinate position;

    public StickerCreated(String boardId, String figureId, String content, int width, int length, String color, Coordinate position) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.content = content;
        this.width = width;
        this.length = length;
        this.color = color;
        this.position = position;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getFigureId() {
        return figureId;
    }

    public String getContent() {
        return content;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getColor() {
        return color;
    }

    public Coordinate getPosition() {
        return position;
    }
}
