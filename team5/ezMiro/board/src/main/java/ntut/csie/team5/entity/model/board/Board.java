package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.team5.entity.model.board.event.BoardCreated;
import ntut.csie.team5.entity.model.board.event.FigureCommitted;

import java.util.ArrayList;
import java.util.List;

public class Board extends AggregateRoot<String> {

    private String name;
    private String projectId;
    private final List<CommittedFigure> committedFigures;

    public Board(String boardId, String name, String projectId) {
        super(boardId);
        this.name = name;
        this.projectId = projectId;
        this.committedFigures = new ArrayList<>();

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

    public void commitFigure(String figureId) {
        addFigure(figureId);

        addDomainEvent(new FigureCommitted(figureId, getBoardId()));
    }

    public void addFigure(String figureId) {
        committedFigures.add(new CommittedFigure(figureId, getBoardId()));
    }

    public List<CommittedFigure> getCommittedFigures() {
        return committedFigures;
    }
}
