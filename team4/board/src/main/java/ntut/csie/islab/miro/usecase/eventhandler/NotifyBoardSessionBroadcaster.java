package ntut.csie.islab.miro.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.islab.miro.entity.model.figure.line.event.*;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.event.*;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.BoardSession;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorMovedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.BoardEnteredDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.BoardLeftDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ConnectableFigure;
import ntut.csie.islab.miro.usecase.websocket.BoardSessionBroadcaster;
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
    public void whenStickyNoteCreated(StickyNoteCreatedDomainEvent stickyNoteCreatedDomainEvent) {
        Board board = boardRepository.findById(stickyNoteCreatedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(stickyNoteCreatedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickyNoteResized(StickyNoteResizedDomainEvent stickyNoteResizedDomainEvent) {
        ConnectableFigure figure = figureRepository.findById(stickyNoteResizedDomainEvent.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(stickyNoteResizedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickyNoteMoved(StickyNoteMovedDomainEvent stickyNoteMovedDomainEvent) {
        UUID figureId = stickyNoteMovedDomainEvent.getFigureId();
        ConnectableFigure figure = figureRepository.findById(figureId).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(stickyNoteMovedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickyNoteContentChanged(StickyNoteContentChangedDomainEvent stickyNoteContentChangedDomainEvent) {
        Board board = boardRepository.findById(stickyNoteContentChangedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(stickyNoteContentChangedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickyNoteColorChanged(StickyNoteColorChangedDomainEvent stickyNoteColorChangedDomainEvent) {
        Board board = boardRepository.findById(stickyNoteColorChangedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(stickyNoteColorChangedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenStickyNoteDeleted(StickyNoteDeletedDomainEvent stickyNoteDeletedDomainEvent) {
        Board board = boardRepository.findById(stickyNoteDeletedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(stickyNoteDeletedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenCursorMoved(CursorMovedDomainEvent cursorMovedDomainEvent) {
        Board board = boardRepository.findById(cursorMovedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(cursorMovedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenBoardEntered(BoardEnteredDomainEvent boardEnteredDomainEvent) {
        Board board = boardRepository.findById(boardEnteredDomainEvent.boardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(boardEnteredDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenBoardLeft(BoardLeftDomainEvent boardLeftDomainEvent) {
        Board board = boardRepository.findById(boardLeftDomainEvent.boardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(boardLeftDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenLineCreated(LineCreatedDomainEvent lineCreatedDomainEvent) {
        Board board = boardRepository.findById(lineCreatedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(lineCreatedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenLineDeleted(LineDeletedDomainEvent lineDeletedDomainEvent) {
        Board board = boardRepository.findById(lineDeletedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(lineDeletedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenLinePathChanged(LinePathChangedDomainEvent linePathChangedDomainEvent) {
        Board board = boardRepository.findById(linePathChangedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(linePathChangedDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenConnectableFigureAttachedByLine(ConnectableFigureAttachedByLineDomainEvent connectablefigureAttachedByLineDomainEvent) {
        Board board = boardRepository.findById(connectablefigureAttachedByLineDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(connectablefigureAttachedByLineDomainEvent, each.getBoardSessionId().getId()));
    }

    @Subscribe
    public void whenConnectableFigureUnattachedByLine(ConnectableFigureUnattachedDomainEvent connectableFigureUnattachedDomainEvent) {
        Board board = boardRepository.findById(connectableFigureUnattachedDomainEvent.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessionList();
        boardSessions.forEach(each -> broadcast(connectableFigureUnattachedDomainEvent, each.getBoardSessionId().getId()));
    }

    private void broadcast(DomainEvent domainEvent, String sessionId) {
        boardSessionBroadcaster.broadcast(domainEvent, sessionId);

    }

}
