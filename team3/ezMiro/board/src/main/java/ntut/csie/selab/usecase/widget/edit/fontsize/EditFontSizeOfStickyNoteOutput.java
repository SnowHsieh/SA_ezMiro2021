package ntut.csie.selab.usecase.widget.edit.fontsize;

public class EditFontSizeOfStickyNoteOutput {

    private String StickyNoteId;
    private int fontSize;

    public String getStickyNoteId() {
        return StickyNoteId;
    }

    public void setStickyNoteId(String stickyNoteId) {
        this.StickyNoteId = stickyNoteId;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
