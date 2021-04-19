package ntut.csie.islab.miro.usecase.stickyNote;

import java.util.UUID;

public class DeleteStickyNoteInput {
    private UUID stickyNoteId;

    public UUID getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(UUID stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }
}
