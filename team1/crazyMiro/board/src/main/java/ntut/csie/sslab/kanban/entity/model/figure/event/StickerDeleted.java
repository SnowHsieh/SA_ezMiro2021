package ntut.csie.sslab.kanban.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class StickerDeleted extends DomainEvent {
    private String figureId;

    public StickerDeleted(String figureId) {
        super(DateProvider.now());
        this.figureId = figureId;
    }

    public String getFigureId() {
        return figureId;
    }
}
