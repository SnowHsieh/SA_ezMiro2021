package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="line")
public class LineData {
    @Column(name="board_id", nullable = false)
    private String boardId;

    @Id
    @Column(name="line_id")
    private String lineId;

    @Column(name="start_connectable_figure_id")
    private String startConnectableFigureId;

    @Column(name="end_connectable_figure_id")
    private String endConnectableFigureId;

    @Column(name="start_offset_x")
    private double startOffsetX;

    @Column(name="start_offset_y")
    private double startOffsetY;

    @Column(name="end_offset_x")
    private double endOffsetX;

    @Column(name="end_offset_y")
    private double endOffsetY;

    @Column(name="start_arrow_style")
    private String startArrowStyle;

    @Column(name="end_arrow_style")
    private String endArrowStyle;

    public LineData() {
    }

    public LineData(String boardId, String lineId, String startConnectableFigureId, String endConnectableFigureId, Coordinate startOffset, Coordinate endOffset, String startArrowStyle, String endArrowStyle) {
        this.boardId = boardId;
        this.lineId = lineId;
        this.startConnectableFigureId = startConnectableFigureId;
        this.endConnectableFigureId = endConnectableFigureId;
        this.startOffsetX = startOffset.getX();
        this.startOffsetY = startOffset.getY();
        this.endOffsetX = endOffset.getX();
        this.endOffsetY = endOffset.getY();
        this.startArrowStyle = startArrowStyle;
        this.endArrowStyle = endArrowStyle;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getStartConnectableFigureId() {
        return startConnectableFigureId;
    }

    public void setStartConnectableFigureId(String startConnectableFigureId) {
        this.startConnectableFigureId = startConnectableFigureId;
    }

    public String getEndConnectableFigureId() {
        return endConnectableFigureId;
    }

    public void setEndConnectableFigureId(String endConnectableFigureId) {
        this.endConnectableFigureId = endConnectableFigureId;
    }

    public double getStartOffsetX() {
        return startOffsetX;
    }

    public void setStartOffsetX(double startOffsetX) {
        this.startOffsetX = startOffsetX;
    }

    public double getStartOffsetY() {
        return startOffsetY;
    }

    public void setStartOffsetY(double startOffsetY) {
        this.startOffsetY = startOffsetY;
    }

    public double getEndOffsetX() {
        return endOffsetX;
    }

    public void setEndOffsetX(double endOffsetX) {
        this.endOffsetX = endOffsetX;
    }

    public double getEndOffsetY() {
        return endOffsetY;
    }

    public void setEndOffsetY(double endOffsetY) {
        this.endOffsetY = endOffsetY;
    }

    public String getStartArrowStyle() {
        return startArrowStyle;
    }

    public void setStartArrowStyle(String startArrowStyle) {
        this.startArrowStyle = startArrowStyle;
    }

    public String getEndArrowStyle() {
        return endArrowStyle;
    }

    public void setEndArrowStyle(String endArrowStyle) {
        this.endArrowStyle = endArrowStyle;
    }
}