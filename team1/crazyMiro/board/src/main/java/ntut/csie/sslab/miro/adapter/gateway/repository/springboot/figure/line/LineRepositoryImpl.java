package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.line;

import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.sticker.StickerRepositoryPeer;
import ntut.csie.sslab.miro.entity.model.figure.FigureType;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LineRepositoryImpl implements LineRepository {

//    private List<Line> lines;
    private LineRepositoryPeer peer;

    public LineRepositoryImpl(LineRepositoryPeer lineRepositoryPeer) {
//        this.lines = new ArrayList<>();
        this.peer = lineRepositoryPeer;
    }

    @Override
    public List<Line> findAll() {
//        return lines;
        List<LineData> lineData = new ArrayList<>();
        peer.findAll().forEach(lineData::add);
        return LineMapper.transformToDomain(lineData);
    }

    @Override
    public Optional<Line> findById(String id) {
//        return lines.stream().filter(x -> x.getLineId().equals(id)).findFirst();
        return peer.findById(id).map(LineMapper::transformToDomain);
    }

    @Override
    public void save(Line line) {
//        lines.removeIf(x->x.getLineId().equals(line.getLineId()));
//        lines.add(line);
        peer.save(LineMapper.transformToData(line));
    }

    @Override
    public void deleteById(String id) {
//        lines.removeIf(x->x.getLineId().equals(id));
        peer.deleteById(id);
    }

    @Override
    public List<Line> getLineByBoardId(String boardId) {
//        return lines.stream().filter(x-> x.getBoardId().equals(boardId)).collect(Collectors.toList());
        return findAll().stream().filter(x -> x.getBoardId().equals(boardId) && x.getType().equals(FigureType.Line)).collect(Collectors.toList());
    }

    @Override
    public List<Line> findByFigureId(String figureId) {
//        return lines.stream().filter(x-> x.getSourceId().equals(figureId) || x.getTargetId().equals(figureId)).collect(Collectors.toList());
        return findAll().stream().filter(x -> x.getSourceId().equals(figureId) || x.getTargetId().equals(figureId)).collect(Collectors.toList());
    }
}
