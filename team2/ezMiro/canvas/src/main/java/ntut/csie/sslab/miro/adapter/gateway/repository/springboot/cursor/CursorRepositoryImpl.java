package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.cursor;

import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CursorRepositoryImpl implements CursorRepository {
    private Map<String, Cursor> cursors = new HashMap<>();

    @Override
    public List<Cursor> findAll() {
        return this.cursors.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Cursor> findById(String cursorId) {
        return Optional.ofNullable(cursors.get(cursorId));
    }

    @Override
    public void save(Cursor cursor) {
        cursors.put(cursor.getId(), cursor);
    }

    @Override
    public void deleteById(String cursorId) {
        cursors.remove(cursorId);
    }

    @Override
    public void deleteAll() {
        cursors.clear();
    }

    @Override
    public Optional<Cursor> findByUserId(String userId) {
        return cursors.values().stream().filter(c -> c.getUserId().equals(userId)).findFirst();
    }
}