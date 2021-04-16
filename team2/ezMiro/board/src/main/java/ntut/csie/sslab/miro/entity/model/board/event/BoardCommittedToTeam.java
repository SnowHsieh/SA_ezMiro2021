package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardCommittedToTeam extends DomainEvent {
    private final String boardId;
    private final String teamId;
    private final String title;
    private final int order;

    public BoardCommittedToTeam(String teamId, String boardId, String title, int order) {
        super(DateProvider.now());
        this.teamId = teamId;
        this.boardId = boardId;
        this.title = title;
        this.order = order;
    }

    public String boardId() {
        return boardId;
    }

    public String teamId() {
        return teamId;
    }

    public String title() {
        return title;
    }

    public int order() {
        return order;
    }
}
