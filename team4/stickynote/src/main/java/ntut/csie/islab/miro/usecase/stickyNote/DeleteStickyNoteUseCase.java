package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class DeleteStickyNoteUseCase {

    private FigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public DeleteStickyNoteUseCase(FigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public DeleteStickyNoteInput newInput() {
        return new DeleteStickyNoteInput();
    }

    public void execute(DeleteStickyNoteInput input, CqrsCommandOutput output) {
        Figure stickyNote = stickyNoteRepository.findById(input.getStickyNoteId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getStickyNoteId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete stickyNote failed: stickyNote not found, stickyNote id = " + input.getStickyNoteId());
            return;
        }

        stickyNoteRepository.delete(stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
    }
}
