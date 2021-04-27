package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

public class FigureDto {
    private String figureId;
    private String content;
    private int size;
    private String color;
    private Coordinate position;

    public FigureDto(String figureId, String content, int size, String color, Coordinate position) {
        this.figureId = figureId;
        this.content = content;
        this.size = size;
        this.color = color;
        this.position = position;
    }

    public String getFigureId() {
        return figureId;
    }

    public String getContent() {
        return content;
    }

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public Coordinate getPosition() {
        return position;
    }
}
