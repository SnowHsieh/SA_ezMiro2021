package ntut.csie.sslab.kanban.usecase.card.get;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetCardInput extends Input {
    public String getCardId();

    public void setCardId(String cardId);
}
