package ntut.csie.islab.miro.usecase.stickyNote;

import java.util.UUID;

import ntut.csie.islab.miro.figure.entity.figure.Style;
import ntut.csie.islab.miro.figure.entity.figure.Position;
public class CreateStickyNoteInput {
    private UUID boardId;
    private Position position;
    private String content;
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

    public void setPosition(double x, double y) {
        this.position = new Position(x,y);
    }
    public Position getPosition() {
        return this.position;
    }
}
