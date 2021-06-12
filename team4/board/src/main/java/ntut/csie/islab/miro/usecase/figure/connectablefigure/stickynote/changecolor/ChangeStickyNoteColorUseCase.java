package ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote.changecolor;

import ntut.csie.islab.miro.entity.model.figure.connectablefigure.stickynote.StickyNote;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ConnectableFigure;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ChangeStickyNoteColorUseCase {
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public ChangeStickyNoteColorUseCase(StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public ChangeStickyNoteColorInput newInput() {
        return new ChangeStickyNoteColorInput();
    }

    public void execute(ChangeStickyNoteColorInput input, CqrsCommandPresenter output) {
        ConnectableFigure stickyNote = stickyNoteRepository.findById(input.getFigureId()).orElse(null);
        if (null == stickyNote){
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("change stickyNote color failed: stickyNote not found, stickyNote id = " + input.getFigureId());
            return;
        }
        stickyNote.changeColor(input.getColor());
        stickyNoteRepository.save((StickyNote) stickyNote);
        domainEventBus.postAll(stickyNote);
        output.setId(stickyNote.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("change stickyNote color success");

    }
}