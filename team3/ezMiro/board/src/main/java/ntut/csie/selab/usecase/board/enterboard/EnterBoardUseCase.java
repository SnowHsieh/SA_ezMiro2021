package ntut.csie.selab.usecase.board.enterboard;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;

import java.awt.*;
import java.util.Optional;

public class EnterBoardUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public EnterBoardUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(EnterBoardInput input, EnterBoardOutput output) {
        Optional<Board> board = boardRepository.findById(input.getBoardId());

        if (board.isPresent()) {
            Board selectedBoard = board.get();
            selectedBoard.clearDomainEvents();
            Cursor addedCursor = new Cursor(input.getBoardId(), input.getUserId(), new Point(100, 100));
            selectedBoard.addCursor(addedCursor);
            output.setCursor(addedCursor);
            domainEventBus.postAll(selectedBoard);
        } else {
            throw new RuntimeException("board not found, board id = " + input.getBoardId());
        }
    }
}
