package ntut.csie.team5.usecase.board.leave;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.BoardSessionId;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;
import ntut.csie.team5.usecase.board.BoardRepository;

public class LeaveBoardUseCaseImpl implements LeaveBoardUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public LeaveBoardUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public LeaveBoardInput newInput() {
        return new LeaveBoardInputImpl();
    }

    @Override
    public void execute(LeaveBoardInput input, CqrsCommandPresenter output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);

        if (null == board) {
            output.setId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Enter board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        board.acceptUserLeaving(input.getBoardSessionId(), input.getUserId());

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(input.getBoardSessionId());
        output.setExitCode(ExitCode.SUCCESS);

    }

    private class LeaveBoardInputImpl implements LeaveBoardInput {

        private String boardId;
        private String userId;
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
        public String getUserId() {
            return userId;
        }

        @Override
        public void setUserId(String userId) {
            this.userId = userId;
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
