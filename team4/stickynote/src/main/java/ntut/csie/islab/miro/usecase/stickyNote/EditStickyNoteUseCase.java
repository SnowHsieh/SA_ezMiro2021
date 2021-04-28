package ntut.csie.islab.miro.usecase.stickyNote;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;

public class EditStickyNoteUseCase {
    private FigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public EditStickyNoteUseCase(FigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public EditStickyNoteInput newInput() {
        return new EditStickyNoteInput();
    }

    public void execute(EditStickyNoteInput input, CqrsCommandPresenter output) {
        Figure stickyNote = stickyNoteRepository.findById(input.getBoardId(),input.getFigureId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Edit stickyNote failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }

        stickyNoteRepository.edit(input.getBoardId(),stickyNote, input.getContent(), input.getStyle());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Edit stickyNote success");
    }
}
