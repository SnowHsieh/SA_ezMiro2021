package ntut.csie.selab.usecase.eventHandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.event.WidgetCreationNotifiedToAllUser;
import ntut.csie.selab.entity.model.board.event.WidgetDeletionCommitted;
import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
import ntut.csie.selab.entity.model.widget.event.WidgetDeleted;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardAssociationRepository;

import java.util.Date;
import java.util.Optional;

public class NotifyBoard {

    private BoardAssociationRepository boardAssociationRepository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(BoardAssociationRepository boardAssociationRepository, DomainEventBus domainEventBus) {
        this.boardAssociationRepository = boardAssociationRepository;
        this.domainEventBus = domainEventBus;
    }

    @Subscribe
    public void whenWidgetCreated(WidgetCreated widgetCreated) {
        Optional<Board> board = boardAssociationRepository.findById(widgetCreated.getBoardId());

        if (board.isPresent()) {
            Board selectedBoard = board.get();
            boardAssociationRepository.save(selectedBoard);
            boardAssociationRepository.saveCommittedWidget(selectedBoard.getId(), widgetCreated.getWidgetId());
            domainEventBus.post(new WidgetCreationNotifiedToAllUser(new Date(), widgetCreated.getBoardId(), widgetCreated.getWidgetId()));
        } else {
            throw new RuntimeException("Board not found, board id = " + widgetCreated.getBoardId());
        }
    }

    @Subscribe
    public void whenWidgetDeleted(WidgetDeleted widgetDeleted) {
        Optional<Board> board = boardAssociationRepository.findById(widgetDeleted.getBoardId());

        if (board.isPresent()) {
            Board selectedBoard = board.get();
            boardAssociationRepository.save(selectedBoard);
//            boardAssociationRepository.deleteCommittedWidget(selectedBoard.getId(), widgetDeleted.getWidgetId());
            domainEventBus.post(new WidgetDeletionCommitted(new Date()));
        } else {
            throw new RuntimeException("Board not found, board id = " + widgetDeleted.getBoardId());
        }
    }
}
