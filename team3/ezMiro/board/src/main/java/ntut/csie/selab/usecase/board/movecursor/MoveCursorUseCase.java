package ntut.csie.selab.usecase.board.movecursor;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;

import java.util.Optional;

public class MoveCursorUseCase {
    BoardRepository boardRepository;
    DomainEventBus domainEventBus;

    public MoveCursorUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(MoveCursorInput input, MoveCursorOutput output) {
        Optional<Board> board = boardRepository.findById(input.getBoardId());

        if (board.isPresent()) {
            Board selectedBoard = board.get();
            selectedBoard.moveCursorOf(input.getUserId(), input.getPoint());
            output.setCursors(selectedBoard.getCursors());
            output.setBoardId(input.getBoardId());
        } else {
            throw new RuntimeException("board not found, board id = " + input.getBoardId());
        }
    }
}
