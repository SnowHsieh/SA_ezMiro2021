package ntut.csie.sslab.kanban.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorDeleted;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NotifyBoardSessionBroadcaster {

    private BoardSessionBroadcaster boardSessionBroadcaster;
    private CursorRepository cursorRepository;


    public NotifyBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcasters, CursorRepository cursorRepository) {
        this.boardSessionBroadcaster = boardSessionBroadcasters;
        this.cursorRepository = cursorRepository;
    }

    public void whenCursorCreated(CursorCreated cursorCreated) {
        broadcast(cursorCreated, cursorCreated.getSessionId());
    }


    public void whenCursorMoved(CursorMoved cursorMoved) {
        Cursor cursor = cursorRepository.findById(cursorMoved.getCursorId()).get();
        List<Cursor> cursors = cursorRepository.getCursorByBoardId(cursor.getBoardId());
        List<String> sessionIds = new ArrayList<>();
        cursors.forEach(each->sessionIds.add(each.getSessionId()));
        sessionIds.forEach(each->broadcast(cursorMoved, each));
    }


    public void whenCursorDeleted(CursorDeleted cursorDeleted) {
        Cursor cursor = cursorRepository.findById(cursorDeleted.getCursorId()).get();
        List<Cursor> cursors = cursorRepository.getCursorByBoardId(cursor.getBoardId());
        List<String> sessionIds = new ArrayList<>();
        cursors.forEach(each->sessionIds.add(each.getSessionId()));
        sessionIds.forEach(each->broadcast(cursorDeleted, each));
    }

    private void broadcast(DomainEvent domainEvent, String sessionId){
        boardSessionBroadcaster.broadcast(domainEvent, sessionId);
    }
}