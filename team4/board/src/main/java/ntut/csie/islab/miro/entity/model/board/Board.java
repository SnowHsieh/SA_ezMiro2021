package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorDeletedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.cursor.event.CursorMovedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.*;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ntut.csie.sslab.ddd.model.common.Contract.requireNotNull;

public class Board extends AggregateRoot<UUID> {
    private UUID teamId;
    private String boardName;
    private List<CommittedFigure> figureList;
    private List<BoardSession> boardSessionList;

    public Board(UUID teamId, String boardName) {
        super(UUID.randomUUID());
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<CommittedFigure>();
        this.boardSessionList = new ArrayList<BoardSession>();
        addDomainEvent(new BoardCreatedDomainEvent(teamId, getBoardId()));

    }

    public Board(UUID teamId, UUID boardId, String boardName) {
        super(boardId);
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<CommittedFigure>();
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

    public void commitFigure(UUID figureId, FigureTypeEnum figureType) {
        addFigure(figureId, figureType);
        addDomainEvent(new FigureCommittedDomainEvent(getBoardId(), figureId));

    }

    public void uncommitFigure(UUID figureId) {
        requireNotNull("figureId id", figureId);
        System.out.println("uncommitFigure:" + figureId);
        removeFigure(figureId);
        addDomainEvent(new FigureUncommittedDomainEvent(getBoardId(), figureId));

    }

    private void addFigure(UUID figureId, FigureTypeEnum figureType) {
        CommittedFigure committedFigure = new CommittedFigure(getBoardId(), figureId, figureType);
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
            Optional<CommittedFigure> committedFigure = figureList.stream().filter(s -> s.getFigureId().equals(figureId)).findFirst();

            if (committedFigure.isPresent()) {
                newCommittedFigureList.add(new CommittedFigure(this.getBoardId(), figureId,committedFigure.get().getFigureType()));
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

    public void acceptUserEntry(BoardSessionId boardSessionId, UUID userId) {
        BoardSession boardSession = new BoardSession(getBoardId(), userId, boardSessionId);
        boardSessionList.add(boardSession);
        addDomainEvent(new CursorCreatedDomainEvent(getBoardId(), userId));
        addDomainEvent(new BoardEnteredDomainEvent(boardSession.getBoardId(), boardSession.getUserId(), boardSession.getBoardSessionId()));
    }

    public void acceptUserLeaving(BoardSessionId boardSessionId, UUID userId) {
        for (int i = 0; i < boardSessionList.size(); i++) {
            if (boardSessionList.get(i).getBoardSessionId().equals(boardSessionId)) {
                boardSessionList.remove(i);
                break;
            }
        }
        addDomainEvent(new CursorDeletedDomainEvent(getBoardId(), userId));
        addDomainEvent(new BoardLeftDomainEvent(getBoardId(), userId, boardSessionId));
    }

    public void setCursorPosition(UUID userId, Position newPosition) {
        BoardSession boardSession = this.getBoardSessionList().stream().filter(x -> x.getUserId().equals(userId)).findFirst().get();
        Position oldPosition = boardSession.getCursorPosition();
        boardSession.setCursorPosition(newPosition);
        addDomainEvent(new CursorMovedDomainEvent(getBoardId(), boardSession.getUserId(), oldPosition, newPosition));
    }
}
