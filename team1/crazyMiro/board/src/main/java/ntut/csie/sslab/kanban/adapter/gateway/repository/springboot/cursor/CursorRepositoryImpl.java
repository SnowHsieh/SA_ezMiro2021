package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.cursor;

import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursorRepositoryImpl implements CursorRepository {

    private List<Cursor> cursors;

    public CursorRepositoryImpl() {
        this.cursors = new ArrayList<>();
    }

    @Override
    public List<Cursor> findAll() {
        return null;
    }

    @Override
    public Optional<Cursor> findById(String id) {
        return cursors.stream().filter(x -> x.getCursorId().equals(id)).findFirst();
    }

    public Optional<Cursor> getCursorBySessionId(String sessionId){
        return cursors.stream().filter(x -> x.getSessionId().equals(sessionId)).findFirst();
    }

    @Override
    public void save(Cursor cursor) {
        cursors.removeIf(x -> x.getCursorId().equals(cursor.getCursorId()));
        cursors.add(cursor);
    }

    @Override
    public void deleteById(String s) {

    }
}
