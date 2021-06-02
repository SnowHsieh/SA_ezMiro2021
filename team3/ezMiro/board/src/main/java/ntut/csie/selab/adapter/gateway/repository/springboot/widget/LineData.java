package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import ntut.csie.selab.entity.model.widget.Widget;

import javax.persistence.*;

@Entity
@Table(name = "line")
public class LineData {
    @Id
    @Column(name = "line_id")
    private String lineId;

    @Column(name = "board_id", nullable = false)
    private String boardId;

    @Column(name = "top_left_x")
    private int topLeftX;

    @Column(name = "top_left_y")
    private int topLeftY;

    @Column(name = "bottom_right_x")
    private int bottomRightX;

    @Column(name = "bottom_right_y")
    private int bottomRightY;

    @OneToOne
    @JoinColumn(name = "head_widget_id", referencedColumnName = "widget_id")
    private StickyNoteData headWidget;

    @OneToOne
    @JoinColumn(name = "tail_widget_id", referencedColumnName = "widget_id")
    private StickyNoteData tailWidget;

    public LineData() {
    }

    public LineData(String lineId) {
        this.lineId = lineId;
    }

    public LineData(String lineId, String boardId, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        this.lineId = lineId;
        this.boardId = boardId;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public void setTopLeftX(int topLeftX) {
        this.topLeftX = topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public void setTopLeftY(int topLeftY) {
        this.topLeftY = topLeftY;
    }

    public int getBottomRightX() {
        return bottomRightX;
    }

    public void setBottomRightX(int bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public int getBottomRightY() {
        return bottomRightY;
    }

    public void setBottomRightY(int bottomRightY) {
        this.bottomRightY = bottomRightY;
    }

    public StickyNoteData getHeadWidget() {
        return headWidget;
    }

    public void setHeadWidget(StickyNoteData headWidget) {
        this.headWidget = headWidget;
    }

    public StickyNoteData getTailWidget() {
        return tailWidget;
    }

    public void setTailWidget(StickyNoteData tailWidget) {
        this.tailWidget = tailWidget;
    }
}
