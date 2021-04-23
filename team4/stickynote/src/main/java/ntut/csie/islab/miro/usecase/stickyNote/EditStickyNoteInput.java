package ntut.csie.islab.miro.usecase.stickyNote;

import java.util.UUID;

import ntut.csie.islab.miro.figure.entity.model.figure.Style;

public class EditStickyNoteInput {
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
}
