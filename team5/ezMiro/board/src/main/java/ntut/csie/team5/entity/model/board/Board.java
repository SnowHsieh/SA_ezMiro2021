package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.event.*;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot<String> {

    private String name;
    private String projectId;
    private final List<CommittedFigure> committedFigures;
    private final List<BoardSession> boardSessions;

    public Board(String boardId, String name, String projectId) {
        super(boardId);
        this.name = name;
        this.projectId = projectId;
        this.committedFigures = new ArrayList<>();
        this.boardSessions = new ArrayList<>();

        addDomainEvent(new BoardCreated(boardId, name, projectId));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoardId() {
        return getId();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void addFigure(String figureId) {
        CommittedFigure committedFigure = new CommittedFigure(figureId, getBoardId(), committedFigures.size());
        committedFigures.add(committedFigure);
    }

    public void commitFigure(String figureId) {
        addFigure(figureId);
        addDomainEvent(new FigureCommitted(figureId, getBoardId()));
    }

    public void removeFigure(String figureId) {
        for(int i = 0; i < committedFigures.size(); i++){
            if(committedFigures.get(i).figureId().equals(figureId)) {
                committedFigures.remove(i);
                break;
            }
        }
    }

    public void uncommitFigure(String figureId) {
        removeFigure(figureId);
        addDomainEvent(new FigureUncommitted(figureId, getBoardId()));
    }

    public List<CommittedFigure> getCommittedFigures() {
        return committedFigures;
    }

    public void changeFigureZOrder(String figureId, ZOrderType zOrderType) {
        switch (zOrderType) {
            case BRING_FORWARD:
                bringFigureForward(figureId);
                break;
            case SEND_BACKWARDS:
                sendFigureBackwards(figureId);
                break;
            case BRING_TO_FRONT:
                bringFigureToFront(figureId);
                break;
            case SEND_TO_BACK:
                sendFigureToBack(figureId);
                break;
        }

        addDomainEvent(new FigureZOrderChanged(figureId, getBoardId(), zOrderType));
    }

    public void bringFigureForward(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.figureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int zOrder = committedFigure.zOrder();

        CommittedFigure nextCommittedFigure = committedFigures.stream().filter(cf ->  cf.zOrder() == (zOrder + 1)).findAny().orElse(null);

        if(nextCommittedFigure == null)
            return;

        committedFigures.set(zOrder, new CommittedFigure(nextCommittedFigure.figureId(), nextCommittedFigure.boardId(), zOrder));
        committedFigures.set(zOrder + 1, new CommittedFigure(committedFigure.figureId(), committedFigure.boardId(), zOrder + 1));
    }

    public void sendFigureBackwards(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.figureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int zOrder = committedFigure.zOrder();

        CommittedFigure previousCommittedFigure = committedFigures.stream().filter(cf ->  cf.zOrder() == (zOrder - 1)).findAny().orElse(null);

        if(previousCommittedFigure == null)
            return;

        committedFigures.set(zOrder, new CommittedFigure(previousCommittedFigure.figureId(), previousCommittedFigure.boardId(), zOrder));
        committedFigures.set(zOrder - 1, new CommittedFigure(committedFigure.figureId(), committedFigure.boardId(), zOrder - 1));
    }

    public void bringFigureToFront(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.figureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.zOrder();

        for (int i = order; i < committedFigures.size(); i++)
        {
            int nextZOrder = i;
            CommittedFigure nextCommittedFigure = committedFigures.stream().filter(cf ->  cf.zOrder() == (nextZOrder + 1)).findAny().orElse(null);

            if(nextCommittedFigure == null) {
                committedFigures.set(committedFigures.size() - 1, new CommittedFigure(committedFigure.figureId(), committedFigure.boardId(), committedFigures.size() - 1));
                return;
            }

            committedFigures.set(nextZOrder, new CommittedFigure(nextCommittedFigure.figureId(), nextCommittedFigure.boardId(), nextZOrder));
        }
    }

    public void sendFigureToBack(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.figureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.zOrder();

        for (int i = order; i >= 0; i--)
        {
            int previousZOrder = i;
            CommittedFigure previousCommittedFigure = committedFigures.stream().filter(cf ->  cf.zOrder() == (previousZOrder - 1)).findAny().orElse(null);

            if(previousCommittedFigure == null) {
                committedFigures.set(0, new CommittedFigure(committedFigure.figureId(), committedFigure.boardId(), 0));
                return;
            }

            committedFigures.set(previousZOrder, new CommittedFigure(previousCommittedFigure.figureId(), previousCommittedFigure.boardId(), previousZOrder));
        }
    }

    public void addBoardSession(String boardSessionId, String userId) {
        BoardSession boardSession = new BoardSession(getBoardId(), boardSessionId, userId);
        boardSessions.add(boardSession);
    }

    public String acceptUserEntry(String userId) {
        String boardSessionId = UUID.randomUUID().toString();
        addBoardSession(boardSessionId, userId);
        addDomainEvent(new BoardEntered(getBoardId(), boardSessionId, userId));
        return boardSessionId;
    }

    public void acceptUserLeaving(String boardSessionId, String userId) {
        for(int i = 0; i < boardSessions.size(); i++){
            if(boardSessions.get(i).boardSessionId().equals(boardSessionId)) {
                boardSessions.remove(i);
                break;
            }
        }

        addDomainEvent(new BoardLeft(getBoardId(), boardSessionId, userId));
    }

    public List<BoardSession> getBoardSessions() {
        return boardSessions;
    }

    public BoardSession getBoardSession(String boardSessionId) {
        BoardSession boardSession = boardSessions.stream().filter(bs ->  bs.boardSessionId().equals(boardSessionId)).findAny().orElse(null);
        return boardSession;
    }

    public void moveCursor(String boardSessionId, int positionX, int positionY) {
        BoardSession boardSession = boardSessions.stream().filter(bs ->  bs.boardSessionId().equals(boardSessionId)).findAny().orElse(null);

        if(null == boardSession)
            throw new NullPointerException("Move cursor error: board session not exist");

        addDomainEvent(new CursorMoved(getBoardId(), boardSession.boardSessionId(), boardSession.userId(), positionX, positionY));
    }
}
