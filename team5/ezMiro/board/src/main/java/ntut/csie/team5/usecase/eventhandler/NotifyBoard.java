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
        Board board = boardRepository.findById((figureCreated.boardId())).orElse(null);
        if (null == board) {
            throw new RuntimeException("Board not found, board id = " + figureCreated.boardId());
        }

        board.commitFigure(figureCreated.figureId());
        boardRepository.save(board);
        domainEventBus.postAll(board);
    }

    @Subscribe
    public void whenFigureDeleted(FigureDeleted figureDeleted) {
        Board board = boardRepository.findById((figureDeleted.boardId())).orElse(null);
        if (null == board) {
            throw new RuntimeException("Board not found, board id = " + figureDeleted.boardId());
        }

        board.uncommitFigure(figureDeleted.figureId());
        boardRepository.save(board);
        domainEventBus.postAll(board);
    }
}
