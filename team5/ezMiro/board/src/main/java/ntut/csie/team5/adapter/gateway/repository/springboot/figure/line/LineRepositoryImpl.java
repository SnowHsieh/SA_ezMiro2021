package ntut.csie.team5.adapter.gateway.repository.springboot.figure.line;

import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.line.LineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LineRepositoryImpl implements LineRepository {

    private LineRepositoryPeer peer;

    public LineRepositoryImpl(LineRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public List<Line> findAll() {
        List<LineData> lineDatas = new ArrayList<>();
        peer.findAll().forEach(lineDatas::add);
        return LineMapper.transformToDomain(lineDatas);
    }

    @Override
    public Optional<Line> findById(String id) {
        return peer.findById(id).map(LineMapper::transformToDomain);
    }

    @Override
    public void save(Line line) {
        LineData data = LineMapper.transformToData(line);
        peer.save(data);
    }

    @Override
    public void deleteById(String id) {
        peer.deleteById(id);
    }
}
