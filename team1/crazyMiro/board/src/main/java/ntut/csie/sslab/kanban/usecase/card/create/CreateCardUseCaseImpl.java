package ntut.csie.sslab.kanban.usecase.card.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardBuilder;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.card.CardRepository;

public class CreateCardUseCaseImpl implements CreateCardUseCase {
	private CardRepository cardRepository;
	private DomainEventBus domainEventBus;
	
	public CreateCardUseCaseImpl(CardRepository cardRepository, DomainEventBus domainEventBus) {
		this.cardRepository = cardRepository;
		this.domainEventBus = domainEventBus;
	}
	
	@Override
	public void execute(CreateCardInput input, CqrsCommandOutput output) {
		Card card = CardBuilder.newInstance()
				.workflowId(input.getWorkflowId())
				.laneId(input.getLaneId())
				.userId(input.getUserId())
				.description(input.getDescription())
				.estimate(input.getEstimate())
				.notes(input.getNote())
				.deadline(input.getDeadline())
				.type(CardType.valueOf(input.getType()))
				.username(input.getUsername())
				.boardId(input.getBoardId())
				.build();

		cardRepository.save(card);
		domainEventBus.postAll(card);

		output.setId(card.getCardId());
		output.setExitCode(ExitCode.SUCCESS);
	}

	@Override
	public CreateCardInput newInput() {
		return new CreateCardInputImpl();
	}

	private static class CreateCardInputImpl implements CreateCardInput {
		private String workflowId;
		private String laneId;
		private String userId;
		private String description;
		private String estimate;
		private String notes;
		private String deadline;
		private String type;
		private String username;
		private String boardId;

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
		public String getDescription() {
			return description;
		}

		@Override
		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String getEstimate() {
			return estimate;
		}

		@Override
		public void setEstimate(String estimate) {
			this.estimate = estimate;
		}

		@Override
		public String getNote() {
			return notes;
		}

		@Override
		public void setNote(String note) {
			this.notes = note;
		}

		@Override
		public String getDeadline() {
			return deadline;
		}

		@Override
		public void setDeadline(String deadline) {
			this.deadline = deadline;
		}

		@Override
		public void setType(String type) {
			this.type = type;
		}

		@Override
		public String getType() {
			return type;
		}

		@Override
		public void setUsername(String username) {
			this.username = username;
		}

		@Override
		public String getUsername() {
			return username;
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
