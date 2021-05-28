package ntut.csie.selab.usecase.board;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.List;

public interface BoardAssociationRepository extends BoardRepository {
    void saveCommittedWidget(String boardId, String widgetId);

    void saveAllCommittedWidget(List<CommittedWidget> committedWidgets);

    void deleteCommittedWidget(String boardId, String widgetId);
}
