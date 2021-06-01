package ntut.csie.selab.adapter.presenter.board.getcontent;

import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.widget.StickyNoteDto;

import java.util.List;

public class BoardContentViewModel {
    private String boardId;
    private List<StickyNoteDto> stickyNoteDtos;
    private List<CommittedWidgetDto> committedWidgetDtos;

    public BoardContentViewModel(String boardId, List<StickyNoteDto> stickyNoteDtos, List<CommittedWidgetDto> committedWidgetDtos) {
        this.boardId = boardId;
        this.stickyNoteDtos = stickyNoteDtos;
        this.committedWidgetDtos = committedWidgetDtos;
    }

    public String getBoardId() {
        return boardId;
    }

    public List<StickyNoteDto> getWidgetDtos() {
        return stickyNoteDtos;
    }

    public List<CommittedWidgetDto> getCommittedWidgetDtos() {
        return committedWidgetDtos;
    }
}
