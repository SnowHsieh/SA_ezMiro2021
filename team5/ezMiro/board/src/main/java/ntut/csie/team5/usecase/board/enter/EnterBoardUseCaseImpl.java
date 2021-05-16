package ntut.csie.team5.usecase.board.enter;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.BoardSessionId;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;
import ntut.csie.team5.usecase.board.BoardRepository;

public class EnterBoardUseCaseImpl implements EnterBoardUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public EnterBoardUseCaseImpl(BoardRepository boardRepository,
                                  DomainEventBus domainEventBus) {

        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public EnterBoardInput newInput() {
        return new EnterBoardInputImpl();
    }

    @Override
    public void execute(EnterBoardInput input, CqrsCommandOutput output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Enter board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        BoardSessionId boardSessionId = BoardSessionId.create();

        board.acceptUserEntry(boardSessionId, input.getUserId());

        output.setId(boardSessionId.id());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class EnterBoardInputImpl implements EnterBoardInput {

        private String boardId;
        private String userId;

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
    }
}
