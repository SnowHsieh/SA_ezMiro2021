package ntut.csie.team5.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.team5.entity.model.board.event.BoardCreated;
import ntut.csie.team5.entity.model.board.event.FigureCommitted;
import ntut.csie.team5.entity.model.board.event.FigureUncommitted;
import ntut.csie.team5.entity.model.board.event.FigureZOrderChanged;

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
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int zOrder = committedFigure.getZOrder();

        CommittedFigure nextCommittedFigure = committedFigures.stream().filter(cf ->  cf.getZOrder() == (zOrder + 1)).findAny().orElse(null);

        if(nextCommittedFigure == null)
            return;

        committedFigures.set(zOrder, new CommittedFigure(nextCommittedFigure.getFigureId(), nextCommittedFigure.getBoardId(), zOrder));
        committedFigures.set(zOrder + 1, new CommittedFigure(committedFigure.getFigureId(), committedFigure.getBoardId(), zOrder + 1));
    }

    public void sendFigureBackwards(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int zOrder = committedFigure.getZOrder();

        CommittedFigure previousCommittedFigure = committedFigures.stream().filter(cf ->  cf.getZOrder() == (zOrder - 1)).findAny().orElse(null);

        if(previousCommittedFigure == null)
            return;

        committedFigures.set(zOrder, new CommittedFigure(previousCommittedFigure.getFigureId(), previousCommittedFigure.getBoardId(), zOrder));
        committedFigures.set(zOrder - 1, new CommittedFigure(committedFigure.getFigureId(), committedFigure.getBoardId(), zOrder - 1));
    }

    public void bringFigureToFront(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.getZOrder();

        for (int i = order; i < committedFigures.size(); i++)
        {
            int nextZOrder = i;
            CommittedFigure nextCommittedFigure = committedFigures.stream().filter(cf ->  cf.getZOrder() == (nextZOrder + 1)).findAny().orElse(null);

            if(nextCommittedFigure == null) {
                committedFigures.set(committedFigures.size() - 1, new CommittedFigure(committedFigure.getFigureId(), committedFigure.getBoardId(), committedFigures.size() - 1));
                return;
            }

            committedFigures.set(nextZOrder, new CommittedFigure(nextCommittedFigure.getFigureId(), nextCommittedFigure.getBoardId(), nextZOrder));
        }
    }

    public void sendFigureToBack(String figureId) {
        CommittedFigure committedFigure = committedFigures.stream().filter(cf ->  cf.getFigureId().equals(figureId)).findAny().orElse(null);

        if(committedFigure == null)
            return;

        int order = committedFigure.getZOrder();

        for (int i = order; i >= 0; i--)
        {
            int previousZOrder = i;
            CommittedFigure previousCommittedFigure = committedFigures.stream().filter(cf ->  cf.getZOrder() == (previousZOrder - 1)).findAny().orElse(null);

            if(previousCommittedFigure == null) {
                committedFigures.set(0, new CommittedFigure(committedFigure.getFigureId(), committedFigure.getBoardId(), 0));
                return;
            }

            committedFigures.set(previousZOrder, new CommittedFigure(previousCommittedFigure.getFigureId(), previousCommittedFigure.getBoardId(), previousZOrder));
        }
    }
}
