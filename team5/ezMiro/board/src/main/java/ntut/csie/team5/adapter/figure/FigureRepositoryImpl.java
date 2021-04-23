package ntut.csie.team5.adapter.figure;

import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.usecase.figure.note.FigureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FigureRepositoryImpl implements FigureRepository {

    private List<Figure> figures;

    public FigureRepositoryImpl() {
        figures = new ArrayList<>();
    }

    @Override
    public List<Figure> findAll() {
        return null;
    }

    @Override
    public Optional<Figure> findById(String id) {
        return figures.stream().filter(note -> note.getId().equals(id)).findAny();
    }

    @Override
    public void save(Figure note) {
        figures.add(note);
    }

    @Override
    public void deleteById(String id) {
        Optional<Figure> note = findById(id);

        if(note.isPresent())
            figures.remove(note.get());
    }
}
