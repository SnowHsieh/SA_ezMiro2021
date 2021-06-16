package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="widget")
public class StickyNoteData {
    @Id
    @Column(name="widget_id")
    private String widgetId;

    @Column(name="board_id", nullable = false)
    private String boardId;

    @Column(name="top_left_x")
    private int topLeftX;

    @Column(name="top_left_y")
    private int topLeftY;

    @Column(name="bottom_right_x")
    private int bottomRightX;

    @Column(name="bottom_right_y")
    private int bottomRightY;

    @Column(name="color")
    private String color;

    @Column(name="text_color")
    private String textColor;

    @Column(name="text")
    private String text;

    @Column(name="font_size")
    private int fontSize;

    public StickyNoteData() {
    }

    public StickyNoteData(
            String widgetId,
            String boardId,
            int topLeftX,
            int topLeftY,
            int bottomRightX,
            int bottomRightY,
            String color,
            String textColor,
            String text,
            int fontSize
    ) {
        this.widgetId = widgetId;
        this.boardId = boardId;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
        this.color = color;
        this.textColor = textColor;
        this.text = text;
        this.fontSize = fontSize;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public String getBoardId() {
        return boardId;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getBottomRightX() {
        return bottomRightX;
    }

    public int getBottomRightY() {
        return bottomRightY;
    }

    public String getColor() {
        return color;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public void setTopLeftX(int topLeftX) {
        this.topLeftX = topLeftX;
    }

    public void setTopLeftY(int topLeftY) {
        this.topLeftY = topLeftY;
    }

    public void setBottomRightX(int bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public void setBottomRightY(int bottomRightY) {
        this.bottomRightY = bottomRightY;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
