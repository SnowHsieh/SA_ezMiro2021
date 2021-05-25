package ntut.csie.islab.miro.usecase.textFigure.stickyNote;


import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;

public class EditStickyNoteUseCase {
    private TextFigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public EditStickyNoteUseCase(TextFigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public EditStickyNoteInput newInput() {
        return new EditStickyNoteInput();
    }

    public void execute(EditStickyNoteInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getBoardId(),input.getFigureId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Edit stickyNote failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
//        stickyNote.changeContent(input.getContent());
        stickyNoteRepository.edit(input.getBoardId(),stickyNote, input.getContent(), input.getStyle());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Edit stickyNote success");
    }
}
