package ntut.csie.selab.usecase.widget.create;

import ntut.csie.selab.entity.model.StickyNote;
import ntut.csie.selab.entity.model.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.WidgetRepository;


import java.util.Date;
import java.util.UUID;

public class CreateStickyNoteUseCase {
    WidgetRepository widgetRepository;
    DomainEventBus domainEventBus;

    public CreateStickyNoteUseCase(WidgetRepository widgetRepository, DomainEventBus domainEventBus) {
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateStickyNoteInput input, CreateStickyNoteOutput output) {
        String stickyNoteId = UUID.randomUUID().toString();
        Widget widget = new StickyNote(stickyNoteId, input.getBoardId(), input.getCoordinate());

        widgetRepository.add(widget);
        domainEventBus.post(new WidgetCreated(new Date(), input.getBoardId(), stickyNoteId));

        output.setStickyNoteId(widget.getId());
        output.setBoardId(widget.getBoardId());
        output.setCoordinate(widget.getCoordinate());
    }
}
