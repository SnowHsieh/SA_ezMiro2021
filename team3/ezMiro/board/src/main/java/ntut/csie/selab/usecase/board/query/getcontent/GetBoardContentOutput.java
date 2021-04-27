package ntut.csie.selab.usecase.board.query.getcontent;

import ntut.csie.selab.entity.model.widget.Widget;

import java.util.List;

public class GetBoardContentOutput {
    private String boardId;
    private List<Widget> widgets;

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
}
