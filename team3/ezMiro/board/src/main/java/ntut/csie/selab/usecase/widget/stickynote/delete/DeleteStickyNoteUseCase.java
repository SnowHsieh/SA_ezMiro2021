package ntut.csie.selab.usecase.widget.stickynote.delete;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;

public class DeleteStickyNoteUseCase {
    WidgetRepository widgetRepository;
    DomainEventBus domainEventBus;

    public DeleteStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(DeleteStickyNoteInput input, DeleteStickyNoteOutput output) {
        Widget widget = widgetRepository.findById(input.getStickyNoteId()).orElse(null);
        widget.clearDomainEvents();
        if (null == widget) {
            output.setStickyNoteId(input.getStickyNoteId());
            throw new RuntimeException("Sticky note found, widget id = " + input.getStickyNoteId());
        }
        widget.delete();
        widgetRepository.delete(widget);
        domainEventBus.postAll(widget);

        output.setStickyNoteId(widget.getId());
    }
}
