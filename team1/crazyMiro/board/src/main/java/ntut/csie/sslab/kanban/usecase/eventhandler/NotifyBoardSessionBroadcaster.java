package ntut.csie.sslab.kanban.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.entity.model.board.BoardSession;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.kanban.entity.model.board.event.FigureBroughtToFront;
import ntut.csie.sslab.kanban.entity.model.board.event.FigureSentToBack;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorCreated;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorDeleted;
import ntut.csie.sslab.kanban.entity.model.cursor.event.CursorMoved;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.entity.model.figure.Sticker;
import ntut.csie.sslab.kanban.entity.model.figure.event.*;
import ntut.csie.sslab.kanban.usecase.BoardSessionBroadcaster;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;

import java.util.ArrayList;
import java.util.List;

public class NotifyBoardSessionBroadcaster {

    private BoardSessionBroadcaster boardSessionBroadcaster;
    private BoardRepository boardRepository;
    private FigureRepository figureRepository;

    @Autowired
    public NotifyBoardSessionBroadcaster(BoardSessionBroadcaster boardSessionBroadcasters, BoardRepository boardRepository, FigureRepository figureRepository) {
        this.boardSessionBroadcaster = boardSessionBroadcasters;
        this.boardRepository = boardRepository;
        this.figureRepository = figureRepository;
    }

    @Subscribe
    public void whenStickerCreated(StickerCreated stickerCreated){
        Board board = boardRepository.findById(stickerCreated.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerCreated, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerMoved(StickerMoved stickerMoved){
        Figure figure = figureRepository.findById(stickerMoved.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerMoved, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerContentChanged(StickerContentChanged stickerContentChanged){
        Figure figure = figureRepository.findById(stickerContentChanged.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerContentChanged, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerColorChanged(StickerColorChanged stickerColorChanged){
        Figure figure = figureRepository.findById(stickerColorChanged.getFigureId()).get();
        Board board = boardRepository.findById(figure.getBoardId()).get();
        List<BoardSession> boardSessions = board.getBoardSessions();
        boardSessions.forEach(each->broadcast(stickerColorChanged, each.getBoardSessionId()));
    }

    @Subscribe
    public void whenStickerSizeChanged(StickerSizeChanged stickerSizeChanged){
        Figure figure = figureRepository.findById(stickerSizeChanged.getFigureId()).get();
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