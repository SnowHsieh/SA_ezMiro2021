package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CursorRepository extends AbstractRepository<Cursor, String> {

    List<Cursor> getCursorsByBoardId(String boardId);

}
