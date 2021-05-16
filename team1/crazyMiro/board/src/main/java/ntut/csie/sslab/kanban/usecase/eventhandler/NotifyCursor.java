package ntut.csie.sslab.kanban.usecase.eventhandler;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardLeft;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;

import java.util.UUID;

public class NotifyCursor {
    private CursorRepository cursorRepository;
    private DomainEventBus domainEventBus;

    public NotifyCursor(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.cursorRepository = cursorRepository;
        this.domainEventBus = domainEventBus;
    }

    public void handleBoardEntered(BoardEntered boardEntered) {
        Cursor cursor = new Cursor(boardEntered.getUserId(), boardEntered.getBoardId(), UUID.randomUUID().toString());
        cursorRepository.save(cursor);
        domainEventBus.postAll(cursor);
    }

    public void handleBoardLeft(BoardLeft boardLeft) {
//        cursorRepository.getCursorsByBoardId()
    }
}
