package ntut.csie.sslab.kanban.usecase.card.edit.description;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.usecase.ClientBoardContentMightExpire;
import ntut.csie.sslab.kanban.usecase.card.CardRepository;

public class ChangeCardDescriptionUseCaseImpl implements ChangeCardDescriptionUseCase {
    private CardRepository cardRepository;
    private DomainEventBus domainEventBus;

    public ChangeCardDescriptionUseCaseImpl(CardRepository cardRepository,
                                            DomainEventBus domainEventBus) {

        this.cardRepository = cardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeCardDescriptionInput input, CqrsCommandOutput output) {
        Card card = cardRepository.findById(input.getCardId()).orElse(null);
        if (null == card){
            output.setId(input.getCardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Change card description failed: card not found, card id = " + input.getCardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        card.changeDescription(input.getNewDescription(), input.getUserId(), input.getUsername(), input.getBoardId());

        cardRepository.save(card);

        domainEventBus.postAll(card);

        output.setId(card.getCardId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    @Override
    public ChangeCardDescriptionInput newInput() {
        return new ChangeCardDescriptionInputImpl();
    }

    private static class ChangeCardDescriptionInputImpl implements ChangeCardDescriptionInput {
        private String cardId;
        private String description;
        private String boardId;
        private String userId;
        private String username;

        @Override
        public String getCardId() {
            return cardId;
        }

        @Override
        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        @Override
        public String getNewDescription() {
            return description;
        }

        @Override
        public void setNewDescription(String newDescription) {
            this.description = newDescription;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
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
    }
}
