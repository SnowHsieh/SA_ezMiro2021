package ntut.csie.selab.adapter.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineDataMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.LineRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LineRepositoryImpl implements LineRepository {
    private LineRepositoryPeer peer;

    public LineRepositoryImpl(LineRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public void save(Widget line) {
        peer.save(LineDataMapper.domainToData(line));
    }

    @Override
    public void delete(Widget line) {
        peer.deleteById(line.getId());
    }

    @Override
    public Optional<Widget> findById(final String lineId) {
        Optional<LineData> lineData = peer.findById(lineId);
        if (lineData.isPresent()) {
            Line selectedLine = LineDataMapper.dataToDomain(lineData.get());
            return Optional.of(selectedLine);
        }
        return Optional.empty();
    }

    @Override
    public List<Widget> findAllByHeadWidgetOrTailWidget(final String widgetId) {
        List<LineData> lineDatas = peer.findAllByHeadWidgetIdOrTailWidgetId(widgetId, widgetId);
        return lineDatas.stream().map(LineDataMapper::dataToDomain).collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<Widget> lines) {
        peer.saveAll(lines.stream().map(LineDataMapper::domainToData).collect(Collectors.toList()));
    }
}
