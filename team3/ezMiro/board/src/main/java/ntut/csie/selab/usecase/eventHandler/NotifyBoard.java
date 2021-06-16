package ntut.csie.selab.usecase.eventHandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.event.WidgetDeletionCommitted;
import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
import ntut.csie.selab.entity.model.board.event.WidgetCreationCommitted;
import ntut.csie.selab.entity.model.widget.event.WidgetDeleted;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;

import java.util.Date;
import java.util.Optional;

public class NotifyBoard {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void whenWidgetCreated(WidgetCreated widgetCreated) {
        Optional<Board> board = boardRepository.findById(widgetCreated.getBoardId());

        if (board.isPresent()) {
            Board selectedBoard = board.get();
            selectedBoard.clearDomainEvents();
            selectedBoard.commitWidgetCreation(widgetCreated.getWidgetId());
            boardRepository.save(selectedBoard);
            domainEventBus.postAll(selectedBoard);
        } else {
            throw new RuntimeException("Board not found, board id = " + widgetCreated.getBoardId());
        }
    }

    @Subscribe
    public void whenWidgetDeleted(WidgetDeleted widgetDeleted) {
        Optional<Board> board = boardRepository.findById(widgetDeleted.getBoardId());

        if (board.isPresent()) {
            Board selectedBoard = board.get();
            selectedBoard.clearDomainEvents();
            selectedBoard.commitWidgetDeletion(widgetDeleted.getWidgetId());
            boardRepository.save(selectedBoard);
            domainEventBus.postAll(selectedBoard);
        } else {
            throw new RuntimeException("Board not found, board id = " + widgetDeleted.getBoardId());
        }
    }
}
