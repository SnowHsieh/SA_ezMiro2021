package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class StickerSizeChanged extends DomainEvent {
    private String figureId;
    private int width;
    private int length;

    public StickerSizeChanged(String figureId, int width, int length) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.width = width;
        this.length = length;
    }

    public String getFigureId() {
        return figureId;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
