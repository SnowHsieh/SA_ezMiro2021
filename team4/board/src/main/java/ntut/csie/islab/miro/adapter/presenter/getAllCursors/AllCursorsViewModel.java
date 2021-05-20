package ntut.csie.islab.miro.adapter.presenter.getAllCursors;

import ntut.csie.islab.miro.entity.model.board.cursor.Cursor;
import ntut.csie.islab.miro.usecase.textFigure.TextFigureDto;
import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;

import java.util.List;
import java.util.UUID;

public class AllCursorsViewModel implements ViewModel {
    private UUID boardId;
    private List<Cursor> cursorDtos;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public List<Cursor> getCursorDtos() {
        return cursorDtos;
    }

    public void setCursorDtos(List<Cursor> cursorDtos) {
        this.cursorDtos = cursorDtos;
    }
}
