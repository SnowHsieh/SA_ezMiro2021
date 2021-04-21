package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.StickyNote;
import ntut.csie.selab.entity.model.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteInput;
import ntut.csie.selab.usecase.widget.create.CreateStickyNoteOutput;
import ntut.csie.selab.usecase.widget.create.WidgetCreated;

import java.util.Date;
import java.util.UUID;

public class CreateStickyNoteUseCase {
    WidgetRepositoryImpl widgetRepositoryImpl;
    DomainEventBus domainEventBus;

    public CreateStickyNoteUseCase(WidgetRepositoryImpl widgetRepositoryImpl, DomainEventBus domainEventBus) {
        this.widgetRepositoryImpl = widgetRepositoryImpl;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateStickyNoteInput input, CreateStickyNoteOutput output) {
        String stickyNoteId = UUID.randomUUID().toString();
        Widget widget = new StickyNote(stickyNoteId, input.getBoardId(), input.getCoordinate());

        widgetRepositoryImpl.add(widget);
        domainEventBus.post(new WidgetCreated(new Date(), input.getBoardId(), stickyNoteId));

        output.setStickyNoteId(widget.getId());
        output.setBoardId(widget.getBoardId());
        output.setCoordinate(widget.getCoordinate());
    }
}
