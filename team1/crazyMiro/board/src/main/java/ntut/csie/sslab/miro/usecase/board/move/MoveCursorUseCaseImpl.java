package ntut.csie.sslab.miro.usecase.board.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

public class MoveCursorUseCaseImpl implements MoveCursorUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public MoveCursorUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(MoveCursorInput input, CqrsCommandOutput output) {
        try{
            Board board = boardRepository.findById(input.getboardId()).get();
            board.moveCursor(input.getUserId(), input.getPosition());

            domainEventBus.postAll(board);

            output.setId(board.getId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e){
            output.setMessage(e.getMessage())
                    .setExitCode(ExitCode.FAILURE);
        }
    }

    @Override
    public MoveCursorInput newInput() {
        return new MoveCursorInputImpl();
    }

    private class MoveCursorInputImpl implements MoveCursorInput {
        private String boardId;
        private String userId;
        private Coordinate position;

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

        @Override
        public String getboardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }
}
