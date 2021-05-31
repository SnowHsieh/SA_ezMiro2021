package ntut.csie.selab.usecase.board.edit.zorder;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangeZOrderOfWidgetUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public ChangeZOrderOfWidgetUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(ChangeZOrderOfWidgetInput input, ChangeZOrderOfWidgetOutput output) {
        Optional<Board> board = boardRepository.findById(input.getBoardId());
        if(board.isPresent()) {
            Board selectedBoard = board.get();

            Optional<CommittedWidget> committedWidget = selectedBoard.getCommittedWidgetBy(input.getWidgetId());

            if(committedWidget.isPresent()) {
                CommittedWidget selectedCommittedWidget = committedWidget.get();
                selectedBoard.clearDomainEvents();
                selectedBoard.changeZOrder(selectedCommittedWidget.getZOrder(), input.getZOrder());

                boardRepository.save(selectedBoard);
                domainEventBus.postAll(selectedBoard);
                output.setBoardId(selectedBoard.getId());
                output.setWidgetId(selectedBoard.getCommittedWidgetBy(selectedCommittedWidget.getWidgetId()).get().getWidgetId());
                output.setZOrder(selectedBoard.getCommittedWidgetBy(selectedCommittedWidget.getWidgetId()).get().getZOrder());
            } else {
                throw new RuntimeException("Widget not found, widget id = " + input.getWidgetId());
            }
        } else {
            throw new RuntimeException("Board not found, board id = " + input.getBoardId());
        }
    }
}
