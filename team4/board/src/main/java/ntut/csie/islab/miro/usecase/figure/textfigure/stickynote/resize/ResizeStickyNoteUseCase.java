package ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.resize;


import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.StickyNote;
import ntut.csie.islab.miro.usecase.figure.textfigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.figure.textfigure.TextFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ResizeStickyNoteUseCase {
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public ResizeStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public ResizeStickyNoteInput newInput() {
        return new ResizeStickyNoteInput();
    }

    public void execute(ResizeStickyNoteInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getFigureId()).orElse(null);
        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Resize stickyNote failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
        stickyNote.resize(input.getWidth(), input.getHeight());
        stickyNoteRepository.save((StickyNote) stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Resize stickyNote success");

    }
}
