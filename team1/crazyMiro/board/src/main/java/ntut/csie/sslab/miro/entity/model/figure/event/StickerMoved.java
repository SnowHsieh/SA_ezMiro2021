package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.miro.entity.model.Coordinate;

public class StickerMoved extends DomainEvent {
    private final String figureId;
    private final Coordinate position;
    private final String userId;

    public StickerMoved(String figureId, Coordinate position, String userId) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.position = position;
        this.userId = userId;
    }

    public String getFigureId() {
        return figureId;
    }

    public Coordinate getPosition() {
        return position;
    }

    public String getUserId() {
        return userId;
    }
}
