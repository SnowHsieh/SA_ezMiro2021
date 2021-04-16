package ntut.csie.team5.adapter.figure;

import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.note.FigureRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FigureRepositoryImpl implements FigureRepository {

    private List<Note> notes;

    public FigureRepositoryImpl() {
        notes = new ArrayList<>();
    }

    @Override
    public List<Note> findAll() {
        return null;
    }

    @Override
    public Optional<Note> findById(String id) {
        return notes.stream().filter(note -> note.getNoteId().equals(id)).findAny();
    }

    @Override
    public void save(Note note) {
        notes.add(note);
    }

    @Override
    public void deleteById(String id) {
        Optional<Note> note = findById(id);

        if(note.isPresent())
            notes.remove(note.get());
    }
}
