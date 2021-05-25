package ntut.csie.islab.miro.usecase.textFigure.stickyNote;


import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ResizeStickyNoteUseCase {
    private TextFigureRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public ResizeStickyNoteUseCase(TextFigureRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public ResizeStickyNoteInput newInput() {
        return new ResizeStickyNoteInput();
    }

    public void execute(ResizeStickyNoteInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getBoardId(),input.getFigureId()).orElse(null);
        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Resize stickyNote failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
        stickyNoteRepository.resize(stickyNote, input.getWidth(), input.getHeight());
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Resize stickyNote success");

    }
}
