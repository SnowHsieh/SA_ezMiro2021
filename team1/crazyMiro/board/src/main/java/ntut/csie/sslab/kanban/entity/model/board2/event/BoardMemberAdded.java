package ntut.csie.sslab.kanban.entity.model.board2.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class BoardMemberAdded extends DomainEvent {

    private final String userId;
    private final String boardId;
    private final String memberType;

    public BoardMemberAdded(String userId, String boardId, String memberType) {
        super(DateProvider.now());
        this.userId = userId;
        this.boardId = boardId;
        this.memberType = memberType;
    }

    public String userId() {
        return userId;
    }

    public String boardId() {
        return boardId;
    }

    public String memberType() {
        return memberType;
    }
}
