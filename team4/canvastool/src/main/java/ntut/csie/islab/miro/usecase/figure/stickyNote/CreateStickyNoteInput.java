package ntut.csie.islab.miro.usecase.figure.stickyNote;

import ntut.csie.islab.miro.entity.figure.FigurePosition;
import ntut.csie.islab.miro.entity.figure.FigureStyle;

import java.util.UUID;

public class CreateStickyNoteInput {
    private UUID boardId;
    private FigurePosition position;
    private String content;
    private FigureStyle style;

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

    public FigureStyle getStyle() {
        return style;
    }

    public void setStyle(FigureStyle style) {
        this.style = style;
    }

    public void setPosition(double x, double y) {
        this.position = new FigurePosition(x,y);
    }
    public FigurePosition getPosition() {
        return this.position;
    }
}
