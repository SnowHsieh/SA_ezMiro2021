package ntut.csie.islab.miro.entity.model.figure.connectablefigure;


import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.ArrowKindEnum;

import java.util.List;
import java.util.UUID;

public abstract class ConnectableFigure extends Figure {
    private Position position;
    private String content;
    private double width;
    private double height;
    private Style style;

    public ConnectableFigure(UUID boardId, Position position, String content, double width, double height, Style style) {
        super(boardId);
        this.position = position;
        this.content = content;
        this.width = width;
        this.height = height;
        this.style = style;
    }

    public ConnectableFigure(UUID boardId, UUID stickyNoteId, Position position, String content, double width, double height, Style style) {
        super(boardId, stickyNoteId);
        this.position = position;
        this.content = content;
        this.width = width;
        this.height = height;
        this.style = style;
    }

    public abstract void markAsRemoved(UUID boardId, UUID figureId);

    public abstract void changeContent(String newContent);

    public abstract void changePosition(Position newPosition);

    public abstract void changeColor(String newColor);

    public abstract void resize(Double newWidth, Double newHeight);


    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public List<Position> getPositionList() {
        return null;
    }

    @Override
    public void setPositionList(List<Position> positionList) {

    }

    @Override
    public ArrowKindEnum getSrcArrowKind() {
        return null;
    }

    @Override
    public void setSrcArrowKind(ArrowKindEnum srcArrowKind) {

    }

    @Override
    public ArrowKindEnum getDestArrowKind() {
        return null;
    }

    @Override
    public void setDestArrowKind(ArrowKindEnum destArrowKind) {

    }

    @Override
    public int getStrokeWidth() {
        return 0;
    }

    @Override
    public void setStrokeWidth(int strokeWidth) {

    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public void setColor(String color) {

    }

    @Override
    public UUID getSrcConnectableFigureId() {
        return null;
    }

    @Override
    public void setSrcConnectableFigureId(UUID srcConnectableFigureId) {

    }

    @Override
    public UUID getDestConnectableFigureId() {
        return null;
    }

    @Override
    public void setDestConnectableFigureId(UUID destConnectableFigureId) {

    }
}
