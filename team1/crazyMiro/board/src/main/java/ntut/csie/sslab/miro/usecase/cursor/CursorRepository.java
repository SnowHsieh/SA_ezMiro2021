package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;

import java.util.List;
import java.util.Optional;

public interface CursorRepository extends AbstractRepository<Cursor, String> {

    List<Cursor> getCursorsByBoardId(String boardId);

    Optional<Cursor> findCursorByUserId(String userId);

}
