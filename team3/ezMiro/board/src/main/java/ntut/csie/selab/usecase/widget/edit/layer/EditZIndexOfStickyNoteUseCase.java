package ntut.csie.selab.usecase.widget.edit.layer;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.entity.model.widget.event.WidgetZIndexEdited;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Date;
import java.util.Optional;

public class EditZIndexOfStickyNoteUseCase {

    private WidgetRepository widgetRepository;
    private DomainEventBus domainEventBus;

    public EditZIndexOfStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(EditZIndexOfStickyNoteInput input, EditZIndexOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = widgetRepository.findById(input.getStickyNoteId());
        if(stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.setzIndex(input.getzIndex());
            selectedStickyNote.addDomainEvent(new WidgetZIndexEdited(new Date()));

            domainEventBus.postAll(selectedStickyNote);
            output.setStickyNoteId(selectedStickyNote.getId());
            output.setzIndex(selectedStickyNote.getzIndex());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
