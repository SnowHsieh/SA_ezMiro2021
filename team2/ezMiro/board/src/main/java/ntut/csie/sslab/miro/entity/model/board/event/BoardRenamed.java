package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardRenamed extends DomainEvent {

    private final String teamId;
    private final String boardId;
    private final String oldName;
    private final String newName;


    public BoardRenamed(String teamId, String boardId, String oldName, String newName){
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
        this.oldName = oldName;
        this.newName = newName;
    }

    public String teamId() {
        return teamId;
    }

    public String boardId() {
        return boardId;
    }

    public String oldName() {
        return oldName;
    }

    public String newName() {
        return newName;
    }
}
