package ntut.csie.sslab.miro.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardSession;
import ntut.csie.sslab.miro.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.miro.entity.model.board.event.FigureBroughtToFront;
import ntut.csie.sslab.miro.entity.model.board.event.FigureSentToBack;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorCreated;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorDeleted;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorMoved;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorShowed;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.event.*;
import ntut.csie.sslab.miro.entity.model.figure.event.LineCreated;
import ntut.csie.sslab.miro.entity.model.figure.event.LineDeleted;
import ntut.csie.sslab.miro.entity.model.figure.event.LineMoved;
import ntut.csie.sslab.miro.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.figure.StickerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class NotifyBoardSessionBroadcaster {

    private BoardSessionBroadcaster boardSessionBroadcaster;
    private BoardRepository boardRepository;
    private StickerRepository stickerRepository;

    @Autowired
    public NotifyBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcasters, BoardRepository boardRepository, StickerRepository stickerRepository) {
        this.boardSessionBroadcaster = boardSessionBroadcasters;
        this.boardRepository = boardRepository;
        this.stickerRepository = stickerRepository;
    }

    @Subscribe
    public void whenStickerCreated(StickerCreated stickerCreated){
        Board board = boardRepository.findById(stickerCreated.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerCreated, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerMoved(StickerMoved stickerMoved){
        Figure figure = stickerRepository.findById(stickerMoved.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions = boardSessions.stream().filter(x->!x.getUserId().equals(stickerMoved.getUserId())).collect(Collectors.toList());
        boardSessions.forEach(each->broadcast(stickerMoved, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerContentChanged(StickerContentChanged stickerContentChanged){
        Figure figure = stickerRepository.findById(stickerContentChanged.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerContentChanged, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerColorChanged(StickerColorChanged stickerColorChanged){
        Figure figure = stickerRepository.findById(stickerColorChanged.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerColorChanged, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerSizeChanged(StickerSizeChanged stickerSizeChanged){
        Figure figure = stickerRepository.findById(stickerSizeChanged.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerSizeChanged, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerDeleted(StickerDeleted stickerDeleted){
        Board board = boardRepository.findById(stickerDeleted.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerDeleted, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenFigureBroughtToFront(FigureBroughtToFront figureBroughtToFront){
        Board board = boardRepository.findById(figureBroughtToFront.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(figureBroughtToFront, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenFigureSentToBack(FigureSentToBack figureSentToBack){
        Board board = boardRepository.findById(figureSentToBack.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(figureSentToBack, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenCursorCreated(CursorCreated cursorCreated) {
        Board board = boardRepository.findById(cursorCreated.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        if(boardSessions.stream().filter(x->x.getUserId().equals(cursorCreated.getUserId())).collect(Collectors.toList()).size() > 1)
            return;
        boardSessions.forEach(each->broadcast(cursorCreated, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenCursorMoved(CursorMoved cursorMoved) {
        Board board = boardRepository.findById(cursorMoved.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions = boardSessions.stream().filter(x->!x.getUserId().equals(cursorMoved.getUserId())).collect(Collectors.toList());
        boardSessions.forEach(each->broadcast(cursorMoved, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenCursorShowed(CursorShowed cursorShowed) {
        Board board = boardRepository.findById(cursorShowed.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions = boardSessions.stream().filter(x->!x.getUserId().equals(cursorShowed.getUserId())).collect(Collectors.toList());
        boardSessions.forEach(each->broadcast(cursorShowed, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenCursorDeleted(CursorDeleted cursorDeleted) {
        Board board = boardRepository.findById(cursorDeleted.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        if(boardSessions.stream().anyMatch(x->x.getUserId().equals(cursorDeleted.getUserId())))
            return;
        boardSessions = boardSessions.stream().filter(x->!x.getUserId().equals(cursorDeleted.getUserId())).collect(Collectors.toList());
        boardSessions.forEach(each->broadcast(cursorDeleted, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenLineCreated(LineCreated lineCreated) {
        Board board = boardRepository.findById(lineCreated.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(lineCreated, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenLineDeleted(LineDeleted lineDeleted) {
        Board board = boardRepository.findById(lineDeleted.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(lineDeleted, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenLineMoved(LineMoved lineMoved) {
        Board board = boardRepository.findById(lineMoved.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(lineMoved, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenLineTargetPositionChanged(LineTargetPositionChanged LineTargetPositionChanged) {
        Board board = boardRepository.findById(LineTargetPositionChanged.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(LineTargetPositionChanged, each.getBoardSessionId()));
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