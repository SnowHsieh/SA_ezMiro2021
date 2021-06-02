package ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.create;

import ntut.csie.islab.miro.usecase.figure.textfigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.figure.textfigure.TextFigure;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.StickyNote;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.*;

public class CreateStickyNoteUseCase {

    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public CreateStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }
    public CreateStickyNoteInput newInput() {
        return new CreateStickyNoteInput();
    }

    public void execute(CreateStickyNoteInput input, CqrsCommandOutput output) {
        TextFigure stickyNote = new StickyNote(input.getBoardId(),
                input.getPosition(),
                input.getContent(),
                input.getStyle());
        stickyNoteRepository.save((StickyNote) stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Create stickyNote success");
    }
}