package ntut.csie.islab.miro.entity.model.figure;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.line.ArrowKindEnum;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.List;
import java.util.UUID;

public abstract class Figure extends AggregateRoot<UUID> {
    private UUID boardId;
    private List<Position> positionList;
    private ArrowKindEnum srcArrowKind ; //NONE,CIRCLE,ARROW
    private ArrowKindEnum destArrowKind; //NONE,CIRCLE,ARROW
    private int strokeWidth ;
    private String color ;


    public Figure(UUID boardId, List<Position> positionList, int strokeWidth, String color) {
        super(UUID.randomUUID());
        this.boardId = boardId;
        this.positionList = positionList;
        this.srcArrowKind = ArrowKindEnum.NONE;
        this.destArrowKind = ArrowKindEnum.NONE;
        this.strokeWidth = strokeWidth;
        this.color = color;
    }

    public Figure(UUID boardId,UUID figureId ,List<Position> positionList, int strokeWidth, String color) {
        super(figureId);
        this.boardId = boardId;
        this.positionList = positionList;
        this.srcArrowKind = ArrowKindEnum.NONE;
        this.destArrowKind = ArrowKindEnum.NONE;
        this.strokeWidth = strokeWidth;
        this.color = color;
    }

    public  UUID getFigureId(){
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
}
