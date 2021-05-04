package ntut.csie.team5.adapter.gateway.repository.springboot.figure;

import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return figures.stream().filter(figure -> figure.getId().equals(id)).findAny();
    }

    @Override
    public void save(Figure figure) {
        if(figures.contains(figure))
            return;

        figures.add(figure);
    }

    @Override
    public void deleteById(String id) {
        Optional<Figure> figure = findById(id);

        if(figure.isPresent())
            figures.remove(figure.get());
    }

    @Override
    public List<Figure> getFiguresByBoardId(String boardId) {
        return figures.stream().filter(figure -> figure.getBoardId().equals(boardId)).collect(Collectors.toList());
    }
}
