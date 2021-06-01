package ntut.csie.selab.entity.model.widget;

import ntut.csie.selab.entity.model.widget.event.TextOfWidgetEdited;

import java.util.Date;

public class StickyNote extends Widget {

    private String text;
    private String textColor;
    private int fontSize;

    public StickyNote(String id, String boardId, Coordinate coordinate) {
        super(id, boardId, coordinate, WidgetType.STICKY_NOTE.getType());
        this.textColor = "#000000";
        this.text = "";
    }

    public void setText(String text) {
        this.text = text;

        addDomainEvent(new TextOfWidgetEdited(new Date(), boardId, id));
    }

    public String getText() {
        return text;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
