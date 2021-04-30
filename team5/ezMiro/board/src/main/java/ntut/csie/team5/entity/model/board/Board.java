package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.team5.entity.model.board.event.BoardCreated;
import ntut.csie.team5.entity.model.board.event.FigureCommitted;
import ntut.csie.team5.entity.model.board.event.FigureUncommitted;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        committedFigures.add(new CommittedFigure(figureId, getBoardId(), committedFigures.size()));
    }

    public void uncommitFigure(String figureId) {
        removeFigure(figureId);

        addDomainEvent(new FigureUncommitted(figureId, getBoardId()));
    }

    public void removeFigure(String figureId) {
        for(int i = 0; i < committedFigures.size(); i++){
            if(committedFigures.get(i).getFigureId().equals(figureId)) {
                committedFigures.remove(i);
                break;
            }
        }
    }

    public List<CommittedFigure> getCommittedFigures() {
        return committedFigures;
    }


    public void moveFigureOrderFront(String figureId) {
        Optional<CommittedFigure> committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny();

    }

    public void moveFigureOrderBack(String figureId) {
    }

    public void moveFigureOrderFrontEnd(String figureId) {
    }

    public void moveFigureOrderBackEnd(String figureId) {
    }
}
