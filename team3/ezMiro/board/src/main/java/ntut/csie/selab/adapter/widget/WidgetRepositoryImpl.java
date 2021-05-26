package ntut.csie.selab.adapter.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WidgetRepositoryImpl implements WidgetRepository {

    private WidgetRepositoryPeer peer;

    List<Widget> widgets;

    public WidgetRepositoryImpl(WidgetRepositoryPeer peer) {
        this.peer = peer;
        widgets = new ArrayList<>();
    }

    @Override
    public void save(Widget widget) {
        peer.save(WidgetMapper.domainToData(widget));
        widgets.add(widget);
    }

    @Override
    public void delete(Widget widget) { widgets.remove(widget); }

    @Override
    public Optional<Widget> findById(final String widgetId) {
        return widgets.stream().filter(e -> e.getId().equals(widgetId)).findFirst();
    }
}
