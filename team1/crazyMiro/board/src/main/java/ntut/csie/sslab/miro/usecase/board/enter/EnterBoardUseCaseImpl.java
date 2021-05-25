package ntut.csie.sslab.miro.usecase.board.enter;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardSession;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

import java.util.UUID;

public class EnterBoardUseCaseImpl implements EnterBoardUseCase {
   private BoardRepository boardRepository;
   private DomainEventBus domainEventBus;

    public EnterBoardUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(EnterBoardInput input, CqrsCommandOutput output) {
        try {
            BoardSession boardSession = new BoardSession(input.getUserId(), input.getBoardId(), UUID.randomUUID().toString());
            Board board = boardRepository.findById(input.getBoardId()).get();
            board.addBoardSession(boardSession);
            boardRepository.save(board);
            domainEventBus.postAll(board);
            output.setId(boardSession.getBoardSessionId())
                    .setExitCode(ExitCode.SUCCESS);
        } catch(Exception e) {
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    @Override
    public EnterBoardInput newInput() {
        return new EnterBoardInputImpl();
    }

    private class EnterBoardInputImpl implements EnterBoardInput {
        private String userId;
        private String boardId;

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public void setUserId(String userId) {
            this.userId = userId;
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
