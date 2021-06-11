package ntut.csie.islab.miro.entity.model.figure.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.entity.model.figure.line.event.*;


import java.util.List;
import java.util.UUID;

public class Line extends Figure {
    private List<Position> positionList;
    private UUID srcConnectableFigureId;
    private UUID destConnectableFigureId;
    private ArrowKindEnum srcArrowKind; //NONE,CIRCLE,ARROW
    private ArrowKindEnum destArrowKind; //NONE,CIRCLE,ARROW
    private int strokeWidth;
    private String color;

    public Line(UUID boardId, List<Position> positionList, int strokeWidth, String color) {
        super(boardId);
        this.positionList = positionList;
        this.srcArrowKind = ArrowKindEnum.NONE;
        this.destArrowKind = ArrowKindEnum.NONE;
        this.strokeWidth = strokeWidth;
        this.color = color;
        addDomainEvent(new LineCreatedDomainEvent(boardId, getId()));
    }

    public Line(UUID boardId, UUID lineId, List<Position> positionList, int strokeWidth, String color) {
        super(boardId, lineId);
        this.positionList = positionList;
        this.srcArrowKind = ArrowKindEnum.NONE;
        this.destArrowKind = ArrowKindEnum.NONE;
        this.strokeWidth = strokeWidth;
        this.color = color;
        addDomainEvent(new LineCreatedDomainEvent(boardId, getId()));
    }


    @Override
    public List<Position> getPositionList() {
        return positionList;
    }

    @Override
    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    @Override
    public ArrowKindEnum getSrcArrowKind() {
        return srcArrowKind;
    }

    @Override
    public void setSrcArrowKind(ArrowKindEnum srcArrowKind) {
        this.srcArrowKind = srcArrowKind;
    }

    @Override
    public ArrowKindEnum getDestArrowKind() {
        return destArrowKind;
    }

    @Override
    public void setDestArrowKind(ArrowKindEnum destArrowKind) {
        this.destArrowKind = destArrowKind;
    }

    @Override
    public int getStrokeWidth() {
        return strokeWidth;
    }

    @Override
    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public UUID getSrcConnectableFigureId() {
        return srcConnectableFigureId;
    }

    @Override
    public void setSrcConnectableFigureId(UUID srcConnectableFigureId) {
        this.srcConnectableFigureId = srcConnectableFigureId;
    }

    @Override
    public UUID getDestConnectableFigureId() {
        return destConnectableFigureId;
    }

    @Override
    public void setDestConnectableFigureId(UUID destConnectableFigureId) {
        this.destConnectableFigureId = destConnectableFigureId;
    }


    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public void setPosition(Position position) {

    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public Style getStyle() {
        return null;
    }

    @Override
    public void setStyle(Style style) {

    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void markAsRemoved(UUID boardId, UUID figureId) {
        addDomainEvent(new LineDeletedDomainEvent(boardId, figureId));
    }

    @Override
    public void changeContent(String newContent) {

    }

    @Override
    public void changePosition(Position newPosition) {

    }

    @Override
    public void changeColor(String newColor) {

    }

    @Override
    public void resize(Double newWidth, Double newHeight) {

    }

    @Override
    public FigureTypeEnum getKind() {
        return FigureTypeEnum.LINE;
    }

    @Override
    public void changeLinePath(List<Position> positionList) {
        List<Position> originalPositionList = this.getPositionList();
        this.setPositionList(positionList);
        addDomainEvent(new LinePathChangedDomainEvent(this.getBoardId(), this.getFigureId(), originalPositionList, positionList));

    }

    @Override
    public void attachConnectableFigure(UUID figureId, String attachEndPointKind) {
        if (attachEndPointKind.equals("source")) {
            this.setSrcConnectableFigureId(figureId);
        } else if (attachEndPointKind.equals("destination")) {
            this.setDestConnectableFigureId(figureId);
        } else {
            //todo: alert bug
        }
        addDomainEvent(new ConnectableFigureAttachedByLineDomainEvent(this.getBoardId(), this.getFigureId(), this.getSrcConnectableFigureId(), this.getDestConnectableFigureId()));
    }

    @Override
    public void unattachConnectableFigure(String attachEndPointKind) {
        UUID unattachedConnectableFigureId = null;


        if (attachEndPointKind.equals("source")) {
            unattachedConnectableFigureId = this.getSrcConnectableFigureId();
            this.setSrcConnectableFigureId(null);
        } else if (attachEndPointKind.equals("destination")) {
            unattachedConnectableFigureId = this.getDestConnectableFigureId();
            this.setDestConnectableFigureId(null);
        } else {
            //todo: alert bug
        }
        addDomainEvent(new ConnectableFigureUnattachedDomainEvent(this.getBoardId(), this.getFigureId(), unattachedConnectableFigureId, attachEndPointKind));
    }


}
