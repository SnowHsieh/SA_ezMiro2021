package ntut.csie.sslab.miro.usecase.board.leave;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

public class LeaveBoardUseCaseImpl implements LeaveBoardUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public LeaveBoardUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(LeaveBoardInput input, CqrsCommandOutput output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if(board == null) {
            output.setId(input.getBoardId())
                    .setMessage("Leave Board failed: board not found, board id = " + input.getBoardId());
//           domainEventBus.post()
            return;
        }
        board.leave(input.getUserId());

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(board.getId());
    }

    @Override
    public LeaveBoardInput newInput() {
        return new LeaveBoardInputImpl();
    }

    private class LeaveBoardInputImpl implements LeaveBoardInput {
        private String boardId;
        private String userId;

        public String getBoardId() {
            return boardId;
        }

        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}