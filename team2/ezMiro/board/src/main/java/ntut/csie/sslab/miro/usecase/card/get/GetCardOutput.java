package ntut.csie.sslab.miro.usecase.card.get;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.miro.usecase.card.CardDto;

public interface GetCardOutput extends Output {
    public CardDto getCard();

    public void setCard(CardDto cardDto);
}
