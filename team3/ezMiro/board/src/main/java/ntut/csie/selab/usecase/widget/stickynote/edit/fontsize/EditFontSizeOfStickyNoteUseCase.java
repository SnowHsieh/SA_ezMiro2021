package ntut.csie.selab.usecase.widget.stickynote.edit.fontsize;

import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class EditFontSizeOfStickyNoteUseCase {
    StickyNoteRepository stickyNoteRepository;
    DomainEventBus domainEventBus;

    public EditFontSizeOfStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(EditFontSizeOfStickyNoteInput input, EditFontSizeOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = stickyNoteRepository.findById(input.getStickyNoteId());
        if (stickyNote.isPresent()) {
            StickyNote selectedStickyNote = (StickyNote) stickyNote.get();
            selectedStickyNote.setFontSize(input.getFontSize());

            domainEventBus.postAll(selectedStickyNote);
            output.setStickyNoteId(input.getStickyNoteId());
            output.setFontSize(input.getFontSize());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
