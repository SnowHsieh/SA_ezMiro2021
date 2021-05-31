package ntut.csie.selab.usecase.widget.stickynote.edit.color;

public class ChangeColorOfStickyNoteOutput {
    private String stickyNoteId;
    private String stickyNoteColor;

    public String getStickyNoteColor() {
        return stickyNoteColor;
    }

    public void setStickyNoteColor(String stickyNoteColor) {
        this.stickyNoteColor = stickyNoteColor;
    }

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }
}
