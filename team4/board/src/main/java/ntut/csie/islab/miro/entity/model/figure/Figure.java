package ntut.csie.islab.miro.entity.model.figure;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.line.ArrowKindEnum;
import ntut.csie.islab.miro.entity.model.figure.textfigure.Style;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Figure extends AggregateRoot<UUID> {
    private UUID boardId;
    // line
    private List<Position> positionList;
    private UUID srcTextFigureId;
    private UUID destTextFigureId;
    private ArrowKindEnum srcArrowKind; //NONE,CIRCLE,ARROW
    private ArrowKindEnum destArrowKind; //NONE,CIRCLE,ARROW
    private int strokeWidth;
    private String color;
    // textFigure
    private Position position;
    private String content;
    private Style style;


    // line
    public Figure(UUID boardId, List<Position> positionList, int strokeWidth, String color) {
        super(UUID.randomUUID());
        this.boardId = boardId;
        this.positionList = positionList;
        this.srcArrowKind = ArrowKindEnum.NONE;
        this.destArrowKind = ArrowKindEnum.NONE;
        this.strokeWidth = strokeWidth;
        this.color = color;
    }

    public Figure(UUID boardId, UUID figureId, List<Position> positionList, int strokeWidth, String color) {
        super(figureId);
        this.boardId = boardId;
        this.positionList = positionList;
        this.srcArrowKind = ArrowKindEnum.NONE;
        this.destArrowKind = ArrowKindEnum.NONE;
        this.strokeWidth = strokeWidth;
        this.color = color;
    }

    // textFigure
    public Figure(UUID boardId, Position position, String content, Style style) {
        super(UUID.randomUUID());
        this.boardId = boardId;
        this.position = position;
        this.content = content;
        this.style = style;
    }

    public Figure(UUID boardId, UUID stickyNoteId, Position position, String content, Style style) {
        super(stickyNoteId);
        this.boardId = boardId;
        this.position = position;
        this.content = content;
        this.style = style;
    }

    public UUID getFigureId() {
        return this.getId();
    }

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

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

    public UUID getSrcTextFigureId() {
        return srcTextFigureId;
    }

    public void setSrcTextFigureId(UUID srcTextFigureId) {
        this.srcTextFigureId = srcTextFigureId;
    }

    public UUID getDestTextFigureId() {
        return destTextFigureId;
    }

    public void setDestTextFigureId(UUID destTextFigureId) {
        this.destTextFigureId = destTextFigureId;
    }

    public abstract void markAsRemoved(UUID boardId, UUID figureId);

    public abstract void changeContent(String newContent);

    public abstract void changePosition(Position newPosition);

    public abstract void changeColor(String newColor);

    public abstract void resize(Double newWidth, Double newHeight);

    public abstract FigureTypeEnum getKind();

    public abstract void changeLinePath(List<Position> positionList);

    public abstract void attachTextFigure(UUID figureId,String attachEndPointKind);

    public abstract void unattachTextFigure(String attachEndPointKind);
}
