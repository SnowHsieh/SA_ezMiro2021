package ntut.csie.islab.miro.usecase.stickyNote;

import java.util.UUID;

import ntut.csie.islab.miro.figure.entity.figure.Style;

public class EditStickyNoteInput {
    private UUID stickyNoteId;
    private String content;
    private Style style;

    public UUID getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(UUID stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
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
