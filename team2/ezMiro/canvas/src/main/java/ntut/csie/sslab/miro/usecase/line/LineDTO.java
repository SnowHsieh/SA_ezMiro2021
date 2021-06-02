package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.ArrowStyle;

public class LineDTO {
    private String lineId;
    private String startConnectableFigureId;
    private String endConnectableFigureId;
    private Coordinate startOffset;
    private Coordinate endOffset;
    private ArrowStyle startArrowStyle;
    private ArrowStyle endArrowStyle;
    private int zOrder;

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

    public Coordinate getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(Coordinate startOffset) {
        this.startOffset = startOffset;
    }

    public Coordinate getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(Coordinate endOffset) {
        this.endOffset = endOffset;
    }

    public ArrowStyle getStartArrowStyle() {
        return startArrowStyle;
    }

    public void setStartArrowStyle(ArrowStyle startArrowStyle) {
        this.startArrowStyle = startArrowStyle;
    }

    public ArrowStyle getEndArrowStyle() {
        return endArrowStyle;
    }

    public void setEndArrowStyle(ArrowStyle endArrowStyle) {
        this.endArrowStyle = endArrowStyle;
    }

    public int getZOrder() {
        return zOrder;
    }

    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }
}