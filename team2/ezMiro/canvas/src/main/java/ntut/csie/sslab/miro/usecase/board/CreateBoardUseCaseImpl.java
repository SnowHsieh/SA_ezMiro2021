package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardBuilder;

public class CreateBoardUseCaseImpl implements CreateBoardUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public CreateBoardUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateBoardInput input, CqrsCommandOutput output) {
        Board board = BoardBuilder.newInstance()
                .teamId(input.getTeamId())
                .boardName(input.getBoardName())
                .build();

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(board.getId());
    }

    @Override
    public CreateBoardInput newInput() {
        return new CreateBoardInputImpl();
    }

    private static class CreateBoardInputImpl implements CreateBoardInput {
        private String teamId;
        private String boardName;

        @Override
        public String getTeamId() {
            return teamId;
        }

        @Override
        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        @Override
        public String getBoardName() {
            return boardName;
        }

        @Override
        public void setBoardName(String boardName) {
            this.boardName = boardName;
        }
    }
}
