package ntut.csie.sslab.miro.entity.model.figure;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.Coordinate;


public abstract class Figure extends AggregateRoot<String> {

    private String boardId;
    private String figureId;
    private String color;

    public Figure(String boardId, String figureId, String color) {
        super(figureId);
        this.boardId = boardId;
        this.figureId = figureId;
        this.color = color;
    }

    public String getFigureId() {
        return figureId;
    }

    public void setFigureId(String figureId) {
        this.figureId = figureId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBoardId() {
        return boardId;
    }

    public abstract FigureType getType();

    public Boolean isDeleted(){return this.isDeleted;}
}
