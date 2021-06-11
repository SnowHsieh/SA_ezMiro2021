package ntut.csie.islab.miro.entity.model.figure;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.line.ArrowKindEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.List;
import java.util.UUID;

public abstract class Figure extends AggregateRoot<UUID> {
    private UUID boardId;

    public Figure(UUID boardId) {
        super(UUID.randomUUID());
        this.boardId = boardId;
    }

    public Figure(UUID boardId, UUID figureId) {
        super(figureId);
        this.boardId = boardId;
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

    public abstract List<Position> getPositionList();

    public abstract void setPositionList(List<Position> positionList);

    public abstract ArrowKindEnum getSrcArrowKind();

    public abstract void setSrcArrowKind(ArrowKindEnum srcArrowKind);

    public abstract ArrowKindEnum getDestArrowKind();

    public abstract void setDestArrowKind(ArrowKindEnum destArrowKind);

    public abstract int getStrokeWidth();

    public abstract void setStrokeWidth(int strokeWidth);

    public abstract String getColor();

    public abstract void setColor(String color);


    public abstract UUID getSrcConnectableFigureId();

    public abstract void setSrcConnectableFigureId(UUID srcConnectableFigureId);

    public abstract UUID getDestConnectableFigureId();

    public abstract void setDestConnectableFigureId(UUID destConnectableFigureId);

    //to ConnectableFigure
    public abstract Position getPosition();

    public abstract void setPosition(Position position);

    public abstract String getContent();

    public abstract void setContent(String content);

    public abstract Style getStyle();

    public abstract void setStyle(Style style);

    public abstract double getWidth() ;

    public abstract void setWidth(double width) ;

    public abstract double getHeight() ;

    public abstract void setHeight(double height) ;
    //set DomainEvent
    public abstract void markAsRemoved(UUID boardId, UUID figureId);

    public abstract void changeContent(String newContent);

    public abstract void changePosition(Position newPosition);

    public abstract void changeColor(String newColor);

    public abstract void resize(Double newWidth, Double newHeight);

    public abstract FigureTypeEnum getKind();

    public abstract void changeLinePath(List<Position> positionList);

    public abstract void attachConnectableFigure(UUID figureId, String attachEndPointKind);

    public abstract void unattachConnectableFigure(String attachEndPointKind);
}
