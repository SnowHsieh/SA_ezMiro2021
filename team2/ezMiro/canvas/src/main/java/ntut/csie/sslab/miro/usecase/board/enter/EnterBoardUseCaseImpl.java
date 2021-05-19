package ntut.csie.sslab.miro.usecase.board.enter;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

public class EnterBoardUseCaseImpl implements EnterBoardUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public EnterBoardUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(EnterBoardInput input, CqrsCommandOutput output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if(board == null) {
            output.setId(input.getBoardId())
                    .setMessage("Enter Board failed: board not found, board id = " + input.getBoardId());
//           domainEventBus.post()
            return;
        }
        board.enter(input.getUserId());

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(board.getId());
    }

    @Override
    public EnterBoardInput newInput() {
        return new EnterBoardInputImpl();
    }

    private class EnterBoardInputImpl implements EnterBoardInput {
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