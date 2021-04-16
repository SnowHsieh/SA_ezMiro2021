package ntut.csie.sslab.miro.adapter.presenter.card.get;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.card.CardDto;

public class CardViewModel implements ViewModel {
    private CardDto card;

    public CardDto getCard() {
        return card;
    }

    public void setCard(CardDto card) {
        this.card = card;
    }
}
