package ntut.csie.selab.entity.model.widget;

public class StickyNote extends Widget {
    private int fontSize;

    public StickyNote(String id, String boardId, Coordinate coordinate) {
        super(id, boardId, coordinate);
        fontSize = 12;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
