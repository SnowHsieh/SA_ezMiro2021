package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.miro.entity.model.board.event.BoardCreated;
import java.util.*;

public class Board extends AggregateRoot<String> {
    private String teamId;
    private String boardName;
    private Map<String, CommittedFigure> committedFigures;

    public Board(String teamId, String boardId, String boardName) {
        super(boardId);
        this.teamId = teamId;
        this.boardName = boardName;
        this.committedFigures = new HashMap<>();

        addDomainEvent(new BoardCreated(teamId, boardId, boardName));
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

    public void commitFigure(String boardId, String noteId, int zOrder) {
        committedFigures.put(noteId, new CommittedFigure(boardId, noteId, zOrder));
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
    }

    public void removeNoteFromBoard(String boardId, String noteId) {
        committedFigures.remove(noteId);
    }
}
