package ntut.csie.selab.usecase.board.query.getcontent;

import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.List;

public class GetBoardContentOutput {
    private String boardId;
    private List<Widget> widgets;
    private List<CommittedWidget> committedWidgets;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }

    public List<CommittedWidget> getCommittedWidgets() {
        return committedWidgets;
    }

    public void setCommittedWidgets(List<CommittedWidget> committedWidgets) {
        this.committedWidgets = committedWidgets;
    }
}
