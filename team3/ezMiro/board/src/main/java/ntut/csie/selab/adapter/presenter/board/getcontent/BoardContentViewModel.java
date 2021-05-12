package ntut.csie.selab.adapter.presenter.board.getcontent;

import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.widget.WidgetDto;

import java.util.List;

public class BoardContentViewModel {
    private String boardId;
    private List<WidgetDto> widgetDtos;
    private List<CommittedWidgetDto> committedWidgetDtos;

    public BoardContentViewModel(String boardId, List<WidgetDto> widgetDtos, List<CommittedWidgetDto> committedWidgetDtos) {
        this.boardId = boardId;
        this.widgetDtos = widgetDtos;
        this.committedWidgetDtos = committedWidgetDtos;
    }

    public String getBoardId() {
        return boardId;
    }

    public List<WidgetDto> getWidgetDtos() {
        return widgetDtos;
    }

    public List<CommittedWidgetDto> getCommittedWidgetDtos() {
        return committedWidgetDtos;
    }
}
