package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.cursor.Cursor;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorDeletedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorMovedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.*;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static ntut.csie.sslab.ddd.model.common.Contract.ensure;
import static ntut.csie.sslab.ddd.model.common.Contract.requireNotNull;

public class Board extends AggregateRoot<UUID> {
    private UUID teamId;
    private String boardName;
    private List<CommittedFigure> figureList;
    private List<Cursor> cursorList;
    private List<BoardSession> boardSessionList;

    public Board(UUID teamId, String boardName) {
        super(UUID.randomUUID());
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<CommittedFigure>();
        this.cursorList = new ArrayList<Cursor>();
        this.boardSessionList = new ArrayList<BoardSession>();
        addDomainEvent(new BoardCreatedDomainEvent(teamId, getBoardId()));

    }

    public Board(UUID teamId, UUID boardId, String boardName) {
        super(boardId);
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<CommittedFigure>();
        this.cursorList = new ArrayList<Cursor>();
        this.boardSessionList = new ArrayList<BoardSession>();
        addDomainEvent(new BoardCreatedDomainEvent(teamId, getBoardId()));

    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID temaId) {
        this.teamId = temaId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public UUID getBoardId() {
        return this.getId();
    }

    public void commitFigure(UUID figureId) {
        addFigure(figureId);
        addDomainEvent(new FigureCommittedDomainEvent(getBoardId(), figureId));

    }

    public void uncommitFigure(UUID figureId) {
        requireNotNull("figureId id", figureId);

        removeFigure(figureId);
        addDomainEvent(new FigureUncommittedDomainEvent(getBoardId(), figureId));

    }

    private void addFigure(UUID figureId) {
        CommittedFigure committedFigure = new CommittedFigure(getBoardId(), figureId);
        this.figureList.add(committedFigure);
    }

    private void removeFigure(UUID figureId) {
        for (int i = 0; i < figureList.size(); i++) {
            if (figureList.get(i).getFigureId().equals(figureId)) {
                figureList.remove(i);
            }
        }
    }


    public List<CommittedFigure> getCommittedFigures() {
        return this.figureList;
    }

    public void setCommittedFigureListOrder(List<UUID> figureOrderList) {

        List<CommittedFigure> newCommittedFigureList = new ArrayList<CommittedFigure>();
        for (UUID figureId : figureOrderList) {
            if (figureList.stream().filter(s -> s.getFigureId().equals(figureId)).findFirst().isPresent()) {
                newCommittedFigureList.add(new CommittedFigure(this.getBoardId(), figureId));
            }
        }
        figureList = newCommittedFigureList;

        addDomainEvent(new FigureChangedDomainEvent(this.getBoardId(), this.figureList));

    }


    public List<BoardSession> getBoardSessionList() {
        return boardSessionList;
    }

    public void setBoardSessionList(List<BoardSession> boardSessionList) {
        this.boardSessionList = boardSessionList;
    }

    public List<Cursor> getCursorList() {
        return cursorList;
    }

    private void createCursor(UUID userId) {
        Cursor cursor = new Cursor(userId, this.getBoardId());
        cursorList.add(cursor);
        addDomainEvent(new CursorCreatedDomainEvent(getBoardId(), userId));
    }

    private void deleteCursor(UUID userId) {
        for (int i = 0; i < cursorList.size(); i++) {
            if (cursorList.get(i).getUserId().equals(userId)) {
                cursorList.remove(i);
                break;
            }
        }

        addDomainEvent(new CursorDeletedDomainEvent(getBoardId(), userId));
    }

    public void acceptUserEntry(BoardSessionId boardSessionId, UUID userId) {
        BoardSession boardSession = new BoardSession(getBoardId(), userId, boardSessionId);
        boardSessionList.add(boardSession);
        createCursor(userId);

        addDomainEvent(new BoardEnteredDomainEvent(boardSession.getBoardId(), boardSession.getUserId(), boardSession.getBoardSessionId()));
    }

    public void acceptUserLeaving(BoardSessionId boardSessionId, UUID userId) {
        for (int i = 0; i < boardSessionList.size(); i++) {
            if (boardSessionList.get(i).getBoardSessionId().equals(boardSessionId)) {
                boardSessionList.remove(i);
                break;
            }
        }
        deleteCursor(userId);
        addDomainEvent(new BoardLeftDomainEvent(getBoardId(), userId, boardSessionId));
    }

    public void setCursorPosition(UUID userId, Position newPosition) {
        Cursor cursor = this.getCursorList().stream().filter(x -> x.getUserId().equals(userId)).findFirst().get();
        Position oldPosition = cursor.getPosition();
        cursor.setPosition(newPosition);
        addDomainEvent(new CursorMovedDomainEvent(getBoardId(), cursor.getUserId(), oldPosition, newPosition));
    }
}
