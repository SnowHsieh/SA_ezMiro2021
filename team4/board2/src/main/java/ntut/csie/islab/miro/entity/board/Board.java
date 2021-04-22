package ntut.csie.islab.miro.entity.board;

import ntut.csie.islab.miro.figure.entity.figure.Figure;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot<UUID> {
    private UUID teamId;
    private String boardName;
    private List<Figure> figureList;

    public Board(UUID teamId,String boardName){
        super(UUID.randomUUID());
        this.teamId = teamId;
        this.boardName = boardName;
        this.figureList = new ArrayList<Figure>();
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
}
