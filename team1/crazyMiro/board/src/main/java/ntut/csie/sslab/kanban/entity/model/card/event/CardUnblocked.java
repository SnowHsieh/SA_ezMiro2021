package ntut.csie.sslab.kanban.entity.model.card.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardUnblocked extends DomainEvent {

    private final String cardId;
    private final String blockerId;
    private final String blockerName;
    private final String cardDescription;
    private final String userId;
    private final String username;
    private final String boardId;


    public CardUnblocked(String cardId, String blockerId, String blockerName, String cardDescription, String userId, String username, String boardId) {
        super(DateProvider.now());
        this.cardId = cardId;
        this.blockerId = blockerId;
        this.blockerName = blockerName;
        this.cardDescription = cardDescription;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;

    }
    public String cardId() {
        return cardId;
    }

    public String blockerId() { return blockerId; }

    public String blockerName() {
        return blockerName;
    }

    public String cardDescription() {
        return cardDescription;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String boardId() {
        return boardId;
    }
}
