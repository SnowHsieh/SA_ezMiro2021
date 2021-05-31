package ntut.csie.selab.usecase.widget.stickynote.edit.fontsize;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class EditFontSizeOfStickyNoteUseCase {
    WidgetRepository widgetRepository;
    DomainEventBus domainEventBus;

    public EditFontSizeOfStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(EditFontSizeOfStickyNoteInput input, EditFontSizeOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = widgetRepository.findById(input.getStickyNoteId());
        if (stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.setFontSize(input.getFontSize());

            domainEventBus.postAll(selectedStickyNote);
            output.setStickyNoteId(input.getStickyNoteId());
            output.setFontSize(input.getFontSize());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
