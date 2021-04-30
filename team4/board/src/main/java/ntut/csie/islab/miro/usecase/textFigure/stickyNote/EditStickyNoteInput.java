package ntut.csie.islab.miro.usecase.textFigure.stickyNote;

import ntut.csie.islab.miro.entity.model.textFigure.Style;
import java.util.UUID;

public class EditStickyNoteInput {
    private UUID boardId;
    private UUID figureId;
    private String content;
    private Style style;

    public UUID getFigureId() {
        return figureId;
    }

    public void setFigureId(UUID figureId) {
        this.figureId = figureId;
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

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }
}
