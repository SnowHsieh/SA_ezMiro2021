package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.line;

import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LineRepositoryImpl implements LineRepository {

    private List<Line> lines;

    public LineRepositoryImpl() {
        this.lines = new ArrayList<>();
    }

    @Override
    public List<Line> findAll() {
        return lines;
    }

    @Override
    public Optional<Line> findById(String id) {
        return lines.stream().filter(x -> x.getLineId().equals(id)).findFirst();
    }

    @Override
    public void save(Line line) {
        lines.removeIf(x->x.getLineId().equals(line.getLineId()));
        lines.add(line);
    }

    @Override
    public void deleteById(String id) {
        lines.removeIf(x->x.getLineId().equals(id));
    }

    @Override
    public List<Line> getLineByBoardId(String boardId) {
        return lines.stream().filter(x-> x.getBoardId().equals(boardId)).collect(Collectors.toList());
    }

    @Override
    public List<Line> findByFigureId(String figureId) {
        return lines.stream().filter(x-> x.getSourceId().equals(figureId) || x.getTargetId().equals(figureId)).collect(Collectors.toList());
    }
}
