package ntut.csie.selab.usecase.board.edit.zorder;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.Collection;
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

    private void reArrangeZOrderIn(Board board, int originZOrder, int newZOrder) {
        List<CommittedWidget> committedWidgets = board.getCommittedWidgets();
        if (originZOrder < newZOrder) {
            shiftZOrderInRange(originZOrder, newZOrder, newZOrder, committedWidgets, -1);
        } else {
            shiftZOrderInRange(newZOrder, originZOrder, newZOrder, committedWidgets, 1);
        }
        sortAscendByZOrder(board.getCommittedWidgets());
    }

    private void sortAscendByZOrder(List<CommittedWidget> widgets) {
        widgets.sort((w1, w2) -> w1.getZOrder() - w2.getZOrder());
    }

    private void shiftZOrderInRange(int startIndex, int endIndex, int targetZOrder, List<CommittedWidget> committedWidgets, int offset) {
        List<CommittedWidget> subList = new ArrayList<>(committedWidgets.subList(startIndex, endIndex + 1));
        committedWidgets.removeAll(subList);
        CommittedWidget target;
        if (offset > 0) {
            target = subList.remove(subList.size() - 1);
        } else {
            target = subList.remove(0);
        }
        for (int i = 0; i < subList.size(); i++) {
            committedWidgets.add(startIndex + i, new CommittedWidget(subList.get(i).getBoardId(), subList.get(i).getWidgetId(), subList.get(i).getZOrder() + offset));
        }
        committedWidgets.add(endIndex, new CommittedWidget(target.getBoardId(), target.getWidgetId(), targetZOrder));
    }
}
