package ntut.csie.selab.usecase.widget.stickynote.delete;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

public class DeleteStickyNoteUseCase {
    StickyNoteRepository stickyNoteRepository;
    DomainEventBus domainEventBus;

    public DeleteStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(DeleteStickyNoteInput input, DeleteStickyNoteOutput output) {
        Widget widget = stickyNoteRepository.findById(input.getStickyNoteId()).orElse(null);
        widget.clearDomainEvents();
        if (null == widget) {
            output.setStickyNoteId(input.getStickyNoteId());
            throw new RuntimeException("Sticky note found, widget id = " + input.getStickyNoteId());
        }
        widget.delete();
        stickyNoteRepository.delete(widget);
        domainEventBus.postAll(widget);

        output.setStickyNoteId(widget.getId());
    }
}
