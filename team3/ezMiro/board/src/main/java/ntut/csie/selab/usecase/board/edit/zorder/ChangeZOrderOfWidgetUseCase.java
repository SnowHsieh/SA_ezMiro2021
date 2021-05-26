package ntut.csie.selab.usecase.board.edit.zorder;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardAssociationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangeZOrderOfWidgetUseCase {

    private BoardAssociationRepository boardAssociationRepository;
    private DomainEventBus domainEventBus;

    public ChangeZOrderOfWidgetUseCase(BoardAssociationRepository boardAssociationRepository, DomainEventBus domainEventBus) {
        this.boardAssociationRepository = boardAssociationRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(ChangeZOrderOfWidgetInput input, ChangeZOrderOfWidgetOutput output) {
        Optional<Board> board = boardAssociationRepository.findById(input.getBoardId());
        if(board.isPresent()) {
            Board selectedBoard = board.get();

            Optional<CommittedWidget> committedWidget = selectedBoard.getCommittedWidgetBy(input.getWidgetId());

            if(committedWidget.isPresent()) {
                CommittedWidget selectedCommittedWidget = committedWidget.get();
                selectedBoard.clearDomainEvents();
                selectedBoard.changeZOrder(selectedCommittedWidget.getZOrder(), input.getZOrder());

                boardAssociationRepository.saveAllCommittedWidget(selectedBoard.getCommittedWidgets());
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
