package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import org.springframework.data.repository.Repository;

public interface CursorRepository extends AbstractRepository<Cursor, String> {
}
