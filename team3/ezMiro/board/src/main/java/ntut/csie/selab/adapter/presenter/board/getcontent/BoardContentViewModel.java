package ntut.csie.selab.adapter.presenter.board.getcontent;

import ntut.csie.selab.usecase.widget.WidgetDto;

import java.util.List;

public class BoardContentViewModel {
    private String boardId;
    private List<WidgetDto> widgetDtos;

    public BoardContentViewModel(String boardId, List<WidgetDto> widgetDtos) {
        this.boardId = boardId;
        this.widgetDtos = widgetDtos;
    }

    public String getBoardId() {
        return boardId;
    }

    public List<WidgetDto> getWidgetDtos() {
        return widgetDtos;
    }

}
