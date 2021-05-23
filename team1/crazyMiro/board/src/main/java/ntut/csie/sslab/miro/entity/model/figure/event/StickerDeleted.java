package ntut.csie.sslab.miro.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class StickerDeleted extends DomainEvent {
    private String figureId;
    private String boardId;

    public StickerDeleted(String boardId, String figureId) {
        super(DateProvider.now());
        this.figureId = figureId;
        this.boardId = boardId;
    }

    public String getFigureId() {
        return figureId;
    }

    public String getBoardId() {
        return boardId;
    }
}
