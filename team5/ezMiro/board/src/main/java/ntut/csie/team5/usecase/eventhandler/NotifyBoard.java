package ntut.csie.team5.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.figure.event.FigureCreated;
import ntut.csie.team5.entity.model.figure.event.FigureDeleted;
import ntut.csie.team5.usecase.board.BoardRepository;

import java.util.Optional;

public class NotifyBoard {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void whenFigureCreated(FigureCreated figureCreated) {
        Optional<Board> board = boardRepository.findById((figureCreated.boardId()));
        if (!board.isPresent()) {
            throw new RuntimeException("Board not found, board id = " + figureCreated.boardId());
        }

        board.get().commitFigure(figureCreated.figureId());
        boardRepository.save(board.get());
        domainEventBus.postAll(board.get());
    }

    @Subscribe
    public void whenFigureDeleted(FigureDeleted figureDeleted) {
        Optional<Board> board = boardRepository.findById((figureDeleted.boardId()));
        if (!board.isPresent()) {
            throw new RuntimeException("Board not found, board id = " + figureDeleted.boardId());
        }

        board.get().uncommitFigure(figureDeleted.figureId());
        boardRepository.save(board.get());
        domainEventBus.postAll(board.get());
    }
}
