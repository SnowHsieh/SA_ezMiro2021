package ntut.csie.islab.miro.usecase.stickyNote;

import java.util.UUID;

public class DeleteStickyNoteInput {
    private UUID figureId;

    public UUID getFigureId() {
        return figureId;
    }

    public void setFigureId(UUID figureId) {
        this.figureId = figureId;
    }
}
