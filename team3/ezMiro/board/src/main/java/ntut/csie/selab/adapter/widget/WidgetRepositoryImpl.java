package ntut.csie.selab.adapter.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetDataMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class WidgetRepositoryImpl implements WidgetRepository {

    private WidgetRepositoryPeer peer;

    public WidgetRepositoryImpl(WidgetRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public void save(Widget widget) {
        peer.save(WidgetDataMapper.domainToData(widget));
    }

    @Override
    public void delete(Widget widget) {
        peer.deleteById(widget.getId());
    }

    @Override
    public Optional<Widget> findById(final String widgetId) {
        Optional<WidgetData> widgetData = peer.findById(widgetId);
        if (widgetData.isPresent()) {
            Widget selectedWidget = WidgetDataMapper.dataToDomain(widgetData.get());
            return Optional.of(selectedWidget);
        }
        return Optional.empty();
    }
}
