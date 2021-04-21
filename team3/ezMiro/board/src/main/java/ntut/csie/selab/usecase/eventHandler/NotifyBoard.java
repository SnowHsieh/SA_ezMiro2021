package ntut.csie.selab.usecase.eventHandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.selab.entity.model.Board;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.widget.create.WidgetCreated;

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
            board.get().commitWidgetCreation(widgetCreated.getBoardId(), widgetCreated.getWidgetId());
        } else {
            throw new RuntimeException("Board not found, board id = " + widgetCreated.getBoardId());
        }
    }

}
