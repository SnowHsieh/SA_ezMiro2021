package ntut.csie.selab.adapter.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteDataMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class StickyNoteRepositoryImpl implements WidgetRepository {

    private StickyNoteRepositoryPeer peer;

    public StickyNoteRepositoryImpl(StickyNoteRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public void save(Widget widget) {
        try {
            peer.save(StickyNoteDataMapper.domainToData((StickyNote)widget));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Widget widget) {
        peer.deleteById(widget.getId());
    }

    @Override
    public Optional<Widget> findById(final String widgetId) {
        Optional<StickyNoteData> widgetData = peer.findById(widgetId);
        if (widgetData.isPresent()) {
            Widget selectedWidget = StickyNoteDataMapper.dataToDomain(widgetData.get());
            return Optional.of(selectedWidget);
        }
        return Optional.empty();
    }
}
