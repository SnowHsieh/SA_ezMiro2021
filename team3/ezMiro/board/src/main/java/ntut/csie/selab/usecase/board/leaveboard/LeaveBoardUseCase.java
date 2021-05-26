package ntut.csie.selab.usecase.board.leaveboard;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.model.DomainEvent;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;

import java.awt.*;
import java.util.Optional;

public class LeaveBoardUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public LeaveBoardUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(LeaveBoardInput input, LeaveBoardOutput output) {
        Optional<Board> board = boardRepository.findById(input.getBoardId());

        if (board.isPresent()) {
            Board selectedBoard = board.get();
            selectedBoard.removeCursorBy(input.getUserId());
            boardRepository.save(selectedBoard);
            output.setCursorCountInBoard(selectedBoard.getCursorCount());
            output.setCursors(selectedBoard.getCursors());
        } else {
            throw new RuntimeException("board not found, board id = " + input.getBoardId());
        }
    }
}
