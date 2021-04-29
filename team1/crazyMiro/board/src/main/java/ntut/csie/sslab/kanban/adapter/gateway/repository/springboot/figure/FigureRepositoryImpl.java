package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.entity.model.figure.FigureType;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

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
        return figures;
    }

    @Override
    public Optional<Figure> findById(String id) {
        return figures.stream().filter(x-> x.getFigureId().equals(id)).findFirst();
    }

    @Override
    public void save(Figure data) {
        figures.removeIf(figure -> figure.getFigureId().equals(data.getFigureId()));
        figures.add(data);
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public List<Figure> getStickersByBoardId(String boardId) {
        return figures.stream()
                .filter(x -> x.getBoardId().equals(boardId) &&
                                x.getType().equals(FigureType.Sticker))
                .collect(Collectors.toList());
    }
}
