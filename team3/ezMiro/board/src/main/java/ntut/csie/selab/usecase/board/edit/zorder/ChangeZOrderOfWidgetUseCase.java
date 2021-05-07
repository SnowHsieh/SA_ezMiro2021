package ntut.csie.selab.usecase.board.edit.zorder;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;

import java.util.List;
import java.util.Optional;

public class ChangeZOrderOfWidgetUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public ChangeZOrderOfWidgetUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    // a, b, c, d, e
    // e -> c
    // a, b, e, c, d
    public void execute(ChangeZOrderOfWidgetInput input, ChangeZOrderOfWidgetOutput output) {
        Optional<Board> board = boardRepository.findById(input.getBoardId());
        if(board.isPresent()) {
            Board selectedBoard = board.get();
            Optional<CommittedWidget> committedWidget = selectedBoard.getCommittedWidgetBy(input.getWidgetId());

            if(committedWidget.isPresent()) {
                CommittedWidget selectedCommittedWidget = committedWidget.get();
                reArrangeZOrderIn(selectedBoard, selectedCommittedWidget.getZOrder(), input.getZOrder());

                domainEventBus.postAll(selectedBoard);
                output.setBoardId(selectedCommittedWidget.getBoardId());
                output.setWidgetId(selectedCommittedWidget.getWidgetId());
                output.setZOrder(selectedCommittedWidget.getZOrder());
            } else {
                throw new RuntimeException("Widget not found, widget id = " + input.getWidgetId());
            }
        } else {
            throw new RuntimeException("Board not found, board id = " + input.getBoardId());
        }

    }

    private void reArrangeZOrderIn(Board board, int originZOrder, int newZOrder) {
        List<CommittedWidget> committedWidgets = board.getCommittedWidgets();
        List<CommittedWidget> subList = committedWidgets.subList(originZOrder, newZOrder);
//        committedWidgets.removeAll(subList);
        CommittedWidget target;
        if (originZOrder < newZOrder) {
            target = subList.remove(0);
        } else {
            target = subList.remove(subList.size() - 1);
        }
        for (int i = 0; i < subList.size(); i++) {
            committedWidgets.add(originZOrder + 1, new CommittedWidget(board.getId(), subList.get(i).getWidgetId(), originZOrder + i));
        }
        if (originZOrder < newZOrder) {
            committedWidgets.add(newZOrder, new CommittedWidget(board.getId(), target.getWidgetId(), newZOrder));
        } else {
            committedWidgets.add(originZOrder, new CommittedWidget(board.getId(), target.getWidgetId(), newZOrder));
        }
    }
}
