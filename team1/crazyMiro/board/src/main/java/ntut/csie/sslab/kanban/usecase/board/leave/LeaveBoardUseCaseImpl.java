package ntut.csie.sslab.kanban.usecase.board.leave;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;

public class LeaveBoardUseCaseImpl implements LeaveBoardUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public LeaveBoardUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(LeaveBoardInput input, CqrsCommandOutput output) {
        Board board = boardRepository.findById(input.getBoardId()).get();
        board.removeBoardSession(input.getBoardSessionId());
        boardRepository.save(board);
        domainEventBus.postAll(board);
        output.setId(input.getBoardSessionId())
                .setExitCode(ExitCode.SUCCESS);
    }

    @Override
    public LeaveBoardInput newInput() {
        return new LeaveBoardInputImpl();
    }

    private class LeaveBoardInputImpl implements LeaveBoardInput {

        private String boardId;
        private String boardSessionId;


        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getBoardSessionId() {
            return boardSessionId;
        }

        @Override
        public void setBoardSessionId(String boardSessionId) {
            this.boardSessionId = boardSessionId;
        }
    }
}
