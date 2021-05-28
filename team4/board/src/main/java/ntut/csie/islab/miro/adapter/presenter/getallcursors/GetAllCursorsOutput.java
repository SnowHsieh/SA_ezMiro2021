package ntut.csie.islab.miro.adapter.presenter.getallcursors;

import ntut.csie.islab.miro.entity.model.board.cursor.Cursor;
import ntut.csie.sslab.ddd.usecase.Output;

import java.util.List;
import java.util.UUID;

public interface GetAllCursorsOutput extends Output {

    GetAllCursorsOutput setBoardId(UUID boardId);

    UUID getBoardId();

    GetAllCursorsOutput setCursorDtos(List<Cursor> cursorDtos);

    List<Cursor> getCursorDtos();
}
