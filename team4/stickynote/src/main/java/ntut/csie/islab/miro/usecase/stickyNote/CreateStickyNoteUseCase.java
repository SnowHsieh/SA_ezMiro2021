package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.entity.model.stickyNote.StickyNote;
import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.*;

public class CreateStickyNoteUseCase {

    private FigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public CreateStickyNoteUseCase(FigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }
    public CreateStickyNoteInput newInput() {
        return new CreateStickyNoteInput();
    }

    public void execute(CreateStickyNoteInput input, ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput output) {
        Figure stickyNote = new StickyNote(input.getBoardId(), input.getPosition(), input.getContent(), input.getStyle());

        stickyNoteRepository.save(stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
    }
}
