package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.islab.miro.entity.model.board.event.BoardCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.TextFigureCommittedDomainEvent;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot<UUID> {
    private UUID teamId;
    private String boardName;
    private List<CommittedTextFigure> figureList;

    public Board(UUID teamId,String boardName){
        super(UUID.randomUUID());
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<CommittedTextFigure>();
        addDomainEvent(new BoardCreatedDomainEvent(teamId, getBoardId()));

    }

    public Board(UUID teamId,UUID boardId,String boardName){
        super(boardId);
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<CommittedTextFigure>();
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
        addDomainEvent(new TextFigureCommittedDomainEvent(getBoardId(), figureId));

    }
    private void addFigure(UUID figureId) {
        CommittedTextFigure committedTextFigure = new CommittedTextFigure(getBoardId(), figureId);
        this.figureList.add(committedTextFigure);
    }

    public List<CommittedTextFigure> getCommittedFigures() {
        return this.figureList;
    }
}
