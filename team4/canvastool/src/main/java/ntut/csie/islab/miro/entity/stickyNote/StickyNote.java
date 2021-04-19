package ntut.csie.islab.miro.entity.stickyNote;

import ntut.csie.islab.miro.entity.Style;
import ntut.csie.islab.miro.entity.Position;
import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.model.Entity;

import java.util.UUID;

public class StickyNote extends AggregateRoot<UUID> {
    private UUID boardId;
    private Position position;
    private String content;
    private Style style;

    public StickyNote(UUID boardId, Position position, String content, Style style) {
        super(UUID.randomUUID());
        this.boardId = boardId;
        this.position = position;
        this.content = content;
        this.style = style;
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
