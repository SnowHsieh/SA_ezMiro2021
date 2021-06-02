package ntut.csie.selab.usecase.widget.stickynote.edit.color;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class ChangeColorOfStickyNoteUseCase {
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public ChangeColorOfStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }


    public void execute(ChangeColorOfStickyNoteInput input, ChangeColorOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = stickyNoteRepository.findById(input.getStickyNoteId());
        if (stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.clearDomainEvents();
            selectedStickyNote.setColor(input.getStickyNoteColor());

            stickyNoteRepository.save(selectedStickyNote);
            domainEventBus.postAll(selectedStickyNote);
            output.setStickyNoteId(selectedStickyNote.getId());
            output.setStickyNoteColor(selectedStickyNote.getColor());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
