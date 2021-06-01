package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.board.event.*;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorCreated;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorDeleted;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorMoved;
import ntut.csie.sslab.miro.entity.model.board.event.cursor.CursorShowed;

import java.util.*;

public class Board extends AggregateRoot<String> {
    private String boardId;
    private String boardName;
    private List<CommittedFigure> committedFigures;
    private List<BoardSession> boardSessions;

    public Board(String boardId, String boardName) {
        super(boardId);
        this.boardId = boardId;
        this.boardName = boardName;
        this.committedFigures = new ArrayList<>();
        this.boardSessions = new ArrayList<>();

        addDomainEvent(new BoardCreated(boardId));
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public List<CommittedFigure> getCommittedFigures() {
        return committedFigures;
    }

    public List<BoardSession> getBoardSessions() {
        return boardSessions;
    }

    public void commitFigure(String figureId) {
        committedFigures.add(new CommittedFigure(figureId, committedFigures.size()));
        addDomainEvent(new FigureCommitted(boardId, figureId));
    }

    public void bringFigureToFront(String figureId) {
        CommittedFigure figure = committedFigures.stream().filter(x-> x.getFigureId().equals(figureId)).findFirst().get();
        List<CommittedFigure> result = new ArrayList<>(committedFigures.subList(0, figure.getZOrder()));
        List<CommittedFigure> orderChangedFigures = committedFigures.subList(figure.getZOrder()+1, committedFigures.size());

        orderChangedFigures.forEach(each-> {
            result.add(new CommittedFigure(each.getFigureId(), result.size()));
        });

        result.add(new CommittedFigure(figureId, result.size()));
        committedFigures = result;
        addDomainEvent(new FigureBroughtToFront(boardId, figureId));
    }

    public void sendFigureToBack(String figureId) {
        CommittedFigure figure = committedFigures.stream().filter(x-> x.getFigureId().equals(figureId)).findFirst().get();
        List<CommittedFigure> orderChangedFigures = committedFigures.subList(0, figure.getZOrder());
        List<CommittedFigure> result = new ArrayList<>();
        List<CommittedFigure> nothingChangedFigures = committedFigures.subList(figure.getZOrder()+1, committedFigures.size());
        result.add(0, new CommittedFigure(figureId, 0));

        orderChangedFigures.forEach(each-> {
            result.add(new CommittedFigure(each.getFigureId(), result.size()));
        });

        result.addAll(nothingChangedFigures);
        committedFigures = result;
        addDomainEvent(new FigureSentToBack(boardId, figureId));
    }

    public void uncommitFigure(String figureId) {
        CommittedFigure figure = committedFigures.stream().filter(x-> x.getFigureId().equals(figureId)).findFirst().get();
        List<CommittedFigure> result = new ArrayList<>(committedFigures.subList(0, figure.getZOrder()));
        List<CommittedFigure> orderChangedFigures = committedFigures.subList(figure.getZOrder()+1, committedFigures.size());

        orderChangedFigures.forEach(each-> {
            result.add(new CommittedFigure(each.getFigureId(), result.size()));
        });

        committedFigures = result;
        addDomainEvent(new FigureUnCommitted(boardId, figureId));
    }

    public void addBoardSession(BoardSession boardSession) {
        boardSessions.add(boardSession);
        addDomainEvent(new BoardEntered(boardSession.getUserId(),
                                        boardSession.getBoardId(),
                                        boardSession.getBoardSessionId()));
        addDomainEvent(new CursorCreated(boardSession.getUserId(), boardSession.getBoardId()));
    }

    public void removeBoardSession(String boardSessionId) {
        BoardSession boardSession = boardSessions.stream().filter(x->x.getBoardSessionId().equals(boardSessionId)).findFirst().get();
        boardSessions.removeIf(x->x.getBoardSessionId().equals(boardSessionId));
        addDomainEvent(new BoardLeft(boardSession.getUserId(), boardId, boardSessionId));
        addDomainEvent(new CursorDeleted(boardId, boardSession.getUserId()));
    }

    public void moveCursor(String userId, Coordinate position) {
        addDomainEvent(new CursorMoved(boardId, userId, position));
    }

    public void showCursor(String userId, Coordinate position) {
        addDomainEvent(new CursorShowed(boardId, userId, position));
    }
}
