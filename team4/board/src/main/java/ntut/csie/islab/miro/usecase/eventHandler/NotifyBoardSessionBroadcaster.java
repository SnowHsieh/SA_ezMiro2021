package ntut.csie.islab.miro.usecase.eventHandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.BoardSession;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorDeletedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorMovedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.BoardEnteredDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.BoardLeftDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteContentChangedDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteMovedDomainEvent;
import ntut.csie.islab.miro.usecase.webSocket.BoardSessionBroadcaster;
import ntut.csie.sslab.ddd.model.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class NotifyBoardSessionBroadcaster {

    private BoardSessionBroadcaster boardSessionBroadcaster;
    private BoardRepository boardRepository;
    private StickyNoteRepository figureRepository;

    @Autowired
    public NotifyBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcasters, BoardRepository boardRepository, StickyNoteRepository figureRepository) {
        this.boardSessionBroadcaster = boardSessionBroadcasters;
        this.boardRepository = boardRepository;
        this.figureRepository = figureRepository;
    }

    @Subscribe
    public void whenStickyNoteCreated(StickyNoteCreatedDomainEvent stickyNoteCreatedDomainEvent){
        Board board = boardRepository.findById(stickyNoteCreatedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each->broadcast(stickyNoteCreatedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickyNoteMoved(StickyNoteMovedDomainEvent stickyNoteMovedDomainEvent){
        UUID boardId = stickyNoteMovedDomainEvent.getBoardId();
        UUID figureId = stickyNoteMovedDomainEvent.getFigureId();
        TextFigure figure = figureRepository.findById(figureId).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each->broadcast(stickyNoteMovedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickyNoteContentChanged(StickyNoteContentChangedDomainEvent stickyNoteContentChangedDomainEvent){
        UUID boardId = stickyNoteContentChangedDomainEvent.getBoardId();
        UUID figureId = stickyNoteContentChangedDomainEvent.getFigureId();
        TextFigure figure = figureRepository.findById(figureId).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each->broadcast(stickyNoteContentChangedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickerDeleted(StickyNoteDeleteDomainEvent stickyNoteDeleteDomainEvent){
        Board board = boardRepository.findById(stickyNoteDeleteDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each->broadcast(stickyNoteDeleteDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenCursorCreated(CursorCreatedDomainEvent cursorCreatedDomainEvent) {
        Board board = boardRepository.findById(cursorCreatedDomainEvent.boardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        System.out.println("whenCursorCreated notify:" + board);
        boardSessions.forEach(each->broadcast(cursorCreatedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenCursorMoved(CursorMovedDomainEvent cursorMovedDomainEvent) {
        Board board = boardRepository.findById(cursorMovedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        System.out.println("whenCursorMoved notify:" + board);
        boardSessions.forEach(each->broadcast(cursorMovedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenCursorDeleted(CursorDeletedDomainEvent cursorDeletedDomainEvent) {
        Board board = boardRepository.findById(cursorDeletedDomainEvent.boardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each->broadcast(cursorDeletedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenBoardEntered(BoardEnteredDomainEvent boardEnteredDomainEvent) {
        Board board = boardRepository.findById(boardEnteredDomainEvent.boardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        System.out.println("whenBoardEntered notify:" + board);
        boardSessions.forEach(each->broadcast(boardEnteredDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenBoardLeft(BoardLeftDomainEvent boardLeftDomainEvent) {
        Board board = boardRepository.findById(boardLeftDomainEvent.boardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each->broadcast(boardLeftDomainEvent, each.getBoardSessionId().getId()));
    }

    private void broadcast(DomainEvent domainEvent, String sessionId){
        boardSessionBroadcaster.broadcast(domainEvent, sessionId);

    }

}
