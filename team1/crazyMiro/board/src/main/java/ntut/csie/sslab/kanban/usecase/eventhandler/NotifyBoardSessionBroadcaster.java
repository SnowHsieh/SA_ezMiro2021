package ntut.csie.sslab.kanban.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.entity.model.board.BoardSession;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorDeleted;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class NotifyBoardSessionBroadcaster {

    private BoardSessionBroadcaster boardSessionBroadcaster;
    private BoardRepository boardRepository;

    @Autowired
    public NotifyBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcasters, BoardRepository boardRepository) {
        this.boardSessionBroadcaster = boardSessionBroadcasters;
        this.boardRepository = boardRepository;
    }

    @Subscribe
    public void whenCursorCreated(CursorCreated cursorCreated) {
        Board board = boardRepository.findById(cursorCreated.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(cursorCreated, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenCursorMoved(CursorMoved cursorMoved) {

    }

    @Subscribe
    public void whenCursorDeleted(CursorDeleted cursorDeleted) {

    }

    @Subscribe
    public void whenBoardEntered(BoardEntered boardEntered) {
        Board board = boardRepository.findById(boardEntered.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(boardEntered, each.getBoardSessionId()));
    }

    private void broadcast(DomainEvent domainEvent, String sessionId){
        boardSessionBroadcaster.broadcast(domainEvent, sessionId);
    }
}