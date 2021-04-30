package ntut.csie.sslab.kanban.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

import java.util.Date;

public class StickerSizeChanged extends DomainEvent {
    private String figureId;
    private int size;

    public StickerSizeChanged(String figureId, int size) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.size = size;
    }

    public String getFigureId() {
        return figureId;
    }

    public int getSize() {
        return size;
    }
}
