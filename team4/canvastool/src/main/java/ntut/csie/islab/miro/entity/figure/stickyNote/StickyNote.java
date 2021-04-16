package ntut.csie.islab.miro.entity.figure.stickyNote;

import ntut.csie.islab.miro.entity.figure.FigurePosition;
import ntut.csie.islab.miro.entity.figure.FigureStyle;
import ntut.csie.sslab.ddd.model.Entity;

import java.util.UUID;

public class StickyNote extends Entity<UUID> {
    private UUID boardId;
    private FigurePosition position;
    private String content;
    private FigureStyle style;

    public StickyNote(UUID boardId, FigurePosition position, String content, FigureStyle style) {
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

    public FigurePosition getPosition() {
        return position;
    }

    public void setPosition(FigurePosition position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FigureStyle getStyle() {
        return style;
    }

    public void setStyle(FigureStyle style) {
        this.style = style;
    }
}
