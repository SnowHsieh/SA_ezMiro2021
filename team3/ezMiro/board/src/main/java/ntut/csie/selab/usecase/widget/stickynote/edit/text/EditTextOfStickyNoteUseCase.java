package ntut.csie.selab.usecase.widget.stickynote.edit.text;

import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class EditTextOfStickyNoteUseCase {

    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public EditTextOfStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(EditTextOfStickyNoteInput input, EditTextOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = stickyNoteRepository.findById(input.getStickyNoteId());

        if(stickyNote.isPresent()) {
            StickyNote selectedStickyNote = (StickyNote) stickyNote.get();
            selectedStickyNote.clearDomainEvents();
            selectedStickyNote.setText(input.getText());

            stickyNoteRepository.save(selectedStickyNote);
            domainEventBus.postAll(selectedStickyNote);

            output.setStickyNoteId(input.getStickyNoteId());
            output.setText(selectedStickyNote.getText());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
