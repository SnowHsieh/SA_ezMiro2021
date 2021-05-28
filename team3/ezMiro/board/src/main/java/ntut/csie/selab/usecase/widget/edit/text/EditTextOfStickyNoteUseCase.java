package ntut.csie.selab.usecase.widget.edit.text;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class EditTextOfStickyNoteUseCase {

    private WidgetRepository widgetRepository;
    private DomainEventBus domainEventBus;

    public EditTextOfStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(EditTextOfStickyNoteInput input, EditTextOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = widgetRepository.findById(input.getStickyNoteId());

        if(stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.clearDomainEvents();
            selectedStickyNote.setText(input.getText());

            widgetRepository.save(selectedStickyNote);
            domainEventBus.postAll(selectedStickyNote);

            output.setStickyNoteId(input.getStickyNoteId());
            output.setText(selectedStickyNote.getText());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
