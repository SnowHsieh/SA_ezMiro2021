package ntut.csie.sslab.kanban.adapter.presenter.card.get;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.kanban.usecase.card.CardDto;

public class CardViewModel implements ViewModel {
    private CardDto card;

    public CardDto getCard() {
        return card;
    }

    public void setCard(CardDto card) {
        this.card = card;
    }
}
