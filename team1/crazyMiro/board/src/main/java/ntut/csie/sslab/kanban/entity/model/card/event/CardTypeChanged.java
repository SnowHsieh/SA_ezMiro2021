package ntut.csie.sslab.kanban.entity.model.card.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class CardTypeChanged extends DomainEvent {

    private final String cardId;
    private final String oldType;
    private final String newType;
    private final String userId;
    private final String username;
    private final String boardId;

    public CardTypeChanged(String cardId, String oldType, String newType, String userId, String username, String boardId) {
        super(DateProvider.now());
        this.cardId = cardId;
        this.oldType = oldType;
        this.newType = newType;
        this.userId = userId;
        this.username = username;
        this.boardId = boardId;
    }

    public String cardId() {
        return cardId;
    }

    public String newType() {
        return newType;
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

    public String oldType() {
        return oldType;
    }
}
