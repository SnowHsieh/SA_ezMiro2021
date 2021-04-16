package ntut.csie.sslab.miro.usecase.board.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardBuilder;
import ntut.csie.sslab.miro.entity.model.board.BoardMemberType;

public class CreateBoardUseCaseImpl implements CreateBoardUseCase {
	private BoardRepository boardRepository;
	private DomainEventBus domainEventBus;

	public CreateBoardUseCaseImpl(BoardRepository boardRepository,
                                  DomainEventBus domainEventBus) {

		this.boardRepository = boardRepository;
		this.domainEventBus = domainEventBus;
	}

	@Override
	public void  execute(CreateBoardInput input, CqrsCommandOutput output) {
		Board board = BoardBuilder.newInstance()
				.name(input.getName())
				.teamId(input.getTeamId())
				.userId(input.getUserId())
				.build();
		board.addBoardMember(BoardMemberType.Manager, input.getUserId());

		boardRepository.save(board);
		domainEventBus.postAll(board);

		output.setId(board.getBoardId());
		output.setExitCode(ExitCode.SUCCESS);
	}

	@Override
	public CreateBoardInput newInput() {
		return new CreateBoardInputImpl();
	}

	private static class CreateBoardInputImpl implements CreateBoardInput {
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
