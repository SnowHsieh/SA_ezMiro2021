package ntut.csie.islab.miro.usecase.textFigure.stickyNote;

import java.util.UUID;

public class ChangeStickyNoteContentInput {
    private UUID boardId;
    private UUID figureId;
    private String content;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

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
}
