package ntut.csie.islab.miro.usecase.textFigure.stickyNote;


import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.StickyNote;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;

public class MoveStickyNoteUseCase {
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public MoveStickyNoteUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public MoveStickyNoteInput newInput() {
        return new MoveStickyNoteInput();
    }

    public void execute(MoveStickyNoteInput input, CqrsCommandPresenter output) {
        TextFigure stickyNote = stickyNoteRepository.findById(input.getFigureId()).orElse(null);

        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Move stickyNote failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
        stickyNote.changePosition(input.getNewPosition());
        stickyNoteRepository.save((StickyNote) stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Move stickyNote success");
    }
}
