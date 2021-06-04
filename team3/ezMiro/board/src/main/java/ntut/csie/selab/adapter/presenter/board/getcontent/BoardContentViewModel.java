package ntut.csie.selab.adapter.presenter.board.getcontent;

import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.widget.LineDto;
import ntut.csie.selab.usecase.widget.StickyNoteDto;

import java.util.List;

public class BoardContentViewModel {
    private String boardId;
    private List<StickyNoteDto> stickyNoteDtos;
    private List<LineDto> lineDtos;
    private List<CommittedWidgetDto> committedWidgetDtos;

    public BoardContentViewModel(String boardId, List<StickyNoteDto> stickyNoteDtos, List<LineDto> lineDtos, List<CommittedWidgetDto> committedWidgetDtos) {
        this.boardId = boardId;
        this.stickyNoteDtos = stickyNoteDtos;
        this.lineDtos = lineDtos;
        this.committedWidgetDtos = committedWidgetDtos;
    }

    public String getBoardId() {
        return boardId;
    }

    public List<StickyNoteDto> getStickyNoteDtos() {
        return stickyNoteDtos;
    }

    public List<LineDto> getLineDtos() {
        return lineDtos;
    }

    public List<CommittedWidgetDto> getCommittedWidgetDtos() {
        return committedWidgetDtos;
    }
}
