package ntut.csie.sslab.kanban.entity.model.figure;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.model.Entity;
import ntut.csie.sslab.kanban.entity.model.board.Board;


public abstract class Figure extends AggregateRoot<String> {

    private String boardId;
    private String figureId;
    private String content;
    private int size;
    private String color;
    private Coordinate position;

    public Figure(String boardId, String figureId, String content, int size, String color, Coordinate position) {
        super(figureId);
        this.boardId = boardId;
        this.figureId = figureId;
        this.content = content;
        this.size = size;
        this.color = color;
        this.position = position;
    }

    public String getFigureId() {
        return figureId;
    }

    public void setFigureId(String figureId) {
        this.figureId = figureId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public String getBoardId() {
        return boardId;
    }

    public abstract FigureType getType();

    public Boolean isDeleted(){return this.isDeleted;}
}
