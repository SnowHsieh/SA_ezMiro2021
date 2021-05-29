package ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.delete;

import ntut.csie.islab.miro.usecase.figure.textfigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.figure.textfigure.TextFigure;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class DeleteStickyNoteUseCase {

    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public DeleteStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public DeleteStickyNoteInput newInput() {
        return new DeleteStickyNoteInput();
    }

    public void execute(DeleteStickyNoteInput input, CqrsCommandOutput output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getFigureId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete stickyNote failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }

        stickyNote.markAsRemoved(
                stickyNote.getBoardId(),
                stickyNote.getFigureId()
        );

        stickyNoteRepository.deleteById(stickyNote.getFigureId());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Delete stickyNote success");
    }
}
