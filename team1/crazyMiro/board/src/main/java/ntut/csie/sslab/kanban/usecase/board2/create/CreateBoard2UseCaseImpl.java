package ntut.csie.sslab.kanban.usecase.board2.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.usecase.board2.Board2Repository;
import ntut.csie.sslab.kanban.entity.model.board2.BoardBuilder;
import ntut.csie.sslab.kanban.entity.model.board2.BoardMemberType;

public class CreateBoard2UseCaseImpl implements CreateBoard2UseCase {
	private Board2Repository board2Repository;
	private DomainEventBus domainEventBus;

	public CreateBoard2UseCaseImpl(Board2Repository board2Repository,
								   DomainEventBus domainEventBus) {

		this.board2Repository = board2Repository;
		this.domainEventBus = domainEventBus;
	}

	@Override
	public void  execute(CreateBoard2Input input, CqrsCommandOutput output) {
		Board2 board2 = BoardBuilder.newInstance()
				.name(input.getName())
				.teamId(input.getTeamId())
				.userId(input.getUserId())
				.build();
		board2.becameBoardMember(BoardMemberType.Manager, input.getUserId());

		board2Repository.save(board2);
		domainEventBus.postAll(board2);

		output.setId(board2.getBoardId());
		output.setExitCode(ExitCode.SUCCESS);
	}

	@Override
	public CreateBoard2Input newInput() {
		return new CreateBoard2InputImpl();
	}

	private static class CreateBoard2InputImpl implements CreateBoard2Input {
		private String name;
		private String userId;
		private String teamId;

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
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
		public String getTeamId() {
			return teamId;
		}

		@Override
		public void setTeamId(String teamId) {
			this.teamId = teamId;
		}
	}
}
