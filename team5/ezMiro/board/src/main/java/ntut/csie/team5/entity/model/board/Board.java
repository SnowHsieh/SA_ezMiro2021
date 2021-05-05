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
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.getOrder();

        CommittedFigure nextCommittedFigure = committedFigures.stream().filter(cf ->  cf.getOrder() == (order + 1)).findAny().orElse(null);

        if(nextCommittedFigure == null)
            return;

        committedFigure.setOrder(order + 1);
        nextCommittedFigure.setOrder(order);
        committedFigures.set(order, nextCommittedFigure);
        committedFigures.set(order + 1, committedFigure);
    }

    public void moveFigureOrderBack(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.getOrder();

        CommittedFigure previousCommittedFigure = committedFigures.stream().filter(cf ->  cf.getOrder() == (order - 1)).findAny().orElse(null);

        if(previousCommittedFigure == null)
            return;

        committedFigure.setOrder(order - 1);
        previousCommittedFigure.setOrder(order);
        committedFigures.set(order, previousCommittedFigure);
        committedFigures.set(order - 1, committedFigure);
    }

    public void moveFigureOrderFrontEnd(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.getOrder();

        for (int i = order; i < committedFigures.size(); i++)
        {
            int nextOrder = i;
            CommittedFigure nextCommittedFigure = committedFigures.stream().filter(cf ->  cf.getOrder() == (nextOrder + 1)).findAny().orElse(null);

            if(nextCommittedFigure == null) {
                committedFigure.setOrder(committedFigures.size() - 1);
                committedFigures.set(committedFigures.size() - 1, committedFigure);
                return;
            }

            nextCommittedFigure.setOrder(nextOrder);
            committedFigures.set(nextOrder, nextCommittedFigure);
        }
    }

    public void moveFigureOrderBackEnd(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.getOrder();

        for (int i = order; i >= 0; i--)
        {
            int previousOrder = i;
            CommittedFigure previousCommittedFigure = committedFigures.stream().filter(cf ->  cf.getOrder() == (previousOrder - 1)).findAny().orElse(null);

            if(previousCommittedFigure == null) {
                committedFigure.setOrder(0);
                committedFigures.set(0, committedFigure);
                return;
            }

            previousCommittedFigure.setOrder(previousOrder);
            committedFigures.set(previousOrder, previousCommittedFigure);
        }
    }
}
