package ntut.csie.selab.usecase.widget.stickynote.resize;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class ResizeStickyNoteUseCase {

    private WidgetRepository widgetRepository;
    private DomainEventBus eventBus;

    public ResizeStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus eventBus) {
        this.widgetRepository = widgetRepository;
        this.eventBus = eventBus;
    }

    public void execute(ResizeStickyNoteInput input, ResizeStickyNoteOutput output) {
        Optional<Widget> widget = widgetRepository.findById(input.getStickyNoteId());
        if (widget.isPresent()) {
            Widget stickyNote = widget.get();
            stickyNote.clearDomainEvents();
            stickyNote.resize(input.getCoordinate());
            widgetRepository.save(stickyNote);
            eventBus.postAll(stickyNote);

            output.setStickyNoteId(input.getStickyNoteId());
            output.setCoordinate(input.getCoordinate());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
