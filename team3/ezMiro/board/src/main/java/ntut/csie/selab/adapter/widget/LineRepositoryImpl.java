package ntut.csie.selab.adapter.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineDataMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.usecase.widget.LineRepository;

import java.util.Optional;

public class LineRepositoryImpl implements LineRepository {
    private LineRepositoryPeer peer;

    public LineRepositoryImpl(LineRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public void save(Line line) {
        peer.save(LineDataMapper.domainToData(line));
    }

    @Override
    public void delete(Line line) {
        peer.deleteById(line.getId());
    }

    @Override
    public Optional<Line> findById(final String lineId) {
        Optional<LineData> lineData = peer.findById(lineId);
        if (lineData.isPresent()) {
            Line selectedLine = LineDataMapper.dataToDomain(lineData.get());
            return Optional.of(selectedLine);
        }
        return Optional.empty();
    }
}
