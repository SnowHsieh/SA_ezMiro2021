package ntut.csie.selab.usecase.widget.stickynote.edit.fontsize;

public class EditFontSizeOfStickyNoteInput {
    private String stickyNoteId;
    private int fontSize;

    public String getStickyNoteId() {
        return stickyNoteId;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.stickyNoteId = stickyNoteId;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
