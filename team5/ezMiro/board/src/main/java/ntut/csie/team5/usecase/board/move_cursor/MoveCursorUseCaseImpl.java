package ntut.csie.team5.usecase.board.move_cursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;
import ntut.csie.team5.usecase.board.BoardRepository;

public class MoveCursorUseCaseImpl implements MoveCursorUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public MoveCursorUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public MoveCursorInput newInput() {
        return new MoveCursorInputImpl();
    }

    @Override
    public void execute(MoveCursorInput input, CqrsCommandPresenter output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);

        if (null == board) {
            output.setId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Move cursor failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        board.moveCursor(input.getBoardSessionId(), input.getPositionX(), input.getPositionY());

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(board.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class MoveCursorInputImpl implements MoveCursorInput {

        String boardId;
        String boardSessionId;
        int positionX;
        int positionY;

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

        @Override
        public int getPositionX() {
            return positionX;
        }

        @Override
        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        @Override
        public int getPositionY() {
            return positionY;
        }

        @Override
        public void setPositionY(int positionY) {
            this.positionY = positionY;
        }
    }
}
