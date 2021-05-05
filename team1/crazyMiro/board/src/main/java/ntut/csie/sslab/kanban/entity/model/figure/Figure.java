package ntut.csie.sslab.kanban.entity.model.figure;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.entity.model.figure.event.FigureSentToBack;
import ntut.csie.sslab.kanban.entity.model.figure.event.FigureBroughtToFront;


public abstract class Figure extends AggregateRoot<String> {

    private String boardId;
    private String figureId;
    private String content;
    private int size;
    private String color;
    private Coordinate position;
    private int order;

    public Figure(String boardId, String figureId, String content, int size, String color, Coordinate position, int order) {
        super(figureId);
        this.boardId = boardId;
        this.figureId = figureId;
        this.content = content;
        this.size = size;
        this.color = color;
        this.position = position;
        this.order = order;
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

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void bringToFront(int frontIndex) {
        setOrder(frontIndex);
        addDomainEvent(new FigureBroughtToFront(boardId, figureId));
    }

    public void sendToBack() {
        setOrder(0);
        addDomainEvent(new FigureSentToBack(boardId, figureId));
    }
}
