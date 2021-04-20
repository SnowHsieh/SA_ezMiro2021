package ntut.csie.sslab.kanban.usecase.card.edit.type;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.ClientBoardContentMightExpire;
import ntut.csie.sslab.kanban.usecase.card.CardRepository;

public class ChangeCardTypeUseCaseImpl implements ChangeCardTypeUseCase {

    private CardRepository cardRepository;
    private DomainEventBus domainEventBus;

    public ChangeCardTypeUseCaseImpl(CardRepository cardRepository, DomainEventBus domainEventBus) {
        this.cardRepository = cardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeCardTypeInput input, CqrsCommandOutput output) {
        Card card = cardRepository.findById(input.getCardId()).orElse(null);
        if (null == card){
            output.setId(input.getCardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Change card type failed: card not found, card id = " + input.getCardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        CardType cardType = null;

        switch(input.getNewType()) {
            case("General"):
                cardType = CardType.General;
                break;
            case("Expedite"):
                cardType = CardType.Expedite;
                break;
        }
        card.changeType(cardType, input.getUserId(), input.getUsername(), input.getBoardId());

        cardRepository.save(card);
        domainEventBus.postAll(card);

        output.setId(card.getCardId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    @Override
    public ChangeCardTypeInput newInput() {
        return new ChangeCardTypeInputImpl();
    }

    private static class ChangeCardTypeInputImpl implements ChangeCardTypeInput {

        private String cardId;
        private String newType;
        private String userId;
        private String username;
        private String boardId;

        @Override
        public String getCardId() {
            return cardId;
        }

        @Override
        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        @Override
        public String getNewType() {
            return newType;
        }

        @Override
        public void setNewType(String newType) {
            this.newType = newType;
        }

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }

}
