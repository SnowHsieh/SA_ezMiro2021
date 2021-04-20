package ntut.csie.sslab.kanban.usecase.card.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.usecase.ClientBoardContentMightExpire;
import ntut.csie.sslab.kanban.usecase.card.CardRepository;

public class DeleteCardUseCaseImpl implements DeleteCardUseCase {
	private CardRepository cardRepository;
	private DomainEventBus domainEventBus;

	public DeleteCardUseCaseImpl(CardRepository cardRepository,
								 DomainEventBus domainEventBus) {

		this.cardRepository = cardRepository;
		this.domainEventBus = domainEventBus;
	}

	@Override
	public void execute(DeleteCardInput input, CqrsCommandOutput output) {
		Card card = cardRepository.findById(input.getCardId()).orElse(null);

		if (null == card){
			output.setId(input.getCardId())
					.setExitCode(ExitCode.FAILURE)
					.setMessage("Delete card failed: card not found, card id = " + input.getCardId());
			domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
			return;
		}

		card.markAsRemoved(
				input.getWorkflowId(),
				input.getLaneId(),
				input.getUserId(),
				input.getUsername(),
				input.getBoardId()
		);

		cardRepository.deleteById(card.getCardId());
		domainEventBus.postAll(card);

		output.setId(card.getCardId());
		output.setExitCode(ExitCode.SUCCESS);
	}

	@Override
	public DeleteCardInput newInput() {
		return new DeleteCardInputImpl();
	}

	private static class DeleteCardInputImpl implements DeleteCardInput {
		private String cardId;
		private String workflowId;
		private String laneId;
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
		public String getWorkflowId() {
			return workflowId;
		}

		@Override
		public void setWorkflowId(String workflowId) {
			this.workflowId = workflowId;
		}

		@Override
		public String getLaneId() {
			return laneId;
		}

		@Override
		public void setLaneId(String laneId) {
			this.laneId = laneId;
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
