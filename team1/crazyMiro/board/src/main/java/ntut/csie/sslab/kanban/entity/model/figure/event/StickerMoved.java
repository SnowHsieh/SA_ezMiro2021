package ntut.csie.sslab.kanban.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;

import java.util.Date;

public class StickerMoved extends DomainEvent {
    private String figureId;
    private Coordinate position;

    public StickerMoved(String figureId, Coordinate position) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.position = position;
    }

    public String getFigureId() {
        return figureId;
    }

    public Coordinate getPosition() {
        return position;
    }
}
