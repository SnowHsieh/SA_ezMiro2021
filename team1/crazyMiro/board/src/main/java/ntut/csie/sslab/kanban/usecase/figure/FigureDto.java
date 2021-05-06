package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

public class FigureDto {
    private String figureId;
    private String content;
    private int width;
    private int length;
    private String color;
    private Coordinate position;

    public FigureDto(String figureId, String content, int width, int length, String color, Coordinate position) {
        this.figureId = figureId;
        this.content = content;
        this.width = width;
        this.length = length;
        this.color = color;
        this.position = position;
    }

    public String getFigureId() {
        return figureId;
    }

    public String getContent() {
        return content;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getColor() {
        return color;
    }

    public Coordinate getPosition() {
        return position;
    }
}
