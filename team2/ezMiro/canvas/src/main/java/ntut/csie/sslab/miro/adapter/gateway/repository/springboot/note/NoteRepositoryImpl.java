package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note;

import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteRepositoryImpl implements NoteRepository {
    private List<Note> notes = new ArrayList<>();

    @Override
    public List<Note> findAll() {
        return this.notes;
    }

    @Override
    public Optional<Note> findById(String s) {
        return notes.stream().filter(x -> x.getId().equals(s)).findFirst();
    }

    @Override
    public void save(Note data) {
        notes.add(data);
    }

    @Override
    public void deleteById(String s) {

    }
}
