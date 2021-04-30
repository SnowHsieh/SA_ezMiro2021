package ntut.csie.islab.miro.usecase.textFigure.stickyNote;

import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class DeleteStickyNoteUseCase {

    private TextFigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public DeleteStickyNoteUseCase(TextFigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public DeleteStickyNoteInput newInput() {
        return new DeleteStickyNoteInput();
    }

    public void execute(DeleteStickyNoteInput input, CqrsCommandOutput output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getBoardId(),input.getFigureId()).orElse(null);

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

        stickyNoteRepository.delete(stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Delete stickyNote success");
    }
}
