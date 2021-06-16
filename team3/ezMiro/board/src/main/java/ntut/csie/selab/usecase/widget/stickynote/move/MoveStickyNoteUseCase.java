package ntut.csie.selab.usecase.widget.stickynote.move;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class MoveStickyNoteUseCase {
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public MoveStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(MoveStickyNoteInput input, MoveStickyNoteOutput output) {
        Optional<Widget> stickyNote = stickyNoteRepository.findById(input.getStickyNoteId());
        if (stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.clearDomainEvents();
            selectedStickyNote.move(input.getPosition());
            stickyNoteRepository.save(selectedStickyNote);
            domainEventBus.postAll(selectedStickyNote);
            output.setStickyNoteId(selectedStickyNote.getId());
            output.setPosition(selectedStickyNote.getPosition());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
