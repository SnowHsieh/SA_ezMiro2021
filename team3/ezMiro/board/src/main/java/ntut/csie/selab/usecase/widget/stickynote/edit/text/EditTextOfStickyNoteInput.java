package ntut.csie.selab.usecase.widget.stickynote.edit.text;

public class EditTextOfStickyNoteInput {

    private String stickyNoteId;
    private String text;

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public String getText() {
        return text;
    }
}
