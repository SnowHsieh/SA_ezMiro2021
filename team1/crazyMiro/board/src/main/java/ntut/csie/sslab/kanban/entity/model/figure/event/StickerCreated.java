package ntut.csie.sslab.kanban.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

public class StickerCreated extends DomainEvent {
    private final String boardId;
    private final String figureId;
    private final String content;
    private final int size;
    private final String color;
    private final Coordinate position;

    public StickerCreated(String boardId, String figureId, String content, int size, String color, Coordinate position) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
        this.content = content;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public Coordinate getPosition() {
        return position;
    }
}
