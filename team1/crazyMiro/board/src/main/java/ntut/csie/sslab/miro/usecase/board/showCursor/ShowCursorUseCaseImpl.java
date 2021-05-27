package ntut.csie.sslab.miro.usecase.board.showCursor;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

public class ShowCursorUseCaseImpl implements ShowCursorUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public ShowCursorUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ShowCursorInput input, CqrsCommandOutput output) {
        try{
            Board board = boardRepository.findById(input.getBoardId()).get();
            board.showCursor(input.getUserId(), input.getPosition());
            domainEventBus.postAll(board);

            output.setId(input.getUserId())
                .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e){
            output.setMessage(e.getMessage())
                .setExitCode(ExitCode.FAILURE);
        }
    }

    @Override
    public ShowCursorInput newInput() {
        return new ShowCursorInputImpl();
    }

    private class ShowCursorInputImpl implements ShowCursorInput {
        private String boardId;
        private String userId;
        private Coordinate position;

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
        public Coordinate getPosition() {
            return position;
        }

        @Override
        public void setPosition(Coordinate position) {
            this.position = position;
        }
    }
}
