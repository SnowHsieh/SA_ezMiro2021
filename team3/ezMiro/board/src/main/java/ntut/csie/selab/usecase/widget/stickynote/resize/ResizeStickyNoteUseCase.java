package ntut.csie.selab.usecase.widget.stickynote.resize;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class ResizeStickyNoteUseCase {

    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus eventBus;

    public ResizeStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus eventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.eventBus = eventBus;
    }

    public void execute(ResizeStickyNoteInput input, ResizeStickyNoteOutput output) {
        Optional<Widget> widget = stickyNoteRepository.findById(input.getStickyNoteId());
        if (widget.isPresent()) {
            Widget stickyNote = widget.get();
            stickyNote.clearDomainEvents();
            stickyNote.resize(input.getCoordinate());
            stickyNoteRepository.save(stickyNote);
            eventBus.postAll(stickyNote);

            output.setStickyNoteId(input.getStickyNoteId());
            output.setCoordinate(input.getCoordinate());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
