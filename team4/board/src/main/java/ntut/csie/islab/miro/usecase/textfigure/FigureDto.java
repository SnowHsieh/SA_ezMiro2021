package ntut.csie.islab.miro.usecase.textfigure;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.line.ArrowKindEnum;
import ntut.csie.islab.miro.entity.model.textfigure.Style;

import java.util.List;
import java.util.UUID;

public class FigureDto {

    private UUID boardId;
    private UUID figureId;
    private String kind;
    //line
    private List<Position> positionList;
    private ArrowKindEnum srcArrowKind ; //NONE,CIRCLE,ARROW
    private ArrowKindEnum destArrowKind; //NONE,CIRCLE,ARROW
    private int strokeWidth ;
    private String color ;
    //textFigure
    private Position position;
    private String content;
    private Style style;


    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public ArrowKindEnum getSrcArrowKind() {
        return srcArrowKind;
    }

    public void setSrcArrowKind(ArrowKindEnum srcArrowKind) {
        this.srcArrowKind = srcArrowKind;
    }

    public ArrowKindEnum getDestArrowKind() {
        return destArrowKind;
    }

    public void setDestArrowKind(ArrowKindEnum destArrowKind) {
        this.destArrowKind = destArrowKind;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public void setFigureId(UUID figureId) {
        this.figureId = figureId;
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
