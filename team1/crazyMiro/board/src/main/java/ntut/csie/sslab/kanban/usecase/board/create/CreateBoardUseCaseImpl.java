package ntut.csie.sslab.kanban.usecase.board.create;

import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;

public class CreateBoardUseCaseImpl implements CreateBoardUseCase{

    private BoardRepository boardRepository;

    public CreateBoardUseCaseImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void execute(CreateBoardInput input, CqrsCommandOutput output) {
        Board board = new Board(input.getBoardId(), input.getBoardName());

        boardRepository.save(board);

        output.setId(board.getId());
    }

    @Override
    public CreateBoardInput newInput() {
        return new CreateBoardInputImpl();
    }

    private static class CreateBoardInputImpl implements CreateBoardInput {
        private String boardName;
        private String boardId;

        @Override
        public String getBoardName() {
            return boardName;
        }

        @Override
        public void setBoardName(String boardName) {
            this.boardName = boardName;
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
