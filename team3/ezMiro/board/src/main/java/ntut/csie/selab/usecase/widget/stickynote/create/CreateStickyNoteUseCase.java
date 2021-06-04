package ntut.csie.selab.usecase.widget.stickynote.create;

import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;


import java.util.UUID;

public class CreateStickyNoteUseCase {
    StickyNoteRepository stickyNoteRepository;
    DomainEventBus domainEventBus;

    public CreateStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateStickyNoteInput input, CreateStickyNoteOutput output) {
        String stickyNoteId = UUID.randomUUID().toString();
        Widget widget = new StickyNote(stickyNoteId, input.getBoardId(), input.getCoordinate());

        stickyNoteRepository.save(widget);
        domainEventBus.postAll(widget);

        output.setStickyNoteId(widget.getId());
        output.setBoardId(widget.getBoardId());
        output.setCoordinate(widget.getCoordinate());
    }
}
