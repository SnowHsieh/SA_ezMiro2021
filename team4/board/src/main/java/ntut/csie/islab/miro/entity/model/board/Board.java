package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.islab.miro.entity.model.board.event.BoardCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.FigureCommittedDomainEvent;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot<UUID> {
    private UUID teamId;
    private String boardName;
    private List<CommittedFigure> figureList;

    public Board(UUID teamId,String boardName){
        super(UUID.randomUUID());
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<CommittedFigure>();
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

    public UUID getBoardId(){
        return this.getId();
    }

    public void commitFigure(UUID figureId) {
        addFigure(figureId);
        addDomainEvent(new FigureCommittedDomainEvent(getBoardId(), figureId));

    }
    private void addFigure(UUID figureId) {
        CommittedFigure committedFigure = new CommittedFigure(getBoardId(), figureId);
        this.figureList.add(committedFigure);
    }

    public List<CommittedFigure> getCommittedFigures() {
        return this.figureList;
    }
}
