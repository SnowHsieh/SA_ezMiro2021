package ntut.csie.sslab.kanban.entity.model.figure.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class StickerBroughtToFront extends DomainEvent {
    private String boardId;
    private String figureId;

    public StickerBroughtToFront(String boardId, String figureId) {
        super(DateProvider.now());
        this.boardId = boardId;
        this.figureId = figureId;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getFigureId() {
        return figureId;
    }
}
