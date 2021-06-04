package ntut.csie.selab.adapter.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteDataMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class StickyNoteRepositoryImpl implements StickyNoteRepository {

    private StickyNoteRepositoryPeer peer;

    public StickyNoteRepositoryImpl(StickyNoteRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public void save(Widget stickyNote) {
        try {
            peer.save(StickyNoteDataMapper.domainToData((StickyNote) stickyNote));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Widget stickyNote) {
        peer.deleteById(stickyNote.getId());
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
