package ntut.csie.selab.usecase.widget.stickynote.edit.text;

public class EditTextOfStickyNoteOutput {
    private String StickyNoteId;
    private String text;

    public String getStickyNoteId() {
        return StickyNoteId;
    }

    public void setStickyNoteId(String stickyNoteId) {
        StickyNoteId = stickyNoteId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
