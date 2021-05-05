package ntut.csie.selab.usecase.widget.edit.layer;

public class EditZIndexOfStickyNoteInput {
    private String stickyNoteId;
    private int zIndex;

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }
}
