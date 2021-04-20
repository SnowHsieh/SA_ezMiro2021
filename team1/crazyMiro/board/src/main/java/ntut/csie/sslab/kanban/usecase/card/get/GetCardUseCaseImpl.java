package ntut.csie.sslab.kanban.usecase.card.get;

import ntut.csie.sslab.kanban.usecase.card.CardRepository;
import ntut.csie.sslab.kanban.usecase.card.ConvertCardToDto;

public class GetCardUseCaseImpl implements GetCardUseCase, GetCardInput {

    private CardRepository cardRepository;
    private String cardId;

    public GetCardUseCaseImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void execute(GetCardInput input, GetCardOutput output) {
        output.setCard(ConvertCardToDto.transform(cardRepository.findById(input.getCardId()).get()));
    }


    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
