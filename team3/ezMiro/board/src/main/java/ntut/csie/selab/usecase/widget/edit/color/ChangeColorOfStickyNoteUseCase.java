package ntut.csie.selab.usecase.widget.edit.color;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;

import java.util.Optional;

public class ChangeColorOfStickyNoteUseCase {
    private WidgetRepository widgetRepository;
    private DomainEventBus domainEventBus;

    public ChangeColorOfStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
    }


    public void execute(ChangeColorOfStickyNoteInput input, ChangeColorOfStickyNoteOutput output) {
        Optional<Widget> stickyNote = widgetRepository.findById(input.getStickyNoteId());
        if (stickyNote.isPresent()) {
            Widget selectedStickyNote = stickyNote.get();
            selectedStickyNote.setColor(input.getStickyNoteColor());

            domainEventBus.postAll(selectedStickyNote);
            output.setStickyNoteColor(selectedStickyNote.getColor());
        } else {
            throw new RuntimeException("Sticky note not found, sticky note id = " + input.getStickyNoteId());
        }
    }
}
