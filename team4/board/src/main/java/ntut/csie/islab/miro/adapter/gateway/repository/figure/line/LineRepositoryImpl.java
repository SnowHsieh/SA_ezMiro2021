package ntut.csie.islab.miro.adapter.gateway.repository.figure.line;


import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LineRepositoryImpl implements LineRepository {
    private LineRepositoryPeer peer;

    public LineRepositoryImpl(LineRepositoryPeer lineRepositoryPeer) {
        this.peer = lineRepositoryPeer;
    }

    @Override
    public List<Line> findAll() {
        List<LineData> lineDataList = new ArrayList();
        peer.findAll().forEach(x -> lineDataList.add(x));
        return LineMapper.transformToDomain(lineDataList);
    }

    @Override
    public Optional<Line> findById(UUID lineId) {
        return peer.findById(lineId.toString()).map(LineMapper::transformToDomain);
    }

    @Override
    public void save(Line line) {
        peer.save(LineMapper.transformToData(line));
    }

    @Override
    public void deleteById(UUID lineId) {
        peer.deleteById(lineId.toString());
    }
}
