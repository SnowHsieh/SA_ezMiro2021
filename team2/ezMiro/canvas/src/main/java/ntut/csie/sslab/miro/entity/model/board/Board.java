package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.miro.entity.model.board.event.*;

import java.util.*;

public class Board extends AggregateRoot<String> {
    private String teamId;
    private String boardName;
    private Map<String, CommittedFigure> committedFigures;
    private BoardChannel boardChannel;

    public Board(String teamId, String boardId, String boardName, BoardChannel boardChannel) {
        super(boardId);
        this.teamId = teamId;
        this.boardName = boardName;
        this.committedFigures = new HashMap<>();
        this.boardChannel = boardChannel;

        addDomainEvent(new BoardEvents.BoardCreated(UUID.randomUUID(), teamId, boardId, boardName, DateProvider.now()));
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public Map<String, CommittedFigure> getCommittedFigures() {
        return committedFigures;
    }

    public String getBoardChannel() {
        return boardChannel.getChannel();
    }

    public void commitFigure(String boardId, String noteId, int zOrder) {
        committedFigures.put(noteId, new CommittedFigure(boardId, noteId, zOrder));
        addDomainEvent(new BoardEvents.FigureCommitted(UUID.randomUUID(), boardId, noteId, DateProvider.now()));
    }

    public void commitNoteToFront(String boardId, String noteId) {
        int order = committedFigures.get(noteId).getZOrder();
        for (Map.Entry<String, CommittedFigure> entry : committedFigures.entrySet()) {
            if(entry.getValue().getZOrder() > order) {
                committedFigures.put(entry.getKey(),
                        new CommittedFigure(boardId, entry.getKey(), entry.getValue().getZOrder() - 1));
            }
        }
        committedFigures.put(noteId, new CommittedFigure(boardId, noteId, committedFigures.size() - 1));
        addDomainEvent(new BoardEvents.FigureCommittedToFront(UUID.randomUUID(), boardId, noteId, DateProvider.now()));
    }

    public void commitNoteToBack(String boardId, String noteId) {
        int order = committedFigures.get(noteId).getZOrder();
        for (Map.Entry<String, CommittedFigure> entry : committedFigures.entrySet()) {
            if(entry.getValue().getZOrder() < order) {
                committedFigures.put(entry.getKey(),
                        new CommittedFigure(boardId, entry.getKey(), entry.getValue().getZOrder() + 1));
            }
        }
        committedFigures.put(noteId, new CommittedFigure(boardId, noteId, 0));
        addDomainEvent(new BoardEvents.FigureCommittedToBack(UUID.randomUUID(), boardId, noteId, DateProvider.now()));
    }

    public void removeNoteFromBoard(String boardId, String noteId) {
        committedFigures.remove(noteId);
        addDomainEvent(new BoardEvents.NoteRemovedFromBoard(UUID.randomUUID(), boardId, noteId, DateProvider.now()));
    }

    public void enter(String userId) {
        addDomainEvent(new BoardEvents.BoardEntered(UUID.randomUUID(), getId(), userId, DateProvider.now()));
    }

    public void leave(String userId) {
        addDomainEvent(new BoardEvents.BoardLeft(UUID.randomUUID(), getId(), userId, DateProvider.now()));
    }
}