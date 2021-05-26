package ntut.csie.selab.usecase.board;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Widget;

public interface BoardAssociationRepository extends BoardRepository {
    void saveCommittedWidget(String boardId, String widgetId);

    void deleteCommittedWidget(String boardId, String widgetId);
}
