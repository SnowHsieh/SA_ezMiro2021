package ntut.csie.sslab.miro.entity.model.figure.line;

import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import java.util.UUID;

public class LineBuilder {
    private String boardId;
    private String lineId;
    private String startConnectableFigureId;
    private String endConnectableFigureId;
    private Coordinate startOffset;
    private Coordinate endOffset;
    private ArrowStyle startArrowStyle;
    private ArrowStyle endArrowStyle;

    public static LineBuilder newInstance() {
        return new LineBuilder();
    }

    public LineBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public LineBuilder startConnectableFigureId(String startConnectableFigureId) {
        this.startConnectableFigureId = startConnectableFigureId;
        return this;
    }

    public LineBuilder endConnectableFigureId(String endConnectableFigureId) {
        this.endConnectableFigureId = endConnectableFigureId;
        return this;
    }

    public LineBuilder startOffset(Coordinate startOffset) {
        this.startOffset = startOffset;
        return this;
    }

    public LineBuilder endOffset(Coordinate endOffset) {
        this.endOffset = endOffset;
        return this;
    }

    public LineBuilder startArrowStyle(ArrowStyle startArrowStyle) {
        this.startArrowStyle = startArrowStyle;
        return this;
    }

    public LineBuilder endArrowStyle(ArrowStyle endArrowStyle) {
        this.endArrowStyle = endArrowStyle;
        return this;
    }

    public Line build() {
        lineId = UUID.randomUUID().toString();
        Line line = new Line(lineId, boardId, startConnectableFigureId, endConnectableFigureId, startOffset, endOffset, startArrowStyle, endArrowStyle);
        return line;
    }
}