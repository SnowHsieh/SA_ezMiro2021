package ntut.csie.team5.usecase.board.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.BoardBuilder;
import ntut.csie.team5.usecase.board.BoardRepository;

public class CreateBoardUseCaseImpl implements CreateBoardUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public CreateBoardUseCaseImpl(BoardRepository boardRepository,
                                  DomainEventBus domainEventBus) {

        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public CreateBoardInput newInput() {
        return new CreateBoardInputImpl();
    }

    @Override
    public void execute(CreateBoardInput input, CqrsCommandOutput output) {
        Board board = BoardBuilder.newInstance()
                .name(input.getBoardName())
                .projectId(input.getProjectId())
                .build();

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(board.getBoardId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class CreateBoardInputImpl implements CreateBoardInput {

        private String projectId;
        private String boardName;

        @Override
        public String getProjectId() {
            return projectId;
        }

        @Override
        public void setProjectId(String projectId) {
            this.projectId = projectId;
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
