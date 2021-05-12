package ntut.csie.sslab.kanban.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;

import java.util.Optional;

public class NotifyBoardSessionBroadcaster {

    private BoardSessionBroadcaster boardSessionBroadcaster;
    private CursorRepository cursorRepository;


    public NotifyBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcasters, CursorRepository cursorRepository) {
        this.boardSessionBroadcaster = boardSessionBroadcasters;
        this.cursorRepository = cursorRepository;
    }

    @Subscribe
    public void whenCursorCreated(CursorCreated cursorCreated) {
        broadcast(cursorCreated, cursorCreated.getSessionId());
    }


    private void broadcast(DomainEvent domainEvent, String sessionId){

        boardSessionBroadcaster.broadcast(domainEvent, sessionId);

    }
}