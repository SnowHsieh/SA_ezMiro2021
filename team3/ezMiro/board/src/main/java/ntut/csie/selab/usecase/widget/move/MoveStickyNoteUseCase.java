package ntut.csie.selab.usecase.widget.move;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class MoveStickyNoteUseCase {
    private WidgetRepository widgetRepository;
    private DomainEventBus domainEventBus;

    public MoveStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(MoveStickyNoteInput input, MoveStickyNoteOutput output) {
        Optional<Widget> stickyNote = widgetRepository.findById(input.getStickyNoteId());
        if (stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.setCoordinate(input.getCoordinate());

            domainEventBus.postAll(selectedStickyNote);
            output.setStickyNoteId(selectedStickyNote.getId());
            output.setCoordinate(selectedStickyNote.getCoordinate());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
