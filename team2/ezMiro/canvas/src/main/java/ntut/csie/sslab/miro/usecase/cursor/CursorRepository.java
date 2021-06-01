package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import java.util.Optional;

public interface CursorRepository extends AbstractRepository<Cursor, String> {
    Optional<Cursor> findByUserId(String userId);
}