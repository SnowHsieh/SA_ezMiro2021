package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class StickerContentChanged extends DomainEvent {
    private String figureId;
    private String content;

    public StickerContentChanged(String figureId, String content) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.content = content;
    }

    public String getFigureId() {
        return figureId;
    }

    public String getContent() {
        return content;
    }
}
