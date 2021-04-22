package ntut.csie.islab.miro.usecase.stickyNote;

import java.util.UUID;
import ntut.csie.islab.miro.entity.Figure;
import ntut.csie.islab.miro.entity.stickyNote.StickyNote;
import ntut.csie.islab.miro.entity.figure.Style;
import ntut.csie.islab.miro.entity.figure.Position;

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
